package com.cyphertech.games.campfire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cyphertech.games.campfire.databinding.ActivityLogInBinding

class LogIn : AppCompatActivity() {
    private var _logInBinding: ActivityLogInBinding? = null
    private val logInBinding: ActivityLogInBinding
        get() = _logInBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        _logInBinding = ActivityLogInBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        val SignInIntent = Intent(this, SignUp::class.java)
        startActivity(SignInIntent)
        setContentView(logInBinding.root)
    }
}