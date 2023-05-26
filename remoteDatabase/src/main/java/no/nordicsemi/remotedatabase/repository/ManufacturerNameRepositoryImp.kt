package no.nordicsemi.remotedatabase.repository

import no.nordicsemi.remotedatabase.domain.ManufacturerName
import no.nordicsemi.remotedatabase.domain.ManufacturerNameRepository
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class ManufacturerNameRepositoryImp @Inject constructor(
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