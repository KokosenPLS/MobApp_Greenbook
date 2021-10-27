package com.example.gui3.adaptorClasses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gui3.R
import com.example.gui3.dataObjekter.Profil

class ProfilAdaptor(val profiler: ArrayList<Profil>, val listener: OnItemClickListener) : RecyclerView.Adapter<ProfilAdaptor.ViewHolder>(){

    inner class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView),
        View.OnClickListener{
        val bilde: ImageView = itemView.findViewById(R.id.profilBildeDisplay)
        val navn: TextView = itemView.findViewById(R.id.profilNavnDisplay)

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
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.profil_display_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // TODO < Lage ny profiladaptor med connection til database >
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    override fun getItemCount(): Int  {
        return profiler.size
    }


}