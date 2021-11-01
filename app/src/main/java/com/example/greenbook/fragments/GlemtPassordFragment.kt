package com.example.greenbook.fragments

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.greenbook.Database
import com.example.greenbook.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
//https://www.youtube.com/watch?v=nVhPqPpgndM&ab_channel=tutorialsEU
class GlemtPassordFragment : Fragment(R.layout.fragment_glemt_passord) {
    private lateinit var glemtPassord_epost: EditText
    private lateinit var tvStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        glemtPassord_epost = view?.findViewById(R.id.glemtPassord_epost)
        tvStatus = view?.findViewById(R.id.status)


        view?.findViewById<Button>(R.id.glemtPassord).setOnClickListener{
            val epost: String = glemtPassord_epost.text.toString().trim{it <= ' '}
            if(epost.isEmpty()){
                Toast.makeText(activity,
                    resources.getString(R.string.tomEpost_error),
                    Toast.LENGTH_LONG
                ).show()
            }else if(isEmailValid(epost.toString()) == false)
                Toast.makeText(activity,
                    resources.getString(R.string.validitetEpost_error),
                Toast.LENGTH_LONG
                ).show()

            else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(epost)
                    .addOnCompleteListener{jobb ->
                        if(jobb.isSuccessful){
                            findNavController().popBackStack()
                            Toast.makeText(activity,
                                resources.getString(R.string.sendtEpost) + epost,
                            Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }
    }
    //https://roytuts.com/validate-email-address-with-regular-expression-using-kotlin/
    val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    fun isEmailValid(email: String): Boolean {
        return EMAIL_REGEX.toRegex().matches(email);
    }
}