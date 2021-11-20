package com.example.greenbook.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.greenbook.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LaunchActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        auth = Firebase.auth
    }

    override fun onStart() {
        Log.i("activity", "Activity Start")
        super.onStart()
        val user = auth.currentUser
        if(user != null){
            Log.i("activity", "Logged in")
            login()
        }
    }

    private fun login() {
        Log.i("activity", "Logging in")
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}