package com.example.nfcapplication.database

import android.util.Log
import com.example.nfcapplication.viewmodel.ManufacturerName
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

sealed class Resource<T>(val data: T? = null, val error: String? = null) {
    class Success<T>(data: T) : Resource<T>(data = data)
    class Error<T>(error: String, data: T? = null) : Resource<T>(data = data, error = error)
}

interface ManufacturerNameRepository {
    fun getManufacturerNameOnce(): Flow<Resource<List<ManufacturerName>>>
    fun getManufacturerNameRealtime(): Flow<Resource<List<ManufacturerName>>>
}

class ManufacturerNameRepositoryImp(
    private val database: FirebaseDatabase
) : ManufacturerNameRepository {
    override fun getManufacturerNameOnce(): Flow<Resource<List<ManufacturerName>>> = callbackFlow {
        try {
            val m = database.reference.child("/0/manufacturerName").get().await().getValue<List<ManufacturerName>>()
            database.getReference("/0/manufacturerName")
                .get()
                .await().children.forEach {name ->
                    val manufacturerName = name.getValue<List<ManufacturerName>>()
                    Log.d("TAG", "getManufacturerNameOnce: ")
                    manufacturerName?.forEach {
                        Log.d("TAG", "getManufacturerNameOnce: ${it.company}")
                    }
                    manufacturerName?.let { Resource.Success(it) }
                }
//            Resource.Success(manufacturerName)
//                .addOnCompleteListener { task ->
//                    val response = if (task.isSuccessful) {
//                        val manufacturerName = task.result.getValue<List<ManufacturerName>>()!!
//                        Resource.Success(manufacturerName)
//                    } else {
//                        Resource.Error(task.exception?.localizedMessage.toString())
//                    }
//                    trySend(response).isSuccess
//                }
//
//            awaitClose {
//                close()
//            }
        }  catch (e: Exception) {
            Log.e("TAG", "getManufacturerNameOnce: Error on downloading data!", e)
        }
        awaitClose {
                close()
            }
    }

    override fun getManufacturerNameRealtime(): Flow<Resource<List<ManufacturerName>>> =
        callbackFlow {
            database.getReference("/0/manufacturerName")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val manufacturerName = dataSnapshot.getValue<List<ManufacturerName>>()!!
                        trySend(Resource.Success(manufacturerName)).isSuccess
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.w("manufacturerName", "Failed to read value.")
                        trySend(Resource.Error(error = error.message)).isFailure
                    }
                })

            awaitClose {
                close()
            }
        }
}