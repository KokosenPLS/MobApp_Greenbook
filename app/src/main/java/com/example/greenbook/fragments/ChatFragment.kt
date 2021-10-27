package com.example.greenbook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbook.adaptorClasses.ChatAdaptor
import com.example.greenbook.dataObjekter.ChatMessage
import com.example.greenbook.R

class ChatFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val meldinger: ArrayList<ChatMessage> = ArrayList()
        var testbool: Boolean = true

        for (i in 1..50) {
            meldinger.add(
                ChatMessage(
                    "Dette er melding $i i chatten",
                    testbool,
                    "16:20"
                )
            )
            testbool = !testbool
        }


        val recyclerView = view?.findViewById<RecyclerView>(R.id.chatRecyclerView)

        recyclerView?.layoutManager = LinearLayoutManager(view?.context)
        recyclerView?.adapter = ChatAdaptor(meldinger)
        recyclerView?.scrollToPosition(meldinger.size-1)
    }

    companion object {

        fun newInstance() = ChatFragment()
    }
}