package com.example.gui3

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class ArrangementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arrangement)

        val back = findViewById<ImageView>(R.id.back_icon)

        back.setOnClickListener{
            finish()
        }

    }



}