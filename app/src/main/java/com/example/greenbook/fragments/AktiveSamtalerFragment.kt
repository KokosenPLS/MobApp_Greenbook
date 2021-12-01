package com.example.greenbook.fragments

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbook.Database
import com.example.greenbook.adaptorClasses.ProfilAdaptor
import com.example.greenbook.R
import com.example.greenbook.dataObjekter.Profil
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class AktiveSamtalerFragment : Fragment(R.layout.fragment_aktive_samtaler), ProfilAdaptor.OnItemClickListener {

    private lateinit var posts: ArrayList<Profil>
    private val database = Database()
    val user = Firebase.auth.currentUser!!

    private lateinit var adaptor: ProfilAdaptor

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        posts = ArrayList()

        val samtaler = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                posts.clear()
                if(snapshot.hasChildren()){
                    for (verdi in snapshot.children)
                        hentProfil(verdi.key.toString())
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        }

        database.database.child("meldinger").child(user.uid).addValueEventListener(samtaler)

        val recyclerView = view?.findViewById<RecyclerView>(R.id.aktiveSamtalerRecyclerView)
        adaptor = ProfilAdaptor(posts, this)
        val gridLayout = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)

        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = gridLayout
        recyclerView?.adapter = adaptor
    }

    private fun hentProfil(id: String){

        val hentProfil = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var profil = snapshot.getValue<Profil>()!!
                profil.brukerId = id
                posts.add(profil)
                adaptor.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        database.database.child("bruker").child(id).addValueEventListener(hentProfil)
    }

    override fun onItemClick(position: Int) {
        val navn = "${posts[position].fornavn} ${posts[position].etternavn}"
        val action = InboxFragmentDirections.actionInboxFragmentToChatFragment(posts[position].brukerId!!, navn)
        findNavController().navigate(action)
    }
}