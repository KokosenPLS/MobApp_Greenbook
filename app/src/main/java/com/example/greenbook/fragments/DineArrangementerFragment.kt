package com.example.greenbook.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbook.Database
import com.example.greenbook.R
import com.example.greenbook.adaptorClasses.PostAdaptor
import com.example.greenbook.dataObjekter.Arrangement
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class DineArrangementerFragment : Fragment(R.layout.fragment_dine_arrangementer), PostAdaptor.OnItemClickListener {

    private lateinit var database: Database
    private lateinit var user: FirebaseUser
    private lateinit var arrangement :ArrayList<Arrangement>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Database()
        user = Firebase.auth.currentUser!!
        arrangement = ArrayList()
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hentArrangementer()



    }

    private fun hentArrangementer(){
        val mineArrListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                val arrangementListener = object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val arr = snapshot.getValue<Arrangement>()
                        updateList(arr!!)
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                }

                for(arr in snapshot.children){
                    database.database.child("arrangement").child(arr.key!!).addValueEventListener(arrangementListener)
                }

            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        database.database.child("arrangerer").child(user.uid).addValueEventListener(mineArrListener)

    }

    private fun updateList(arrangement: Arrangement){
        this.arrangement.add(arrangement)
        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.dine_arrangement_rc)

        recyclerView?.layoutManager = LinearLayoutManager(view?.context)
        recyclerView?.adapter = PostAdaptor(this.arrangement, this)
    }

    override fun onItemClick(position: Int) {
        val arrangement = arrangement[position]
        val action = DineArrangementerFragmentDirections.actionDineArrangementerFragmentToArrangementFragment(arrangement.arrangementId!!, arrangement.tittel!!)
        findNavController().navigate(action)
    }

}