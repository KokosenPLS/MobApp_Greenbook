package com.example.greenbook.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbook.Database
import com.example.greenbook.NavGraphDirections
import com.example.greenbook.R
import com.example.greenbook.adaptorClasses.ProfilAdaptor
import com.example.greenbook.dataObjekter.Profil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue


class DeltakereArrangementFragment : Fragment(R.layout.fragment_deltakere_arrangement), ProfilAdaptor.OnItemClickListener {

    private val args: DeltakereArrangementFragmentArgs by navArgs()

    private var deltakere = ArrayList<Profil>()
    private var brukerStrings = ArrayList<String>()
    private val database = Database()

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view?.findViewById(R.id.arrangementDeltakereRc)!!

        recyclerView?.layoutManager = LinearLayoutManager(view?.context)

        val gridLayout = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)

        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = gridLayout
        recyclerView?.adapter = ProfilAdaptor(deltakere, this)

        hentDeltakere()

    }

    private fun hentDeltakere() {
        val deltakereListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.hasChildren()){
                    for(bruker in snapshot.children){
                        val deltakerString = bruker.key
                        brukerStrings.add(deltakerString!!)
                    }
                    visDeltakere()
                }
                else{
                    // Ingen brukere
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        database.database.child("påmeldinger").child(args.arrangementID).addValueEventListener(deltakereListener)
    }

    private fun visDeltakere() {

        val deltakereListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val bruker = snapshot.getValue<Profil>()
                    deltakere.add(bruker!!)
                    Log.i("brukere", deltakere.size.toString())
                    recyclerView.adapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        for(bruker in brukerStrings){
           database.database.child("bruker").child(bruker).addValueEventListener(deltakereListener)

        }
        Log.i("array", deltakere.size.toString())
        Log.i("strings", brukerStrings.size.toString())
    }

    companion object {
        fun newInstance() = DeltakereArrangementFragment()
    }

    override fun onItemClick(position: Int) {
        val bruker = deltakere[position]
        val action = NavGraphDirections.actionGlobalProfilOffentlig(brukerStrings[position], "${bruker.fornavn} ${bruker.etternavn}")
        findNavController().navigate(action)
    }

}
