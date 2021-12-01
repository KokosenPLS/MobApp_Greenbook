package com.example.greenbook.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greenbook.Database
import com.example.greenbook.adaptorClasses.ChatAdaptor
import com.example.greenbook.dataObjekter.ChatMessage
import com.example.greenbook.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp

class ChatFragment : Fragment(R.layout.fragment_chat) {

    private lateinit var database: Database
    private val args: ChatFragmentArgs by navArgs()

    private lateinit var inputText: EditText
    private lateinit var btn_send: ImageView
    private lateinit var chatRecyclerView: RecyclerView
    private val user = Firebase.auth.currentUser

    val meldinger: ArrayList<ChatMessage> = ArrayList()
    private lateinit var adaptor: ChatAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Database()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputText = view.findViewById(R.id.chat_input_text)
        btn_send = view.findViewById(R.id.chat_btn_send)

        chatRecyclerView = view.findViewById(R.id.chatRecyclerView)
        adaptor = ChatAdaptor(meldinger, requireContext())
        chatRecyclerView.layoutManager = LinearLayoutManager(view?.context)
        chatRecyclerView.adapter = adaptor

        val meldingListener = object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                leggTilChat(snapshot)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                leggTilChat(snapshot)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        database.database.child("meldinger").child(user?.uid!!).child(args.brukerID).addChildEventListener(meldingListener)

        chatRecyclerView.scrollToPosition(meldinger.size-1)

        btn_send.setOnClickListener(){
            sendMelding()
        }
    }

    private fun leggTilChat(snapshot: DataSnapshot) {
        Log.i("melding", snapshot.value.toString())

        val melding = snapshot.getValue<ChatMessage>()!!
        meldinger.add(melding)


        adaptor.notifyDataSetChanged()
    }

    private fun sendMelding() {
        val melding = inputText.text.toString()
        inputText.text.clear()
        if(melding.isEmpty())
            return

        val timestamp = Timestamp(System.currentTimeMillis()).toString()


        val msg = ChatMessage(melding, user?.uid!!, args.brukerID, timestamp)

        database.sendMelding(msg)

    }

}