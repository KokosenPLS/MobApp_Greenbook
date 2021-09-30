package com.example.gui3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class InboxFollowFragment : Fragment(), ProfilAdaptor.OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val posts: ArrayList<ProfilDisplay> = ArrayList()

        for (i in 1..50) {
            posts.add(
                ProfilDisplay(
                    "https://picsum.photos/900/600?random&" + i,
                    "Stein"+i
                )
            )
        }

        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.inboxFollowRecyclerView)

        recyclerView?.layoutManager = LinearLayoutManager(view?.context)
        recyclerView?.adapter = ProfilAdaptor(posts, this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inbox_follow, container, false)
    }

    companion object {
        fun newInstance() = InboxFollowFragment()
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}