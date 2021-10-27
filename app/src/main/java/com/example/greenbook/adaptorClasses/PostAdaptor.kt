package com.example.greenbook.adaptorClasses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbook.R
import com.example.greenbook.dataObjekter.Post
import com.squareup.picasso.Picasso

class PostAdaptor(val posts : ArrayList<Post>, val listener: OnItemClickListener) : RecyclerView.Adapter<PostAdaptor.ViewHolder>() {

    inner class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        val tittel: TextView = itemView.findViewById(R.id.tittel)
        val sted: TextView = itemView.findViewById(R.id.sted)
        val tid: TextView = itemView.findViewById(R.id.tid)
        val skal: TextView = itemView.findViewById(R.id.registered)
        val bilde: ImageView = itemView.findViewById(R.id.innlegg_img)

        init {
            tittel.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
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
        Picasso.get().load(posts[position].bildeURL).into(holder.bilde)
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    override fun getItemCount(): Int  {
        return posts.size
    }





}