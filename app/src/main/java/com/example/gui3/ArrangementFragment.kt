package com.example.gui3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class ArrangementFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.findViewById<TextView>(R.id.toolbar_tittel)?.text = "Arrangement tittel"



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_arrangement, container, false)

    }

    companion object {
        fun newInstance(param1: String, param2: String) = ArrangementFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val btnPaameldte = view?.findViewById<Button>(R.id.btnPaameldte)
        btnPaameldte?.setOnClickListener {
            btnPaameldte.text="trykket"
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragContainerUser,DeltakereArrangement())
            transaction?.commit()
        }
    }
}