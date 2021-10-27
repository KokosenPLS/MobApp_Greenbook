package com.example.gui3.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gui3.Database
import com.example.gui3.adaptorClasses.PostAdaptor
import com.example.gui3.viewModels.FeedViewModel
import com.example.gui3.R
import com.example.gui3.dataObjekter.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FeedFragment() : Fragment(R.layout.feed_fragment), PostAdaptor.OnItemClickListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var database: Database
    private lateinit var viewModel: FeedViewModel

    val posts: ArrayList<Post> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        user = auth.currentUser!!
        database = Database()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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