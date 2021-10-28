package com.example.greenbook.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.greenbook.Database
import com.example.greenbook.R
import com.example.greenbook.dataObjekter.Arrangement
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*

class LagArrangementFragment : Fragment(R.layout.fragment_lag_arrangement) {

    lateinit var tittelTF: EditText
    lateinit var beskrivelseTF: EditText
    lateinit var stedTF: EditText
    lateinit var tidTF: EditText
    lateinit var plasserTF: EditText
    lateinit var btnLag: Button
    lateinit var dateImage: ImageView
    lateinit var dateTV:TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        user = auth.currentUser!!
        database = Database()

    }

    // TODO: 10/27/2021 Truls: Gjør om beskrivelse til et popupvindu? 
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        datepicker()

        tittelTF = view?.findViewById(R.id.txt_tittel)
        beskrivelseTF = view?.findViewById(R.id.txt_lag_arrangement_beskrivelse)
        stedTF = view?.findViewById(R.id.txt_sted)
        tidTF = view?.findViewById(R.id.txt_tid)
        plasserTF = view?.findViewById(R.id.txt_plasser)
        btnLag = view?.findViewById(R.id.btn_registrer)


        btnLag.setOnClickListener {
            val arr = Arrangement(
                null,
                user.uid,
                tittelTF.text.toString(),
                beskrivelseTF.text.toString(),
                stedTF.text.toString(),
                dateTV.text.toString(),
                tidTF.text.toString(),
                plasserTF.text.toString().toInt()
            )

            val arrangementId = database.addArrangement(arr)
            Log.i("huge", arrangementId)
            val action = LagArrangementFragmentDirections.actionLagArrangementFragmentToArrangementFragment(arrangementId)
            findNavController().navigate(action)

        }

    }

    // TODO: 10/27/2021 Truls: metode for å gjøre det ryddigerer, usikker på om det funker å kalle de der med !!
    fun datepicker() {
        // TODO: Truls lettere om du også gjøre textview klikkbar
        dateImage = view?.findViewById(R.id.lag_arrangement_dp_icon)!!
        dateTV = view?.findViewById(R.id.lag_arrangement_datepicker)!!

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        dateImage.setOnClickListener {
            val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay ->
                dateTV.setText(""+mDay+"/"+mMonth+"/"+mYear)
            }, year, month, day)
            dpd.show()
        }
    }
}
