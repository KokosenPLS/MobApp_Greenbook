package com.example.greenbook.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.greenbook.Database
import com.example.greenbook.R
import com.example.greenbook.dataObjekter.Profil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


class ProfilOffentlig : Fragment() {

    private val args: ProfilOffentligArgs by navArgs()
    private lateinit var profilBilde: ImageView
    private lateinit var navnTV: TextView
    private lateinit var btn_displayFølgere: Button
    private lateinit var btn_følg:Button
    private lateinit var btn_melding:Button
    private lateinit var bioTV:TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var database: Database


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        user = auth.currentUser!!
        database = Database()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profil_offentlig, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profilBilde= requireView().findViewById(R.id.profil_offentlig_bilde) //bilde
        navnTV= requireView().findViewById(R.id.profil_offentlig_navn) //navn
        btn_displayFølgere= requireView().findViewById(R.id.profil_offentlig_følgere) //knapp_følgere
        btn_følg= requireView().findViewById(R.id.profil_offentlig_btn_følg)//knapp følg
        btn_melding= requireView().findViewById(R.id.profil_offentlig_melding) //knapp melding
        bioTV= requireView().findViewById(R.id.profil_offentlig_bio) //bio

        btn_følg.setOnClickListener {
            if(btn_følg.text.equals("Følg")){
                if (args.brukerID!=auth.uid.toString())
                    database.follow(auth.uid.toString(), args.brukerID)
                else
                    Toast.makeText(context, "Du kan ikke følge deg selv", Toast.LENGTH_SHORT).show()
            }
            else{
                if (args.brukerID!=auth.uid.toString())
                    database.stopFollow(auth.uid.toString(),args.brukerID)
            }
        }
        updateUI()
    }

    private fun updateUI(){
        val profilListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val profil = snapshot.getValue<Profil>()!!
                update(profil!!)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        database.database.child("bruker").child(args.brukerID).addValueEventListener(profilListener)

        val følgereListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val joined = snapshot.childrenCount.toInt()
                if(joined == 1)
                    btn_displayFølgere.text = joined.toString() + "følgere"
                else
                    btn_displayFølgere.text = joined.toString() + " følger"

                if(snapshot.hasChild(auth.uid.toString()))
                    btn_følg.text =  "Følger"
                else
                    btn_følg.text =  "Følg"
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        database.database.child("followers").child(args.brukerID).addValueEventListener(følgereListener)
    }

    @SuppressLint("SetTextI18n")
    private fun update(profil: Profil){
        navnTV.text = profil.fornavn + " " + profil.etternavn
        bioTV.setText(profil.bio)
        if(profil.imgUrl !=null)
            Picasso.get().load(profil.imgUrl).into(profilBilde)
    }

    companion object {
    }
}