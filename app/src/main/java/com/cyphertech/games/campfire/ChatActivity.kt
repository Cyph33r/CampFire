package com.cyphertech.games.campfire

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cyphertech.games.campfire.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    private var _activityChatBinding: ActivityChatBinding? = null
    private val activityChatBinding: ActivityChatBinding
        get() = _activityChatBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityChatBinding = ActivityChatBinding.inflate(layoutInflater)
        val chatIntent = intent
        val receiverUid = chatIntent.getStringExtra(EXTRA_RECEIVER_UID)
        setContentView(activityChatBinding.root)
    }
}