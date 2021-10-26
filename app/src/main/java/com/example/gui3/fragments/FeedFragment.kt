package com.example.gui3.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gui3.adaptorClasses.PostAdaptor
import com.example.gui3.viewModels.FeedViewModel
import com.example.gui3.R
import com.example.gui3.dataObjekter.Post

class FeedFragment() : Fragment(), PostAdaptor.OnItemClickListener {


    private lateinit var viewModel: FeedViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

    override fun onItemClick(position: Int) {
        val post = posts[position]
        val action = FeedFragmentDirections.actionFeedFragmentToArrangementFragment(post.tittel)
        findNavController().navigate(action)

    }
}