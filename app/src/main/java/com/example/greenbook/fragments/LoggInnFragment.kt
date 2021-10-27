package com.example.greenbook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        epost = view?.findViewById(R.id.login_epost)
        passord = view?.findViewById(R.id.login_passord)

        btn_login = view?.findViewById(R.id.btn_login)
        btn_registrer = view?.findViewById(R.id.btn_ikke_registrert)

        btn_login.setOnClickListener {
            auth.signInWithEmailAndPassword(epost.text.toString(), passord.text.toString())
                .addOnCompleteListener (requireActivity()) { task ->
                    if(task.isSuccessful){
                        reload()
                    }
                    else{
                        Toast.makeText(activity, "Feil med login, prøv igjen", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        btn_registrer.setOnClickListener {
            val action = LoggInnFragmentDirections.actionLoggInnFragmentToRegistrerFragment()
            findNavController().navigate(action)
        }
    }

    private fun reload(){
        activity?.finish()
        startActivity(activity?.intent)
    }

}