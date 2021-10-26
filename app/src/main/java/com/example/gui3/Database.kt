package com.example.gui3

import com.example.gui3.dataObjekter.Profil
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Database {

    private var database: DatabaseReference = Firebase.database.reference

    fun addBruker(bruker: Profil){
        database.child("bruker").setValue(bruker.brukerID)
        database.child("bruker").child(bruker.brukerID!!).setValue(bruker)
    }

}