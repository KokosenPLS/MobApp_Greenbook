package com.example.greenbook.adaptorClasses

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color.blue
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbook.Database
import com.example.greenbook.R
import com.example.greenbook.dataObjekter.Arrangement
import com.example.greenbook.dataObjekter.Post
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.collection.LLRBNode
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class PostAdaptor(val arrangement : ArrayList<Arrangement>, val listener: OnItemClickListener, val context: Context) : RecyclerView.Adapter<PostAdaptor.ViewHolder>() {

    inner class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        val tittel: TextView = itemView.findViewById(R.id.tittel)
        val sted: TextView = itemView.findViewById(R.id.sted)
        val tid: TextView = itemView.findViewById(R.id.tid)
        val skal: TextView = itemView.findViewById(R.id.registered)
        val beskrivelse: TextView = itemView.findViewById(R.id.feed_arrangement_beskrivelse)
        val btnJoin: Button = itemView.findViewById(R.id.feed_btn_arrangement_join)
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
        holder.tittel.text = arrangement[position].tittel
        holder.sted.text = arrangement[position].sted
        holder.tid.text = arrangement[position].tid
        holder.beskrivelse.text = arrangement[position].beskrivelse
        if(arrangement[position].bildeUrl !=null)
            Picasso.get().load(arrangement[position].bildeUrl).into(holder.bilde)
        val database = Database()
        holder.btnJoin.setOnClickListener {
            if(holder.btnJoin.text.equals("Bli med")) {
                holder.btnJoin.text = "Påmeldt"
                database.meldBrukerPåArrangement(
                    Firebase.auth.currentUser?.uid!!,
                    arrangement[position].arrangementId!!
                )
            }
            else{
                holder.btnJoin.text = "Bli med"
                database.meldBrukerAvArrangement(Firebase.auth.currentUser?.uid!!, arrangement[position].arrangementId!!)
            }
        }

        val deltakereListener = object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.M)
            @SuppressLint("ResourceAsColor")
            override fun onDataChange(snapshot: DataSnapshot) {
                val joined = snapshot.childrenCount.toInt()
                if(joined == 1)
                    holder.skal.text  = joined.toString() + " påmeldt"
                else
                    holder.skal.text  = joined.toString() + " påmeldte"
                if(snapshot.hasChild(Firebase.auth.currentUser?.uid!!)){
                    holder.btnJoin.setBackgroundColor(context.getColor(R.color.greenbook_selected))
                }
                else{
                    holder.btnJoin.setBackgroundColor(context.getColor(R.color.greenbook))
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        database.database.child("påmeldinger").child(arrangement[position].arrangementId!!).addValueEventListener(deltakereListener)

    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    override fun getItemCount(): Int  {
        return arrangement.size
    }





}