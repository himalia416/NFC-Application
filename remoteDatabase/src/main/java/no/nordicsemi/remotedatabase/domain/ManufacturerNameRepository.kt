package no.nordicsemi.remotedatabase.domain

interface ManufacturerNameRepository {
    suspend fun getManufacturerName(identifier: String): ManufacturerName?
}