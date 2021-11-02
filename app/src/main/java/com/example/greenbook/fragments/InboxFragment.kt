package com.example.greenbook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.greenbook.R


class InboxFragment : Fragment(R.layout.fragment_inbox) {

    private lateinit var fragmentContainerView: FragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentContainerView = view?.findViewById(R.id.inbox_fragment_container)

        val tabAktive = view?.findViewById<TextView>(R.id.aktiveSamtalerTab)
        val tabFollows = view?.findViewById<TextView>(R.id.followsTab)

        tabAktive?.setOnClickListener {

            childFragmentManager.beginTransaction().replace(fragmentContainerView.id, AktiveSamtalerFragment()).commit()

            tabAktive.setBackgroundResource(R.color.greenbook_selected)
            tabFollows?.setBackgroundResource(R.color.greenbook)
        }

        tabFollows?.setOnClickListener {

            childFragmentManager.beginTransaction().replace(fragmentContainerView.id, InboxFollowFragment()).commit()

            tabAktive?.setBackgroundResource(R.color.greenbook)
            tabFollows.setBackgroundResource(R.color.greenbook_selected)

        }
    }
}