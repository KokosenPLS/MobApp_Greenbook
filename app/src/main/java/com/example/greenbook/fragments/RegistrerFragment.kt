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
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.security.Key
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.regex.Pattern

class RegistrerFragment : Fragment(R.layout.fragment_registrer) {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: Database

    private lateinit var email: TextInputLayout
    private lateinit var passord: TextInputLayout
    private lateinit var passordBekreft: TextInputLayout
    private lateinit var fornavn: TextInputLayout
    private lateinit var etternavn: TextInputLayout
    private lateinit var fdato: TextView
    private lateinit var datoKnapp: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        database = Database()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email = view?.findViewById(R.id.register_input_email)
        passord = view?.findViewById(R.id.register_input_passord)
        passordBekreft = view?.findViewById(R.id.register_input_passord_confirm)
        fornavn = view?.findViewById(R.id.register_input_fornavn)
        etternavn = view?.findViewById(R.id.register_input_etternavn)

        datepicker(view)

        view.findViewById<Button>(R.id.btn_registrer).setOnClickListener {
            validateInput()
        }
    }

    fun validateEmail(): Boolean{
        val emailInput = email.editText?.text.toString().trim()
        if(emailInput.isEmpty()){
            email.error = "Epost kan ikke være tom"
            return false
        }
        else if(!isEmailValid(emailInput)){
            email.error = "Ugyldig epost"
            return false
        }
        else{
            email.error = null
            return true
        }
    }

    fun validatePassord(): Boolean{
        val passordInput = passord.editText?.text.toString().trim()
        val passordInputBekreft = passordBekreft.editText?.text.toString().trim()
        val passwordREGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\\\S+\$)")
        if(passordInput.isEmpty()){
            passord.error = "Passord kan ikke være tomt"
            return false
        }
        else if(passordInput.length < 6){
            passord.error = "Passord må ha 6 eller flere karakterer"
            return false
        }/*
        else if (!passwordREGEX.matcher(passordInput).matches()){
            Log.i("passord", passordInput)
            passord.error = "Passord må inneholde minst 1 eller flere store bokstaver og tall"
            return false
        }*/
        else if(!passordInput.equals(passordInputBekreft)){
            Log.i("passord", "matcher ikke")
            passordBekreft.error = "Passord matcher ikke"
            return false
        }
        else{
            passord.error = null
            passordBekreft.error = null
            return true
        }
    }

    fun validateFornavn(): Boolean{
        val fornavnInput = fornavn.editText?.text.toString().trim()
        if(fornavnInput.isEmpty()){
            fornavn.error = "Fornavn kan ikke være tomt"
            return false
        }
        else{
            fornavn.error = null
            return true
        }
    }

    fun validateEtternavn(): Boolean{
        val etternavnInput = etternavn.editText?.text.toString().trim()
        if(etternavnInput.isEmpty()){
            etternavn.error = "Etternavn kan ikke være tomt"
            return false
        }
        else{
            etternavn.error = null
            return true
        }
    }

    fun validateFdato(): Boolean{
        if(fdato.text.isEmpty()){
            fdato.hint = "Du må velge en fødselsdato"
            fdato.setHintTextColor(resources.getColor(R.color.error))
            return false
        }
        else if (fdato.text.toString().split("/".toRegex())[2].toInt() > Calendar.getInstance().get(Calendar.YEAR) - 13){
            fdato.hint = "Du er ikke gammel nok til å registrere deg"
            fdato.text = null
            fdato.setHintTextColor(resources.getColor(R.color.error))
            return false
        }
        else{
            Log.i("confirm", "ok")
            return true
        }
    }

    fun validateInput() {

        if(!validateEmail() or !validatePassord() or !validateFornavn() or !validateEtternavn() or !validateFdato()){
            return
        }
        Log.i("update", "oppretter bruker")
        auth.createUserWithEmailAndPassword(email.editText?.text.toString(), passord.editText?.text.toString())
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val bruker = Profil(
                        email.editText?.text.toString(),
                        fornavn.editText?.text.toString(),
                        etternavn.editText?.text.toString(),
                        fdato.text.toString(),
                        "https://firebasestorage.googleapis.com/v0/b/greenbook-a2981.appspot.com/o/Profil%2F8183db5c-916b-4073-86c5-848a2b0a4d13.png?alt=media&token=b140743a-3b1b-46f9-82c3-877308d2c8b1"
                    )
                    database.addBruker(user?.uid!!, bruker)
                    reload()
                } else{
                    Toast.makeText(
                        context,
                        "Autentisering feilet: ${task.exception.toString()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

    }


    fun datepicker(view: View) {
        datoKnapp = view.findViewById(R.id.registrer_bruker_dp_icon)
        fdato = view.findViewById(R.id.registrer_bruker_datepicker)

        fdato.setOnClickListener {
            displayDatePicker()
        }

        datoKnapp.setOnClickListener {
            displayDatePicker()
        }
    }

    fun displayDatePicker(){

        //Vet ikke om du har funnet koden her, Truls?
        //https://stackoverflow.com/questions/45842167/how-to-use-datepickerdialog-in-kotlin   ???
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay ->
            fdato.setText(""+mDay+"/"+mMonth+"/"+mYear)
        }, year, month, day)
        dpd.show()
    }
    private fun reload(){
        activity?.finish()
        startActivity(activity?.intent)
    }

    //https://roytuts.com/validate-email-address-with-regular-expression-using-kotlin/
    //val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    fun isEmailValid(email: String): Boolean {
        return resources.getString(R.string.EMAIL_REGEX).toRegex().matches(email)
    }

    companion object {
        fun newInstance(param1: String, param2: String) = RegistrerFragment()
    }

}