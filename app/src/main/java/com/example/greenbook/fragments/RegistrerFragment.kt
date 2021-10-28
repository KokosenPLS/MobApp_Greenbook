package com.example.greenbook.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.greenbook.Database
import com.example.greenbook.R
import com.example.greenbook.dataObjekter.Profil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*

class RegistrerFragment : Fragment(R.layout.fragment_registrer) {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: Database

    private lateinit var email: EditText
    private lateinit var passord: EditText
    private lateinit var passord2: EditText
    private lateinit var fornavn: EditText
    private lateinit var etternavn: EditText
    private lateinit var fdato: TextView
    private lateinit var datoKnapp: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        database = Database()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email = view?.findViewById(R.id.txt_email)
        passord = view?.findViewById(R.id.registrer_bruker_passord_1)
        passord2 = view?.findViewById(R.id.registrer_bruker_passord_2)
        fornavn = view?.findViewById(R.id.txt_tittel)
        etternavn = view?.findViewById(R.id.txt_etternavn)

        datepicker()

        view?.findViewById<Button>(R.id.btn_registrer).setOnClickListener {
            if (Patterns.EMAIL_ADDRESS.matcher(email.text).matches() && passord.text != null && passord2.getText().toString().equals( passord.getText().toString())) { // TODO: 10/28/2021 denne koden sto på slutten her: && isDatoValid()
                Log.i("key",passord2.text.equals(passord.text).toString())
                auth.createUserWithEmailAndPassword(email.text.toString(), passord.text.toString())
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val bruker = Profil(
                                email.text.toString(),
                                fornavn.text.toString(),
                                etternavn.text.toString(),
                                fdato.text.toString()
                            )
                            database.addBruker(user?.uid!!, bruker)
                            reload()
                        } else {
                            Toast.makeText(
                                context,
                                "Autentisering feilet: ${task.exception.toString()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }



    }

    fun datepicker() {
        // TODO: Truls lettere om du også gjøre textview klikkbar
        datoKnapp = view?.findViewById(R.id.registrer_bruker_dp_icon)!!
        fdato = view?.findViewById(R.id.registrer_bruker_datepicker)!!

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        datoKnapp.setOnClickListener {
            val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay ->
                fdato.setText(""+mDay+"/"+mMonth+"/"+mYear)
            }, year, month, day)
            dpd.show()
        }
    }

    // TODO: 10/28/2021 lar denne stå her, du kan da velge hva du skal gjøre med den
    /*private fun isDatoValid(): Boolean{

        val day = fdato_day.text.toString().toInt()
        val month = fdato_month.text.toString().toInt()
        val year = fdato_year.text.toString().toInt()

        if(year > 1900 && year < 2021-16)
            if(year % 4 == 0){
                // Skuddår
                if(month in 1..12)
                    when(month){
                        2 -> return day in 1..29
                        1,3,5,7,8,10,12 -> return day in 1..31
                        else -> return day in 1..30
                    }
            }
            else{ // Ikke skuddår
                if(month in 1..12)
                    when(month){
                        2 -> return day in 1..28
                        1,3,5,7,8,10,12 -> return day in 1..31
                        else -> return day in 1..30
                    }
            }

        return false
    }*/

    private fun reload(){
        activity?.finish()
        startActivity(activity?.intent)
    }


    companion object {
        fun newInstance(param1: String, param2: String) = RegistrerFragment()
    }
}