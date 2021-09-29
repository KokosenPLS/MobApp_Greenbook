package com.example.gui3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AktiveSamtalerFragment : Fragment(), ProfilAdaptor.OnItemClickListener {

    companion object {
        fun newInstance() = AktiveSamtalerFragment()
    }

    private lateinit var viewModel: FeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.feed_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)

        val posts: ArrayList<ProfilDisplay> = ArrayList()

        for (i in 1..50) {
            posts.add(
                ProfilDisplay(
                    "https://picsum.photos/900/600?random&" + i,
                    "Stein"+i
                )
            )
        }


        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.aktiveSamtalerRV)

        recyclerView?.layoutManager = LinearLayoutManager(view?.context)
        recyclerView?.adapter = ProfilAdaptor(posts, this)
        Toast.makeText(context, "Toast", Toast.LENGTH_LONG)

    }

    override fun onItemClick(position: Int) {

    }
}