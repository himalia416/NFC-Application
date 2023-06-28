package no.nordic.handOverSelectMessageParser

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.nio.BufferUnderflowException
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets


@RequiresApi(Build.VERSION_CODES.M)
data class HandOverData(
    val transport: Int = BluetoothDevice.TRANSPORT_LE,
    val bleOobData: BluetoothLeOobData
)


data class BluetoothLeOobData(
    // required fields
    val bleDeviceAddress: BluetoothDevice? = null,
    val bleAddressType: AddressType? = null,
    val roleType: LeRoleType? = null,
    // optional fields
    val securityManagerTK: ByteArray? = null,
    val leSecureConnectionConfirmation: ByteArray? = null,
    val leSecureConnectionRandom: ByteArray? = null,
    val appearance: ByteArray? = null,
    val flags: Int? = null,
    val localName: String? = null,
)

// The following data type values are assigned by NFC Forum.
// For more details refer to Technical specification Bluetooth Secure Simple Pairing Using NFC Version 1.2 Section 4.2.2
private const val BT_HANDOVER_TYPE_MAC: Int = 0x1B
private const val BT_HANDOVER_TYPE_LE_ROLE: Int = 0x1C
private const val BT_HANDOVER_TYPE_LONG_LOCAL_NAME: Int = 0x09
private const val BT_HANDOVER_TYPE_LONG_LOCAL_NAME_2: Int = 0x08
private const val BT_HANDOVER_TYPE_SECURITY_MANAGER_TK: Int = 0x10
private const val BT_HANDOVER_TYPE_LE_SC_CONFIRMATION: Int = 0x22
private const val BT_HANDOVER_TYPE_LE_SC_RANDOM: Int = 0x23
private const val BT_HANDOVER_TYPE_LE_APPEARANCE: Int = 0x19
private const val BT_HANDOVER_TYPE_LE_FLAG: Int = 0x01
private const val SECURITY_MANAGER_TK_SIZE: Int = 16
private const val SECURITY_MANAGER_LE_SC_C_SIZE: Int = 16
private const val SECURITY_MANAGER_LE_SC_R_SIZE: Int = 16

enum class AddressType {
    UNKNOWN,
    PRIVATE,
    PUBLIC;

    companion object {
        fun parse(byte: Byte): AddressType = when (byte.toInt()) {
            0 -> PUBLIC
            1 -> PRIVATE
            else -> UNKNOWN
        }
    }
}

enum class LeRoleType {
    PERIPHERAL_ONLY,
    CENTRAL_ONLY,
    PERIPHERAL_CENTRAL_PERIPHERAL_PREFERRED,
    PERIPHERAL_CENTRAL_CENTRAL_PREFERRED,
    INVALID; // others reserved for future use

    companion object {
        fun parse(byte: Byte): LeRoleType = when (byte.toInt()) {
            0x00 -> PERIPHERAL_ONLY
            0x01 -> CENTRAL_ONLY
            0x03 -> PERIPHERAL_CENTRAL_PERIPHERAL_PREFERRED
            0x04 -> PERIPHERAL_CENTRAL_CENTRAL_PREFERRED
            else -> INVALID
        }
    }
}

