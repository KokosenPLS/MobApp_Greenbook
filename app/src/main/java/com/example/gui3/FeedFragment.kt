package com.example.gui3

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FeedFragment(val listener: OnArrangementClick) : Fragment(), PostAdaptor.OnItemClickListener {



    private lateinit var viewModel: FeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.feed_fragment, container, false)
    }

    val posts: ArrayList<Post> = ArrayList()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)



        for (i in 1..50) {
            posts.add(
                Post(
                    "Tittel " + i,
                    "Bullgring",
                    "24.12.2021",
                    i,
                    "https://picsum.photos/900/600?random&" + i
                )
            )
        }

        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView?.layoutManager = LinearLayoutManager(view?.context)
        recyclerView?.adapter = PostAdaptor(posts, this)


    }

    interface OnArrangementClick{
        fun onArrangementClick(position: Int)
    }

    override fun onItemClick(position: Int) {

        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragContainerUser, ArrangementFragment())
        transaction?.addToBackStack(null)
        transaction?.commit()

    }
}