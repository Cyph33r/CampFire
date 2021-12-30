package com.cyphertech.games.campfire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cyphertech.games.campfire.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {
    private var _signUpBinding: ActivitySignUpBinding? = null
    private val signUpBinding: ActivitySignUpBinding
        get() = _signUpBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        _signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        signUpBinding.textviewNoAccount.setOnClickListener{
            val logInIntent = Intent(this, LogIn::class.java)
            startActivity(logInIntent)
        }
        setContentView(signUpBinding.root)

    }
}