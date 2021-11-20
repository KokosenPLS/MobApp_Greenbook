package com.example.greenbook.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
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


class FoolgerFragment : Fragment(R.layout.fragment_foolger), ProfilAdaptor.OnItemClickListener {

    private val args:FoolgerFragmentArgs by navArgs()

    private var foolgere = ArrayList<Profil>()
    private var brukerStrings = ArrayList<String>()
    private val database = Database()
    private lateinit var recyclerView: RecyclerView
    private lateinit var textView_foolgere:TextView
    private lateinit var textView_DeDuFoolger:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view?.findViewById(R.id.arrangementDeltakereRc)!!
        recyclerView?.layoutManager = LinearLayoutManager(view?.context)
        textView_foolgere = view?.findViewById(R.id.foolgere_foolgere)!!
        textView_DeDuFoolger = view?.findViewById(R.id.foolgere_DuFoolger)!!


        val gridLayout = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = gridLayout
        val adaptor = ProfilAdaptor(foolgere, this)
        recyclerView.adapter =  adaptor

        hentFoolgere(adaptor,"followers")

        textView_foolgere.setOnClickListener {
            recyclerView.visibility = View.VISIBLE
            foolgere.clear()
            brukerStrings.clear()
            hentFoolgere(adaptor,"followers")
            textView_foolgere.setBackgroundResource(R.color.greenbook_selected)
            textView_DeDuFoolger.setBackgroundResource(R.color.greenbook)
        }

        textView_DeDuFoolger.setOnClickListener {
            recyclerView.visibility = View.VISIBLE
            foolgere.clear()
            brukerStrings.clear()
            hentFoolgere(adaptor,"follows")
            textView_DeDuFoolger.setBackgroundResource(R.color.greenbook_selected)
            textView_foolgere.setBackgroundResource(R.color.greenbook)
        }

    }

    private fun hentFoolgere(adaptor: ProfilAdaptor, databaseChild:String) {
        val profilListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val bruker = snapshot.getValue<Profil>()
                    var found = false
                    for(user in foolgere){
                        if (user.equals(bruker))
                            found = true
                    }
                    if(!found)
                        foolgere.add(bruker!!)

                    adaptor.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        val foolgerListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.hasChildren()){
                    for(bruker in snapshot.children){
                        val brukerString = bruker.key
                        brukerStrings.add(brukerString!!)
                        database.database.child("bruker").child(brukerString!!).addValueEventListener(profilListener)
                    }
                }
                else{
                    // Ingen brukere
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        database.database.child(databaseChild).child(args.brukerId).addValueEventListener(foolgerListener)
    }


    companion object {
        fun newInstance() = DeltakereArrangementFragment()
    }

    override fun onItemClick(position: Int) {
        val bruker = foolgere[position]
        val action = NavGraphDirections.actionGlobalProfilOffentlig(brukerStrings[position], "${bruker.fornavn} ${bruker.etternavn}")
        findNavController().navigate(action)
    }
}