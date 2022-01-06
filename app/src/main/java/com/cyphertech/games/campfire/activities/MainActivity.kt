package com.cyphertech.games.campfire.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.cyphertech.games.campfire.*
import com.cyphertech.games.campfire.databinding.ActivityMainBinding
import com.cyphertech.games.campfire.ui.UserRecyclerAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private val TAG = this::class.simpleName
    private var _activityMainBinding: ActivityMainBinding? = null
    private val activityMainBinding: ActivityMainBinding
        get() = _activityMainBinding!!
    private val mAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        activityMainBinding.button.setOnClickListener {
            mAuth.signOut()
            finish()
        }
        val users = mutableListOf<User>()
        db.collection(DB_USER_COLLECTION_PATH)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "112358 ${document.id} => ${document.data}")
                    val userDisplayName = document.getString(KEY_USER_DISPLAY_NAME)!!
                    val uid = document.getString(KEY_UID)!!
                    val userEmail = document.getString(KEY_USER_EMAIL)!!
                    val user = User(userDisplayName, userEmail, uid)
                    users.add(user)
                    activityMainBinding.listUsers.adapter =
                        UserRecyclerAdapter(this, users.toList())
                    activityMainBinding.listUsers.layoutManager = LinearLayoutManager(this)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "112358 Error getting documents: ", exception)
            }
        activityMainBinding.textviewUsername.text =
            mAuth.currentUser?.displayName ?: "null"
    }

    override fun onBackPressed() {
        this.finishAffinity()
    }
}