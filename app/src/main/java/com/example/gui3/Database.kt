package com.example.gui3

import com.example.gui3.dataObjekter.Profil
import com.example.gui3.dataObjekter.TestData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Database {
    private lateinit var database: DatabaseReference

    init {
        database = FirebaseDatabase.getInstance("https://greenbook-a2981-default-rtdb.europe-west1.firebasedatabase.app/").reference
    }

    fun addBruker(id: String, bruker: Profil){
        database.child("bruker").child(id).setValue(bruker)
    }

    fun addTestData(data: TestData){
        database.child("testdata").setValue(data)
    }

}