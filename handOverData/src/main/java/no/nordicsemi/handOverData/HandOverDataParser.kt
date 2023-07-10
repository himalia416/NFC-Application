package no.nordicsemi.handOverData

import no.nordicsemi.handOverData.data.BluetoothLeOobData
import java.nio.ByteBuffer

interface HandOverDataParser {
    fun parser(payload: ByteBuffer): BluetoothLeOobData
}