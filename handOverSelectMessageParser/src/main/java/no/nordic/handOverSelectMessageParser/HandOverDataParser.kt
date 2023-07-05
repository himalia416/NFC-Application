package no.nordic.handOverSelectMessageParser

import no.nordic.handOverSelectMessageParser.data.BluetoothLeOobData
import java.nio.ByteBuffer

interface HandOverDataParser {
    fun parser(payload: ByteBuffer): BluetoothLeOobData
}