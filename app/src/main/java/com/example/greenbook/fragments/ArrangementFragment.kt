package com.example.greenbook.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.greenbook.Database
import com.example.greenbook.R
import com.example.greenbook.dataObjekter.Arrangement
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class ArrangementFragment : Fragment(R.layout.fragment_arrangement) {

    private val args: ArrangementFragmentArgs by navArgs()
    private lateinit var database: Database
    private lateinit var arrangement: Arrangement
    private lateinit var user: FirebaseUser

    private lateinit var tittel: TextView
    private lateinit var beskrivelse: TextView
    private lateinit var påmeldte: Button
    private lateinit var btn_join: Button
    private lateinit var skrivInlegg: Button
    private lateinit var googleMapsImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Database()
        user = Firebase.auth.currentUser!!

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tittel = view?.findViewById(R.id.arrangement_tittel)
        beskrivelse = view?.findViewById(R.id.arrangement_txt_beskrivelse)
        påmeldte = view?.findViewById(R.id.arrangement_påmeldte)
        btn_join = view?.findViewById(R.id.arrangement_btn_blimed)
        skrivInlegg = view?.findViewById(R.id.arrangement_btn_skriv_innlegg)
        googleMapsImage = view?.findViewById(R.id.arrangement_goToGoogleMaps)

        updateUI()

        btn_join.setOnClickListener {
            if(btn_join.text.equals("Bli med")){

                database.meldBrukerPåArrangement(user.uid, args.arrangementID)
            }
            else{
                database.meldBrukerAvArrangement(user.uid, args.arrangementID)
            }
        }

        googleMapsImage.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=-33.8666,151.1957")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

    }

    private fun updateUI(){
        val arrangementListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val arr = snapshot.getValue<Arrangement>()!!
                update(arr!!)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        database.database.child("arrangement").child(args.arrangementID).addValueEventListener(arrangementListener)

        val deltakereListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val joined = snapshot.childrenCount.toInt()
                if(joined == 1)
                    påmeldte.text = joined.toString() + " påmeldt"
                else
                    påmeldte.text = joined.toString() + " påmeldte"

                if(snapshot.hasChild(user.uid))
                    btn_join.text =  "Påmeldt"
                else
                    btn_join.text =  "Bli med"
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }

        database.database.child("påmeldinger").child(args.arrangementID).addValueEventListener(deltakereListener)

    }

    private fun update(arrangement: Arrangement){
        tittel.text = arrangement.tittel
        beskrivelse.text = (
                        "Sted: " + arrangement.sted + "\n"+
                        "Tid: " + arrangement.tid + "\n"+
                        arrangement.beskrivelse
                )
    }
}

