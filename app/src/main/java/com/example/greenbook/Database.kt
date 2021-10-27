package com.example.greenbook

import com.example.greenbook.dataObjekter.Arrangement
import com.example.greenbook.dataObjekter.Profil
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

    fun addArrangement(arrangement: Arrangement){
        val ref = database.child("arrangement")
        val arrId = ref.push().key
        ref.child(arrId!!).setValue(arrangement)

    }

    fun meldBrukerPåArrangement(id: String, arrangementId: String){
        database.child("påmeldinger").child(arrangementId).setValue(id)
    }

    // TODO: 10/27/2021 denne har jeg ikke laget, dette må gjøres 
    fun lagArrangement(id:String,arr:Arrangement) {
        
    }

    fun follow(id: String, følgerId: String){
        database.child("follows").child(id).setValue(følgerId) // bruker følger
        database.child("followers").child(følgerId).setValue(id) // folk som følger bruker
        // Kanskje litt dobbeltlagring, men blir lettere å lete igjennom data
    }

    fun getBruker(id: String): Profil?{

        var bruker: Profil? = null

        database.child("bruker").child(id).get().addOnSuccessListener {

            if(it.exists()){
                val email = it.child("email").value.toString()
                val fornavn = it.child("fornavn").value.toString()
                val etternavn = it.child("etternavn").value.toString()
                val fdato = it.child("fdato").value.toString()
                bruker = Profil(email, fornavn, etternavn, fdato)
            }


        }

        return bruker
    }

    /*
    fun getArrangment(arrangementId: String): Arrangement?{

        var arrangement: Arrangement? = null

        database.child("arrangement").child(arrangementId).get().addOnSuccessListener {

            if (it.exists()){
                val tittel = it.child("tittel").value.toString()
                val sted = it.child("sted").value.toString()
                val timestamp = it.child("timestamp").value.toString()
                val plasser = it.child("plasser").value.toString().toInt()
                val bildeUrl: String? = it.child("bildeUrl").value.toString()

                arrangement = Arrangement(tittel, sted, timestamp, plasser, bildeUrl)

            }

        }
        return arrangement
    }
    */
}