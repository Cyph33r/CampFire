package com.cyphertech.games.campfire

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cyphertech.games.campfire.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {
    private var _activityChatBinding: ActivityChatBinding? = null
    private val activityChatBinding: ActivityChatBinding
        get() = _activityChatBinding!!
    private val mAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore
    private lateinit var  receiverUid:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityChatBinding = ActivityChatBinding.inflate(layoutInflater)
        val chatIntent = intent
        receiverUid = chatIntent.getStringExtra(EXTRA_RECEIVER_UID)!!
        setContentView(activityChatBinding.root)
        activityChatBinding.buttonSend.setOnClickListener {
            val messageBody = activityChatBinding.editTextTextMultiLine.text.toString()
            val messageMeta = mapOf(
                KEY_MESSAGE_SENDER to mAuth.currentUser!!.uid,
                KEY_MESSAGE_RECEIVER to receiverUid,
                KEY_MESSAGE_BODY to messageBody
            )
            val messagesCollection = db.collection(DB_MESSAGES_PATH)
            messagesCollection.add(messageMeta)


        }
    }
}