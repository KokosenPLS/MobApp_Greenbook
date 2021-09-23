package com.example.gui3

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentContainerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loggInn = findViewById<TextView>(R.id.logg_inn)
        val registrer = findViewById<TextView>(R.id.registrer)

        loggInn.setOnClickListener {

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, LoggInnFragment())
                commit()
            }

            loggInn.setBackgroundResource(R.color.greenbook_selected)
            registrer.setBackgroundResource(R.color.greenbook)

        }

        registrer.setOnClickListener {

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, RegistrerFragment())
                commit()
            }
            loggInn.setBackgroundResource(R.color.greenbook)
            registrer.setBackgroundResource(R.color.greenbook_selected)

        }

    }
}