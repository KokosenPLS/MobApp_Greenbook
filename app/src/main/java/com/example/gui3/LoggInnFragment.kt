package com.example.gui3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class LoggInnFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logg_inn, container, false)
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            LoggInnFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    val forgotpasswordtextview = view?.findViewById<TextView>(R.id.textViewGlemtPassord)

    forgotpasswordtextview?.setOnClickListener {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragmentContainerView,Glemt_Passord())
        transaction?.commit()

    }

    }
}