package com.example.gui3.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.example.gui3.R

class ArrangementFragment : Fragment(R.layout.fragment_arrangement) {

    private val args: ArrangementFragmentArgs by navArgs()

    private lateinit var tittel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tittel = view?.findViewById(R.id.arrangement_tittel)

        tittel.text = args.arrangementID

    }
}