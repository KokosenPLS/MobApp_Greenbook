package com.example.greenbook

import com.example.greenbook.dataObjekter.Arrangement
import com.example.greenbook.dataObjekter.Profil
import com.google.firebase.database.*

class Database {
    lateinit var database: DatabaseReference

    init {
        database = FirebaseDatabase.getInstance("https://greenbook-a2981-default-rtdb.europe-west1.firebasedatabase.app/").reference
    }

    fun addBruker(id: String, bruker: Profil){
        database.child("bruker").child(id).setValue(bruker)
    }

    fun addArrangement(arrangement: Arrangement): String{
        val ref = database.child("arrangement")
        val arrId = ref.push().key
        val ferdigArrangement = Arrangement(
            arrId,
            arrangement.arrangør,
            arrangement.tittel,
            arrangement.beskrivelse,
            arrangement.sted,
            arrangement.dato,
            arrangement.tid,
            arrangement.plasser,
            arrangement.bildeUrl
        )
        ref.child(arrId!!).setValue(ferdigArrangement)
        database.child("arrangerer").child(ferdigArrangement.arrangør!!).child(arrId).setValue(true)
        meldBrukerPåArrangement(ferdigArrangement.arrangør, arrId)
        return arrId
    }

    fun meldBrukerPåArrangement(id: String, arrangementId: String){
        database.child("påmeldinger").child(arrangementId).child(id).setValue(true)
    }

    fun meldBrukerAvArrangement(id: String, arrangementId: String){
        database.child("påmeldinger").child(arrangementId).child(id).removeValue()
    }

    fun follow(id: String, følgerId: String){
        database.child("follows").child(id).setValue(følgerId) // bruker følger
        database.child("followers").child(følgerId).setValue(id) // folk som følger bruker
        // Kanskje litt dobbeltlagring, men blir lettere å lete igjennom data
    }

}