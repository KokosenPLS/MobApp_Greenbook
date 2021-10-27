package com.example.greenbook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbook.adaptorClasses.ProfilAdaptor
import com.example.greenbook.R
import com.example.greenbook.dataObjekter.Profil


class InboxFollowFragment : Fragment(), ProfilAdaptor.OnItemClickListener {

    private lateinit var posts: ArrayList<Profil>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        posts= ArrayList()

        for (i in 1..50) {
            posts.add(
                Profil(
                    "Hauk"+i, "McStein", "mail@mail.com", "24/12/1998"
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
        val action = InboxFragmentDirections.actionInboxFragmentToChatFragment(posts[position].fornavn)
        findNavController().navigate(action)
    }
}