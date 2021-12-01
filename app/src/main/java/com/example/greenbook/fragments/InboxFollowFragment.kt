package com.example.greenbook.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbook.Database
import com.example.greenbook.adaptorClasses.ProfilAdaptor
import com.example.greenbook.R
import com.example.greenbook.dataObjekter.Profil
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class InboxFollowFragment : Fragment(R.layout.fragment_inbox_follow), ProfilAdaptor.OnItemClickListener {

    private lateinit var posts: ArrayList<Profil>
    private val database = Database()
    val user = Firebase.auth.currentUser!!
    private lateinit var adaptor: ProfilAdaptor

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        posts= ArrayList()
        adaptor = ProfilAdaptor(posts, this)
        val profiler = object : ValueEventListener {
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

        database.database.child("follows").child(user.uid).addValueEventListener(profiler)

        recyclerView = view.findViewById(R.id.inboxFollowRecyclerView)
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