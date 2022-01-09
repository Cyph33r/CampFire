package com.cyphertech.games.campfire

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.cyphertech.games.campfire.databinding.ActivityChatBinding
import com.cyphertech.games.campfire.ui.ChatRecyclerAdapter
import com.google.android.gms.tasks.SuccessContinuation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {
    private val TAG = ChatActivity::class.java.simpleName
    private var _activityChatBinding: ActivityChatBinding? = null
    private val activityChatBinding: ActivityChatBinding
        get() = _activityChatBinding!!
    private val mAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore
    private val uid = mAuth.currentUser!!.uid
    private lateinit var receiverUid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityChatBinding = ActivityChatBinding.inflate(layoutInflater)
        val chatIntent = intent
        receiverUid = chatIntent.getStringExtra(EXTRA_RECEIVER_UID)!!
        setContentView(activityChatBinding.root)
        activityChatBinding.buttonSend.setOnClickListener {
            val messageBody = activityChatBinding.editTextTextMultiLine.text.toString()
            val messageMeta = mapOf(
                KEY_MESSAGE_SENDER to uid,
                KEY_MESSAGE_RECEIVER to receiverUid,
                KEY_MESSAGE_BODY to messageBody,
                KEY_MESSAGE_TIME to System.currentTimeMillis()
            )
            val messagesCollection = db.collection(DB_MESSAGES_PATH)
            messagesCollection.add(messageMeta)
        }
        loadMessages()
    }

    private fun loadMessages() {
        val messages = mutableListOf<Message>()
        var receiverDisplayName = ""
        db.collection(DB_USER_COLLECTION_PATH).document(receiverUid)
            .get().addOnSuccessListener {
                receiverDisplayName = it.getString(KEY_USER_DISPLAY_NAME)!!
            }
        db.collection(DB_MESSAGES_PATH)
            .whereEqualTo(KEY_MESSAGE_SENDER, uid)
            .whereEqualTo(KEY_MESSAGE_RECEIVER, receiverUid)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val sender = document.getString(KEY_MESSAGE_SENDER)!!
                    val receiver = document.getString(KEY_MESSAGE_RECEIVER)!!
                    val body = document.getString(KEY_MESSAGE_BODY)!!
                    val message = Message(sender, receiver, receiverDisplayName, body)
                    messages.add(message)
                    activityChatBinding.recyclerviewMessages.adapter =
                        ChatRecyclerAdapter(this, messages)
                    activityChatBinding.recyclerviewMessages.layoutManager =
                        LinearLayoutManager(this)
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Querying of database with $exception")

            }


    }
}