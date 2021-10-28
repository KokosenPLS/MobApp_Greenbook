package com.example.greenbook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbook.adaptorClasses.ProfilAdaptor
import com.example.greenbook.R
import com.example.greenbook.dataObjekter.Profil

class AktiveSamtalerFragment : Fragment(), ProfilAdaptor.OnItemClickListener {

    private lateinit var posts: ArrayList<Profil>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_aktive_samtaler, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        posts = ArrayList()

        for (i in 1..50) {
            posts.add(
                Profil(
                    "Hauk"+i, "McStein", "mail@mail.com", "24/12/1998"
                )
            )
        }


        val recyclerView = view?.findViewById<RecyclerView>(R.id.aktiveSamtalerRecyclerView)

        val gridLayout = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)

        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = gridLayout
        recyclerView?.adapter = ProfilAdaptor(posts, this)



    }

    override fun onItemClick(position: Int) {
        val action = InboxFragmentDirections.actionInboxFragmentToChatFragment(posts[position].fornavn)
        findNavController().navigate(action)
    }
}