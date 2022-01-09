package com.cyphertech.games.campfire

data class User(val userDisplayName: String, val email: String, val uid: String)
data class Message(val senderUid: String, val receiverUid: String,val receiverDisplayName: String, val body: String)