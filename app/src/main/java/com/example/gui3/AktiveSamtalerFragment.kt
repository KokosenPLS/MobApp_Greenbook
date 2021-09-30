package com.example.gui3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AktiveSamtalerFragment : Fragment(), ProfilAdaptor.OnItemClickListener {

    companion object {
        fun newInstance() = AktiveSamtalerFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_aktive_samtaler, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val posts: ArrayList<ProfilDisplay> = ArrayList()

        for (i in 1..50) {
            posts.add(
                ProfilDisplay(
                    "https://picsum.photos/180/125?random&" + i,
                    "Hauk"+i+"stein McSteinesen"
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
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragContainerUser, ChatFragment())
        transaction?.addToBackStack(null)
        transaction?.commit()
    }
}