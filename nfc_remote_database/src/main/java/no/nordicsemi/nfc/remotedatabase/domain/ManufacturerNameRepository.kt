package no.nordicsemi.nfc.remotedatabase.domain

interface ManufacturerNameRepository {
    suspend fun getManufacturerName(identifier: String): ManufacturerName?
}