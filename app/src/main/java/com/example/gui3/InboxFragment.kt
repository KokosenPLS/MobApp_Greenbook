package com.example.gui3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider


class InboxFragment : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.inboxFragmentContainer, AktiveSamtalerFragment())
                commit()
            }

            tabAktive.setBackgroundResource(R.color.greenbook_selected)
            tabFollows?.setBackgroundResource(R.color.greenbook)
        }

        tabFollows?.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.inboxFragmentContainer, InboxFollowFragment())
                commit()
            }

            tabAktive?.setBackgroundResource(R.color.greenbook)
            tabFollows.setBackgroundResource(R.color.greenbook_selected)

        }

    }

    companion object {
        fun newInstance() = InboxFragment()
    }
}