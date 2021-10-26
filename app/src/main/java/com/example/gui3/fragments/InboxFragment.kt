package com.example.gui3.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.gui3.R


class InboxFragment : Fragment() {

    private lateinit var localController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val localNavHost = childFragmentManager.findFragmentById(R.id.inbox_nav_host_fragment) as NavHostFragment

        localController = localNavHost.navController

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inbox, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val tabAktive = view?.findViewById<TextView>(R.id.aktiveSamtalerTab)
        val tabFollows = view?.findViewById<TextView>(R.id.followsTab)

        tabAktive?.setOnClickListener {
            val action = AktiveSamtalerFragmentDirections.actionAktiveSamtalerFragmentToInboxFollowFragment()
            findNavController().navigate(action)
            tabAktive.setBackgroundResource(R.color.greenbook_selected)
            tabFollows?.setBackgroundResource(R.color.greenbook)
        }

        tabFollows?.setOnClickListener {
            val action = InboxFollowFragmentDirections.actionInboxFollowFragmentToAktiveSamtalerFragment()
            findNavController().navigate(action)
            tabAktive?.setBackgroundResource(R.color.greenbook)
            tabFollows.setBackgroundResource(R.color.greenbook_selected)

        }

    }

    companion object {
        fun newInstance() = InboxFragment()
    }
}