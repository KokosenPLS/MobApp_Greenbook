package com.example.greenbook.adaptorClasses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbook.Database
import com.example.greenbook.R
import com.example.greenbook.dataObjekter.Kommentar
import com.example.greenbook.dataObjekter.Profil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class KommentarAdaptor(val kommentarer : ArrayList<Kommentar>, val listener: KommentarAdaptor.OnItemClickListener): RecyclerView.Adapter<KommentarAdaptor.ViewHolder>() {

    private lateinit var database: Database

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val tittel: TextView = itemView.findViewById(R.id.kommentar_tittel)
        val beskrivelse: TextView = itemView.findViewById(R.id.kommentar_tekst)

        init{
            tittel.setOnClickListener(this)
        }

        override fun onClick(p0: View?){
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.kommentar_display_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        database = Database()

        val brukerListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val bruker = snapshot.getValue<Profil>()
                holder.tittel.text = bruker?.fornavn + " " + bruker?.etternavn
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }

        database.database.child("bruker").child(kommentarer[position].brukerId!!).addValueEventListener(brukerListener)

        holder.beskrivelse.text = kommentarer[position].kommentarTekst
    }

    override fun getItemCount(): Int {
        return kommentarer.size
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

}