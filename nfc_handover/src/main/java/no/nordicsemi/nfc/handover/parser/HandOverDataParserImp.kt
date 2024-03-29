package no.nordicsemi.nfc.handover.parser

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import no.nordicsemi.nfc.handover.HandOverDataParser
import no.nordicsemi.nfc.handover.data.AddressType
import no.nordicsemi.nfc.handover.data.BluetoothLeOobData
import no.nordicsemi.nfc.handover.data.BtHandOverType.LE_APPEARANCE
import no.nordicsemi.nfc.handover.data.BtHandOverType.LE_FLAG
import no.nordicsemi.nfc.handover.data.BtHandOverType.LE_ROLE
import no.nordicsemi.nfc.handover.data.BtHandOverType.LE_SC_CONFIRMATION
import no.nordicsemi.nfc.handover.data.BtHandOverType.LE_SC_CONFIRMATION_SIZE
import no.nordicsemi.nfc.handover.data.BtHandOverType.LE_SC_RANDOM
import no.nordicsemi.nfc.handover.data.BtHandOverType.LE_SC_RANDOM_SIZE
import no.nordicsemi.nfc.handover.data.BtHandOverType.LOCAL_NAME
import no.nordicsemi.nfc.handover.data.BtHandOverType.LOCAL_NAME_2
import no.nordicsemi.nfc.handover.data.BtHandOverType.SECURITY_MANAGER_TK
import no.nordicsemi.nfc.handover.data.BtHandOverType.SECURITY_MANAGER_TK_SIZE
import no.nordicsemi.nfc.handover.data.BtHandOverType.TYPE_MAC
import no.nordicsemi.nfc.handover.data.LeRoleType
import no.nordicsemi.nfc.utils.DataByteArray
import java.nio.BufferUnderflowException
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import javax.inject.Singleton

@RequiresApi(Build.VERSION_CODES.M)
@Singleton
internal class HandOverDataParserImp @Inject constructor(
    private val bluetoothAdapter: BluetoothAdapter,
) : HandOverDataParser {
    private val TAG = "HandoverMessageParser"

    private lateinit var bleAddress: BluetoothDevice
    private lateinit var bleAddressType: String
    private lateinit var leRoleType: LeRoleType
    private var localName: String? = null
    private var securityManagerTK: DataByteArray? = null
    private var leSecureConfirmation: DataByteArray? = null
    private var leSecureRandom: DataByteArray? = null
    private var appearance: String? = null
    private var flags: List<String> = emptyList()

    override fun parser(payload: ByteBuffer): BluetoothLeOobData {
        try {
            var bytes: ByteArray?
            var flag: Byte?
            while (payload.remaining() > 0) {
                val len = payload.get().toInt() - 1
                when (payload.get().toInt()) {
                    TYPE_MAC -> {
                        bytes =
                            ByteArray(6) // 6 Bytes for the mac address and 1 byte for the address data type
                        payload[bytes]
                        val address = parseLittleEndianOrder(bytes)
                        bleAddress = bluetoothAdapter.getRemoteDevice(address)
                        bleAddressType = AddressType.parse(payload.get())
                    }

                    LE_ROLE -> {
                        leRoleType = LeRoleType.parse(payload.get())
                    }

                    LOCAL_NAME -> {
                        val nameBytes = ByteArray(len)
                        payload[nameBytes]
                        localName = String(nameBytes, StandardCharsets.UTF_8)
                    }

                    LOCAL_NAME_2 -> {
                        val nameBytes = ByteArray(len)
                        payload[nameBytes]
                        localName = String(nameBytes, StandardCharsets.UTF_8)
                    }

                    SECURITY_MANAGER_TK -> {
                        if (len != SECURITY_MANAGER_TK_SIZE) {
                            Log.i(
                                TAG,
                                "BT OOB: invalid size of SM TK, should be $SECURITY_MANAGER_TK_SIZE bytes."
                            )
                        } else {
                            bytes = ByteArray(len)
                            payload[bytes]
                            securityManagerTK = DataByteArray(parseLittleEndianOrder(bytes))
                        }
                    }

                    LE_SC_CONFIRMATION -> {
                        if (len != LE_SC_CONFIRMATION_SIZE) {
                            Log.i(
                                TAG,
                                "BT OOB: invalid size of LE SC Confirmation, should be $LE_SC_CONFIRMATION_SIZE bytes."
                            )
                        } else {
                            bytes = ByteArray(len)
                            payload[bytes]
                            leSecureConfirmation = DataByteArray(parseLittleEndianOrder(bytes))
                        }
                    }

                    LE_SC_RANDOM -> {
                        if (len != LE_SC_RANDOM_SIZE) {
                            Log.i(
                                TAG,
                                "BT OOB: invalid size of LE SC Random, should be $LE_SC_RANDOM_SIZE bytes."
                            )
                        } else {
                            bytes = ByteArray(len)
                            payload[bytes]
                            leSecureRandom = DataByteArray(parseLittleEndianOrder(bytes))
                        }
                    }

                    LE_APPEARANCE -> {
                        bytes = ByteArray(len)
                        payload[bytes]
                        appearance = AppearanceParser.parse(parseLittleEndianOrder(bytes))
                    }

                    LE_FLAG -> {
                        flag = payload.get()
                        flags = FlagByteParser.parse(flag)
                    }

                    else -> payload.position(payload.position() + len)
                }
            }
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, "BLE OOB: error parsing OOB data $e")
        } catch (e: BufferUnderflowException) {
            Log.e(TAG, "BT OOB: payload shorter than expected $e")
        }
        return BluetoothLeOobData(
            bleDeviceAddress = bleAddress,
            bleAddressType = bleAddressType,
            roleType = leRoleType,
            securityManagerTK = securityManagerTK,
            leSecureConnectionConfirmation = leSecureConfirmation,
            leSecureConnectionRandom = leSecureRandom,
            appearance = appearance,
            flags = flags,
            localName = localName,
        )
    }

    /**
     * Parses the LITTLE_ENDIAN ByteArray.
     */
    private fun parseLittleEndianOrder(buffer: ByteArray): ByteArray =
        buffer.reversed().toByteArray()
}