package com.cyphertech.games.campfire.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.cyphertech.games.campfire.databinding.ActivityMainBinding
import com.cyphertech.games.campfire.ui.UserRecyclerAdapter
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        activityMainBinding.button.setOnClickListener {
            mAuth.signOut()
            finish()
        }
        activityMainBinding.listUsers.adapter = UserRecyclerAdapter(this)
        activityMainBinding.listUsers.layoutManager = LinearLayoutManager(this)
        activityMainBinding.textviewUsername.text =
            mAuth.currentUser?.displayName ?: "null"
    }

    override fun onBackPressed() {
        this.finishAffinity()
    }
}