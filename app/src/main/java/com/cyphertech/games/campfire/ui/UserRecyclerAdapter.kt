package com.cyphertech.games.campfire.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cyphertech.games.campfire.*
import com.cyphertech.games.campfire.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserRecyclerAdapter(context: Context) :
    RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder>() {
    private val TAG = context.javaClass.simpleName
    private val layoutInflater = LayoutInflater.from(context)!!
    private val mAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.user_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentUser = mAuth.currentUser!!
        val user = db.collection(DB_USER_COLLECTION_PATH).document(currentUser.uid)
        user.get()
            .addOnCompleteListener() { task ->
                val documentSnapshot = task.result
                for ((key, data) in documentSnapshot.data!!.entries)
                    Log.d(TAG, "$key => $data")
                holder.username.text =
                    documentSnapshot.getString(KEY_USER_DISPLAY_NAME)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

    }

    override fun getItemCount() = 1

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username = itemView.findViewById<TextView>(R.id.textview_users_name)!!


    }
}