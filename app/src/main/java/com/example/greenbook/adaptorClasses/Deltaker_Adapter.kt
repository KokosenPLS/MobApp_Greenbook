package com.example.greenbook.adaptorClasses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbook.dataObjekter.DeltakerDisplay
import com.example.greenbook.R
import com.squareup.picasso.Picasso


class Deltaker_Adapter(val deltakere: ArrayList<DeltakerDisplay>, val listener: OnItemClickListener) : RecyclerView.Adapter<Deltaker_Adapter.ViewHolder>(){

    inner class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView),
        View.OnClickListener{
        val profilBilde: ImageView = itemView.findViewById(R.id.imageViewDeltakere)
        val navn: TextView = itemView.findViewById(R.id.textViewDeltakere)

        init {
            navn.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.deltakere_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(deltakere[position].img).into(holder.profilBilde)
        holder.navn.text = deltakere[position].navn
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    override fun getItemCount(): Int  {
        return deltakere.size
    }


}
