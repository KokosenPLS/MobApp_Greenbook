package com.example.greenbook.adaptorClasses

import android.annotation.SuppressLint
import android.graphics.Color.blue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbook.Database
import com.example.greenbook.R
import com.example.greenbook.dataObjekter.Arrangement
import com.example.greenbook.dataObjekter.Innlegg
import com.example.greenbook.dataObjekter.Post
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.collection.LLRBNode
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class InnleggAdaptor (val innlegg : ArrayList<Innlegg>, val listener: OnItemClickListener): RecyclerView.Adapter<InnleggAdaptor.ViewHolder>(){

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val beskrivelse: TextView = itemView.findViewById(R.id.innlegg_beskrivelse)
        val tittel: TextView = itemView.findViewById(R.id.innlegg_person)
        val bilde: ImageView = itemView.findViewById(R.id.innlegg_bilde)
        val seKommentar: Button = itemView.findViewById(R.id.innlegg_btn_se_kommentar)

        init{
            seKommentar.setOnClickListener(this)
        }

        override fun onClick(p0: View?){
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder{
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.innlegg_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.beskrivelse.text = innlegg[position].innlegg_beskrivelse
        holder.tittel.text = innlegg[position].innlegg_skriver
        if(innlegg[position].bildeURL?.isEmpty()!!){
            holder.bilde.visibility = ImageView.GONE
        }else{
            Picasso.get().load(innlegg[position].bildeURL).into(holder.bilde)
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    override fun getItemCount(): Int {
        return innlegg.size
    }
}
