package com.example.nfcapplication.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseTesting {
    private lateinit var databaseInstance: DatabaseReference
    val database = Firebase.database
    val myRef = database.reference

    val databaseUrl = "https://nfc-application-dcc66-default-rtdb.europe-west1.firebasedatabase.app/0/manufacturerName"
}