@RequiresApi(Build.VERSION_CODES.M)
class HandOverMessageParser(
    @ApplicationContext private val context: Context
) {

    private val TAG = "NfcHandover"
    private val TYPE_BLE_OOB: ByteArray =
        "application/vnd.bluetooth.le.oob".toByteArray(StandardCharsets.US_ASCII)

    // todo put it in di
    private val manager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val mBluetoothAdapter: BluetoothAdapter? = manager.adapter
    private val _result: MutableStateFlow<BluetoothLeOobData> =
        MutableStateFlow(BluetoothLeOobData())
    val result = _result.asStateFlow()

    fun parser(payload: ByteBuffer): BluetoothLeOobData {
        val result = BluetoothLeOobData()
        try {
            var bdaddr: ByteArray?
            var addressType: AddressType?
            var role: Byte?
            var flag: Byte?
            var leSecureConfirmation: ByteArray?
            var leSecureRandom: ByteArray?
            var appearance: ByteArray?
            var name: String?
            var securityManagerTK: ByteArray?
            while (payload.remaining() > 0) {
                println("remaining payload: ${payload.remaining()}")
                val len = payload.get().toInt() - 1
                println("Length: $len")
                when (payload.get().toInt()) {
                    BT_HANDOVER_TYPE_MAC -> {
                        /* bdaddr = ByteArray(6) // 6 Bytes for the mac address and 1 byte for the address data type
                         payload[bdaddr]
                         val address = bdaddr.reverse()*/
                        bdaddr = ByteArray(6) // 6 bytes for mac, 1 for address type
                        val address = parseMacFromBluetoothRecord(payload[bdaddr])
                        _result.value = _result.value.copy(
                            bleDeviceAddress = mBluetoothAdapter?.getRemoteDevice(address),
                            bleAddressType = AddressType.parse(payload.get())
                        )
                    }

                    BT_HANDOVER_TYPE_LE_ROLE -> {
                        val roleType = LeRoleType.parse(payload.get())
                        _result.value = _result.value.copy(roleType = roleType)
                        println("parser le role")
                    }

                    BT_HANDOVER_TYPE_LONG_LOCAL_NAME -> {
                        val nameBytes = ByteArray(len)
                        payload[nameBytes]
                        name = String(nameBytes, StandardCharsets.UTF_8)
                        _result.value = _result.value.copy(localName = name)
                        println("parser name: $name")
                    }

                    BT_HANDOVER_TYPE_LONG_LOCAL_NAME_2 -> {
                        val nameBytes = ByteArray(len)
                        payload[nameBytes]
                        name = String(nameBytes, StandardCharsets.UTF_8)
                        _result.value = _result.value.copy(localName = name)
                        println("parser name: $name")
                    }

                    BT_HANDOVER_TYPE_SECURITY_MANAGER_TK -> {
                        if (len != SECURITY_MANAGER_TK_SIZE) {
                            Log.i(
                                TAG, "BT OOB: invalid size of SM TK, should be " +
                                        SECURITY_MANAGER_TK_SIZE + " bytes."
                            )
                            break
                        }
                        securityManagerTK = ByteArray(len)
                        val sm = payload[securityManagerTK]
                        _result.value =
                            _result.value.copy(securityManagerTK = parseLittleEndianOrder(sm))
                        println("parser: security manager tk")
                    }

                    BT_HANDOVER_TYPE_LE_SC_CONFIRMATION -> {
                        if (len != SECURITY_MANAGER_LE_SC_C_SIZE) {
                            Log.i(
                                TAG, "BT OOB: invalid size of LE SC Confirmation, should be " +
                                        SECURITY_MANAGER_LE_SC_C_SIZE + " bytes."
                            )
                            break
                        }
                        leSecureConfirmation = ByteArray(len)
                        payload[leSecureConfirmation]
                        println("parser security confirmation: }")
                    }

                    BT_HANDOVER_TYPE_LE_SC_RANDOM -> {
                        if (len != SECURITY_MANAGER_LE_SC_R_SIZE) {
                            Log.i(
                                TAG, "BT OOB: invalid size of LE SC Random, should be " +
                                        SECURITY_MANAGER_LE_SC_R_SIZE + " bytes."
                            )
                            break
                        }
                        leSecureRandom = ByteArray(len)
                        payload[leSecureRandom]
                        println("parser le security random")
                    }

                    BT_HANDOVER_TYPE_LE_APPEARANCE -> {
                        appearance = ByteArray(len)
                        val ap = payload[appearance]
                        _result.value = _result.value.copy(appearance = parseLittleEndianOrder(ap))
                        println("parser appearance")
                    }

                    BT_HANDOVER_TYPE_LE_FLAG -> {
                        flag = payload.get()
                        println("parser flag ${flag.toInt()}")
                    }

                    else -> payload.position(payload.position() + len)
                }
            }
        } catch (e: IllegalArgumentException) {
            println("BLE OOB: error parsing OOB data $e")
        } catch (e: BufferUnderflowException) {
            println("BT OOB: payload shorter than expected")
        }
        return result
    }

    private fun parseMacFromBluetoothRecord(payload: ByteBuffer): ByteArray {
        val address = ByteArray(6)
        payload[address]
        // ByteBuffer.order(LITTLE_ENDIAN) doesn't work for
        // ByteBuffer.get(byte[]), so manually swap order
        for (i in 0..2) {
            val temp = address[i]
            address[i] = address[5 - i]
            address[5 - i] = temp
        }
        return address
    }

    private fun parseLittleEndianOrder(buffer: ByteBuffer): ByteArray {
        return buffer.array().reversed().toByteArray()
    }
}