package com.example.greenbook.adaptorClasses

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbook.dataObjekter.ChatMessage
import com.example.greenbook.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.coroutineContext

class ChatAdaptor(val meldinger: ArrayList<ChatMessage>, val context: Context): RecyclerView.Adapter<ChatAdaptor.ViewHolder>() {

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


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val melding = meldinger[position]
        val user = Firebase.auth.currentUser
        val timestamp = getTimeStamp(melding.timestamp!!)
        if(user?.uid?.equals(melding.sender)!!){
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.gravity = Gravity.END
            holder.cardMessage.layoutParams = params
            holder.cardTimestamp.layoutParams = params
            holder.cardMessage.setCardBackgroundColor(context.getColor(R.color.greenbook_chat))
        }

        holder.melding.text = melding.message
        holder.timestamp.text = timestamp

        // Fin kode hehe
        holder.melding.setOnClickListener {
            toggleVisibility(holder.timestamp)
        }

    }

    private fun getTimeStamp(timestamp: String): String {
        var split = timestamp.split(" ")

        var split2 = split[1].split(":")

        return "${split2[0]}:${split2[1]}"
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