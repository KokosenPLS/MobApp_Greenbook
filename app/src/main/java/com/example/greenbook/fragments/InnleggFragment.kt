package com.example.greenbook.fragments

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.example.greenbook.Database
import com.example.greenbook.R
import com.example.greenbook.dataObjekter.Innlegg
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class InnleggFragment : Fragment(R.layout.fragment_innlegg) {

    private val args: InnleggFragmentArgs by navArgs()

    private lateinit var text: TextView
    private lateinit var kommentarInput: EditText
    private lateinit var btnKommenter: Button

    private lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        text = view?.findViewById(R.id.innlegg_tekst)
        kommentarInput = view?.findViewById(R.id.innlegg_input_kommentar)
        btnKommenter = view?.findViewById(R.id.innlegg_btn_kommenter)

        btnKommenter.setOnClickListener {
            postKommentar()
        }

        database = Database()
        val innleggListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val innlegg = snapshot.getValue<Innlegg>()
                text.text = innlegg?.innlegg_beskrivelse
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        database.database.child("innlegg").child(args.arrangementId).child(args.innleggId).addValueEventListener(innleggListener)
    }

    private fun postKommentar() {
        // TODO lage kommentar
    }

}