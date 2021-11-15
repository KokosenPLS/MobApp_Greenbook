package com.example.greenbook.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbook.Database
import com.example.greenbook.adaptorClasses.PostAdaptor
import com.example.greenbook.viewModels.FeedViewModel
import com.example.greenbook.R
import com.example.greenbook.dataObjekter.Arrangement
import com.example.greenbook.dataObjekter.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class FeedFragment() : Fragment(R.layout.feed_fragment), PostAdaptor.OnItemClickListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var database: Database
    private lateinit var viewModel: FeedViewModel

    var arrangement: ArrayList<Arrangement> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)

        auth = Firebase.auth
        user = auth.currentUser!!
        database = Database()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        hentArrangementer()
    }

    fun hentArrangementer(){
        arrangement = ArrayList()
        val arrangementListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(arr in snapshot.children){
                    val arra= arr.getValue<Arrangement>()
                    arrangement.add(arra!!)
                }
                update(arrangement)
            }
            override fun onCancelled(error: DatabaseError) {

            }
        }
        database.database.child("arrangement").addValueEventListener(arrangementListener)
    }

    private fun update(arr: ArrayList<Arrangement>){

        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView?.layoutManager = LinearLayoutManager(view?.context)
        recyclerView?.adapter = PostAdaptor(arr, this)
    }

    override fun onItemClick(position: Int) {
        val arrangement = arrangement[position]
        val action = FeedFragmentDirections.actionFeedFragmentToArrangementFragment(arrangement.arrangementId!!, arrangement.tittel!!)
        findNavController().navigate(action)

    }
}