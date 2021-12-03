package com.example.greenbook.fragments

import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbook.Database
import com.example.greenbook.NavGraphDirections
import com.example.greenbook.R
import com.example.greenbook.adaptorClasses.KommentarAdaptor
import com.example.greenbook.dataObjekter.Innlegg
import com.example.greenbook.dataObjekter.Kommentar
import com.example.greenbook.dataObjekter.Profil
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import java.sql.Timestamp

class InnleggFragment : Fragment(R.layout.fragment_innlegg), KommentarAdaptor.OnItemClickListener {

    private val args: InnleggFragmentArgs by navArgs()

    private lateinit var text: TextView
    private lateinit var bilde: ImageView
    private lateinit var kommentarInput: EditText
    private lateinit var btnKommenter: Button
    private lateinit var recyclerView: RecyclerView

    private lateinit var database: Database
    var kommentarer = ArrayList<Kommentar>()
    val kommentarAdaptor = KommentarAdaptor(kommentarer, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun hentKommentarer() {
        val kommentarListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                kommentarer.clear()
                for(kommentar in snapshot.children ){
                    kommentarer.add(kommentar.getValue<Kommentar>()!!)
                }
                kommentarAdaptor.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }

        database.database.child("kommentarer").child(args.innleggId).addValueEventListener(kommentarListener)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        text = view?.findViewById(R.id.innlegg_tekst)
        kommentarInput = view?.findViewById(R.id.innlegg_input_kommentar)
        btnKommenter = view?.findViewById(R.id.innlegg_btn_kommenter)
        bilde = view?.findViewById(R.id.innlegg_display_bilde)
        recyclerView = view?.findViewById(R.id.innlegg_kommentar_rv)

        recyclerView.layoutManager = LinearLayoutManager(view?.context)
        recyclerView.adapter = kommentarAdaptor

        btnKommenter.setOnClickListener {
            if(!text.text.isEmpty())
                postKommentar()
        }

        database = Database()
        val innleggListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val innlegg = snapshot.getValue<Innlegg>()
                text.text = innlegg?.innlegg_beskrivelse
                Picasso.get().load(innlegg?.bildeURL).into(bilde)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        database.database.child("innlegg").child(args.arrangementId).child(args.innleggId).addValueEventListener(innleggListener)

        hentKommentarer()

    }

    private fun postKommentar() {
        val user = Firebase.auth.currentUser
        val kommentar = Kommentar(
            null,
            args.innleggId,
            user?.uid,
            kommentarInput.text.toString(),
            Timestamp(System.currentTimeMillis()).toString()
        )

        database.addKommentar(kommentar)
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
        kommentarInput.text.clear()
    }

    override fun onItemClick(position: Int) {
        val brukerId = kommentarer[position].brukerId
        val brukerListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val bruker = snapshot.getValue<Profil>()
                val navn = bruker?.fornavn + " " + bruker?.etternavn
                val action = NavGraphDirections.actionGlobalProfilFragment(brukerId!!,navn)
                findNavController().navigate(action)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        database = Database()
        database.database.child("bruker").child(brukerId!!).addValueEventListener(brukerListener)
    }

}