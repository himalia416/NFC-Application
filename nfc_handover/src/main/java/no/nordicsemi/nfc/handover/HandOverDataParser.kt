package no.nordicsemi.nfc.handover

import no.nordicsemi.nfc.handover.data.BluetoothLeOobData
import java.nio.ByteBuffer

interface HandOverDataParser {
    fun parser(payload: ByteBuffer): BluetoothLeOobData
}