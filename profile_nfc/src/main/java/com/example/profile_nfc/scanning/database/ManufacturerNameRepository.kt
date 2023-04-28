package com.example.profile_nfc.scanning.database

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

data class ManufacturerName(
    val identifier: String = "",
    val company: String = "",
    val country: String = "",
)

interface ManufacturerNameRepository {
    suspend fun getManufacturerName(identifier: String): ManufacturerName?
}

class ManufacturerNameRepositoryImp @Inject constructor(
    private val databaseReference: DatabaseReference,
) : ManufacturerNameRepository {

    override suspend fun getManufacturerName(identifier: String): ManufacturerName? {
        try {
            databaseReference
                .get()
                .await()
                .children.forEach { dataSnapshot ->
                    if (dataSnapshot.getValue<ManufacturerName>()!!.identifier.equals(
                            identifier,
                            ignoreCase = true
                        )
                    ) {
                        return dataSnapshot.getValue<ManufacturerName>()!!
                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}