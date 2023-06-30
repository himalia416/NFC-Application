package no.nordic.handOverSelectMessageParser

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import no.nordic.handOverSelectMessageParser.mapper.AppearanceParser
import no.nordic.handOverSelectMessageParser.parser.FlagByteParser
import java.nio.BufferUnderflowException
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import javax.inject.Singleton

@RequiresApi(Build.VERSION_CODES.M)
data class HandOverData(
    val transport: Int = BluetoothDevice.TRANSPORT_LE,
    val bleOobData: BluetoothLeOobData
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
@Singleton
class HandOverMessageParser @Inject constructor(
    private val bluetoothAdapter: BluetoothAdapter
) {
    private val TAG = "HandoverMessageParser"
    private val _result: MutableStateFlow<BluetoothLeOobData> = MutableStateFlow(BluetoothLeOobData())
    val result = _result.asStateFlow()

    fun parser(payload: ByteBuffer): BluetoothLeOobData {
        val result = BluetoothLeOobData()
        try {
            var bdaddr: ByteArray?
            var flag: Byte?
            var leSecureConfirmation: ByteArray?
            var leSecureRandom: ByteArray?
            var appearance: ByteArray?
            var name: String?
            var securityManagerTK: ByteArray?
            while (payload.remaining() > 0) {
                val len = payload.get().toInt() - 1
                when (payload.get().toInt()) {
                    BT_HANDOVER_TYPE_MAC -> {
                        bdaddr = ByteArray(6) // 6 Bytes for the mac address and 1 byte for the address data type
                        payload[bdaddr]
                        val address = parseLittleEndianOrder(bdaddr)
                        _result.value = _result.value.copy(
                            bleDeviceAddress = bluetoothAdapter.getRemoteDevice(address),
                            bleAddressType = AddressType.parse(payload.get()) // 1 byte for the address data type
                        )
                    }

                    BT_HANDOVER_TYPE_LE_ROLE -> {
                        val roleType = LeRoleType.parse(payload.get())
                        _result.value = _result.value.copy(roleType = roleType)
                    }

                    BT_HANDOVER_TYPE_LONG_LOCAL_NAME -> {
                        val nameBytes = ByteArray(len)
                        payload[nameBytes]
                        name = String(nameBytes, StandardCharsets.UTF_8)
                        _result.value = _result.value.copy(localName = name)
                    }

                    BT_HANDOVER_TYPE_LONG_LOCAL_NAME_2 -> {
                        val nameBytes = ByteArray(len)
                        payload[nameBytes]
                        name = String(nameBytes, StandardCharsets.UTF_8)
                        _result.value = _result.value.copy(localName = name)
                    }

                    BT_HANDOVER_TYPE_SECURITY_MANAGER_TK -> {
                        if (len != SECURITY_MANAGER_TK_SIZE) {
                            Log.i(TAG, "BT OOB: invalid size of SM TK, should be $SECURITY_MANAGER_TK_SIZE bytes.")
                        } else {
                            securityManagerTK = ByteArray(len)
                            payload[securityManagerTK]
                            _result.value = _result.value.copy(securityManagerTK = parseLittleEndianOrder(securityManagerTK))
                        }
                    }

                    BT_HANDOVER_TYPE_LE_SC_CONFIRMATION -> {
                        if (len != SECURITY_MANAGER_LE_SC_C_SIZE) {
                            Log.i(TAG, "BT OOB: invalid size of LE SC Confirmation, should be $SECURITY_MANAGER_LE_SC_C_SIZE bytes.")
                        } else {
                            leSecureConfirmation = ByteArray(len)
                            payload[leSecureConfirmation]
                            _result.value = _result.value.copy(leSecureConnectionConfirmation = parseLittleEndianOrder(leSecureConfirmation))
                        }
                    }

                    BT_HANDOVER_TYPE_LE_SC_RANDOM -> {
                        if (len != SECURITY_MANAGER_LE_SC_R_SIZE) {
                            Log.i(TAG, "BT OOB: invalid size of LE SC Random, should be $SECURITY_MANAGER_LE_SC_R_SIZE bytes.")
                        } else {
                            leSecureRandom = ByteArray(len)
                            payload[leSecureRandom]
                            _result.value = _result.value.copy(leSecureConnectionRandom = parseLittleEndianOrder(leSecureRandom))
                        }
                    }

                    BT_HANDOVER_TYPE_LE_APPEARANCE -> {
                        appearance = ByteArray(len)
                        payload[appearance]
                        parseLittleEndianOrder(appearance)
                        _result.value = _result.value.copy(appearance = AppearanceParser.parse(parseLittleEndianOrder(appearance)))
                    }

                    BT_HANDOVER_TYPE_LE_FLAG -> {
                        flag = payload.get()
                        _result.value = _result.value.copy(flags = FlagByteParser.parse(flag))
                    }

                    else -> payload.position(payload.position() + len)
                }
            }
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, "BLE OOB: error parsing OOB data $e")
        } catch (e: BufferUnderflowException) {
            Log.e(TAG, "BT OOB: payload shorter than expected $e")
        }
        return result
    }

    /**
     * Parses the LITTLE_ENDIAN ByteArray.
     */
    private fun parseLittleEndianOrder(buffer: ByteArray): ByteArray = buffer.reversed().toByteArray()
}