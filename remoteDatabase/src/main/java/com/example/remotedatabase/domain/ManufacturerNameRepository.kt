package com.example.remotedatabase.domain

interface ManufacturerNameRepository {
    suspend fun getManufacturerName(identifier: String): ManufacturerName?
}