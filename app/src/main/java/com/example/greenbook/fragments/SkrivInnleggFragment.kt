package com.example.greenbook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.greenbook.Database
import com.example.greenbook.R
import com.example.greenbook.dataObjekter.Innlegg
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SkrivInnleggFragment : Fragment(R.layout.fragment_skriv_innlegg) {

    private val args: SkrivInnleggFragmentArgs by navArgs()
    private lateinit var tekst: EditText
    private lateinit var bilde: ImageView
    private lateinit var velg_bilde: Button
    private val pickerContent = registerForActivityResult(ActivityResultContracts.GetContent()){
        bilde.setImageURI(it)
    }
    private lateinit var complete: Button
    private lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tekst = view?.findViewById(R.id.skriv_innlegg_tekst)
        bilde = view?.findViewById(R.id.skriv_innlegg_bilde)
        velg_bilde = view?.findViewById(R.id.skriv_innlegg_btn_bilde)

        velg_bilde.setOnClickListener {
            pickerContent.launch("image/*")
        }

        complete = view?.findViewById(R.id.skriv_innlegg_btn_done)
        complete.setOnClickListener {

            if(tekst.text.isEmpty()){
                // TODO(Sjekke inputs (?))
            }
            else { // legge til i databasen
                var bildeURL: String? = null
                if (bilde.drawable != null) {
                    // legg til bilde i database, få url
                    // bildeURL = database.addBilde(bilde.drawable)
                }
                val user = Firebase.auth.currentUser
                database = Database()
                database.addInnlegg(Innlegg(null, args.arrangementId, user?.uid!!, tekst.text.toString(), bildeURL))
                findNavController().navigateUp()
            }
        }

    }

}