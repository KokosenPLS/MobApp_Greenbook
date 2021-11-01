package com.example.greenbook.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.greenbook.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoggInnFragment : Fragment(R.layout.fragment_logg_inn) {

    private lateinit var auth: FirebaseAuth

    private lateinit var epost: EditText
    private lateinit var passord: EditText
    private lateinit var btn_login: Button
    private lateinit var btn_registrer: Button
    private lateinit var btn_glemtPassord: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        epost = view?.findViewById(R.id.login_epost)
        passord = view?.findViewById(R.id.login_passord)
        btn_glemtPassord = view?.findViewById(R.id.txt_glemt_passord)

        btn_login = view?.findViewById(R.id.btn_login)
        btn_registrer = view?.findViewById(R.id.btn_ikke_registrert)

        btn_login.setOnClickListener {
            if(epost.text.isEmpty()){
                epost.setError(resources.getString(R.string.tomEpost_error))
                //epost.setHintTextColor(resources.getColor(R.color.error))
            }else if(isEmailValid(epost.toString()))
                else epost.setError(resources.getString(R.string.validitetEpost_error))
            if(passord.text.isEmpty()){
                passord.setError(resources.getString(R.string.tomPassord_error))
                //passord.setHintTextColor(resources.getColor(R.color.error))
            }
            else{auth.signInWithEmailAndPassword(epost.text.toString(), passord.text.toString())
                .addOnCompleteListener (requireActivity()) { task ->
                    if(task.isSuccessful){
                        reload()
                        //closeSoftKeyboard(requireContext(), epost)
                    }
                    else{
                        Toast.makeText(activity, resources.getString(R.string.feilLogInn), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        btn_registrer.setOnClickListener {
            val action = LoggInnFragmentDirections.actionLoggInnFragmentToRegistrerFragment()
            findNavController().navigate(action)
        }

        btn_glemtPassord.setOnClickListener{
            val action = LoggInnFragmentDirections.actionLoggInnFragmentToGlemtPassordFragment()
            findNavController().navigate(action)
        }
    }
    //https://roytuts.com/validate-email-address-with-regular-expression-using-kotlin/
    val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    fun isEmailValid(email: String): Boolean {
        return EMAIL_REGEX.toRegex().matches(email);
    }

    //https://www.codegrepper.com/code-examples/kotlin/get+keyboard+down+after+enter+kotlin
    private fun closeSoftKeyboard(context: Context, v: View) {
        val iMm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        iMm.hideSoftInputFromWindow(v.windowToken, 0)
        v.clearFocus()
    }

    private fun reload(){
        activity?.finish()
        startActivity(activity?.intent)
    }

}