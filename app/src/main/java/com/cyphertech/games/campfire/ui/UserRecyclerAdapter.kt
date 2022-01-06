package com.cyphertech.games.campfire.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.cyphertech.games.campfire.*
import com.cyphertech.games.campfire.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserRecyclerAdapter(private val context: Context, private val users: List<User>) :
    RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder>() {
    private val TAG = context.javaClass.simpleName
    private val layoutInflater = LayoutInflater.from(context)!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.user_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.user = users[position]
        holder.index = position
        holder.username.text = holder.user.userDisplayName
    }

    override fun getItemCount() = users.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username = itemView.findViewById<TextView>(R.id.textview_users_name)!!
        lateinit var user: User
        var index = 0

        init {
            itemView.setOnClickListener {
                val chatIntent = Intent(context, ChatActivity::class.java)
                chatIntent.putExtra(EXTRA_RECEIVER_UID, user.uid)
                context.startActivity(chatIntent)
            }
        }


    }
}