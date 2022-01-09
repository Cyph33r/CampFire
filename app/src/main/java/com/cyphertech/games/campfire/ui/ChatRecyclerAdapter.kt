package com.cyphertech.games.campfire.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cyphertech.games.campfire.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ChatRecyclerAdapter(val context: Context, private val messages: List<Message>) :
    RecyclerView.Adapter<ChatRecyclerAdapter.ViewHolder>() {
    private val db = Firebase.firestore
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages[position]
        holder.index = position
        holder.textviewBody.text = message.body
        holder.textviewReceiver.text = message.receiverDisplayName

    }

    override fun getItemCount() = messages.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var index = 0
        val textviewReceiver = itemView.findViewById<TextView>(R.id.textview_message_receiver)!!
        val textviewBody = itemView.findViewById<TextView>(R.id.textview_message_body)!!
        val textviewTime = itemView.findViewById<TextView>(R.id.textview_message_time)!!

    }
}
