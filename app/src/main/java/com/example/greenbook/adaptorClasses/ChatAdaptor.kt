package com.example.greenbook.adaptorClasses

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbook.dataObjekter.ChatMessage
import com.example.greenbook.R

class ChatAdaptor(val meldinger: ArrayList<ChatMessage>): RecyclerView.Adapter<ChatAdaptor.ViewHolder>() {

    inner class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        val cardMessage: CardView = itemView.findViewById(R.id.card_chat_message)
        val cardTimestamp: CardView = itemView.findViewById(R.id.card_timestamp)
        val melding: TextView = itemView.findViewById(R.id.message)
        val timestamp: TextView = itemView.findViewById(R.id.timestampTxt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.chat_message, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.melding.text = meldinger[position].message
        holder.timestamp.text = meldinger[position].timestamp

        // Fin kode hehe
        holder.melding.setOnClickListener {
            toggleVisibility(holder.timestamp)
        }

    }

    private fun toggleVisibility(view: TextView){
        if(view.visibility == TextView.GONE)
            view.visibility = TextView.VISIBLE
        else
            view.visibility = TextView.GONE
    }

    override fun getItemCount(): Int  {
        return meldinger.size
    }

}