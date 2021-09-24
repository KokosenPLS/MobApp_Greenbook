package com.example.gui3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostAdaptor(val posts : ArrayList<Post>) : RecyclerView.Adapter<PostAdaptor.ViewHolder>() {



    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        val tittel: TextView = itemView.findViewById(R.id.tittel)
        val sted: TextView = itemView.findViewById(R.id.sted)
        val tid: TextView = itemView.findViewById(R.id.tid)
        val skal: TextView = itemView.findViewById(R.id.registered)
        val bilde: ImageView = itemView.findViewById(R.id.innlegg_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.feed_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tittel.text = posts[position].tittel
        holder.sted.text = posts[position].sted
        holder.tid.text = posts[position].tid
        holder.skal.text = "" + posts[position].going + " påmeldte"
    }

    override fun getItemCount(): Int  {
        return posts.size
    }

}