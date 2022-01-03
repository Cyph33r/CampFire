package com.cyphertech.games.campfire.activities.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.cyphertech.games.campfire.activities.login.LogIn
import com.cyphertech.games.campfire.activities.main.MainActivity
import com.cyphertech.games.campfire.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.cyphertech.games.campfire.models.LogInSignUpModel

class SignUp : AppCompatActivity() {
    private val TAG = this::class.simpleName
    private var _signUpBinding: ActivitySignUpBinding? = null
    private val signUpBinding: ActivitySignUpBinding
        get() = _signUpBinding!!
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        _signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()
        if(mAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        signUpBinding.textviewHaveAccount.setOnClickListener {
            val logInIntent = Intent(this, LogIn::class.java)
            startActivity(logInIntent)
        }
        val logInSignUpModel: LogInSignUpModel by viewModels()
        signUpBinding.edittextName.setText(logInSignUpModel.signUpName)
        signUpBinding.edittextEmailAddress.setText(logInSignUpModel.signUpEmail)
        signUpBinding.edittextPassword.setText(logInSignUpModel.signUpPassword)
        signUpBinding.buttonSignUp.setOnClickListener(::signUp)
        setContentView(signUpBinding.root)

    }

    private fun signUp(view: View?) {
        val name = signUpBinding.edittextName.text.toString().trim()
        val email = signUpBinding.edittextEmailAddress.text.toString().trim()
        val password = signUpBinding.edittextPassword.text.toString().trim()
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val currentUser = mAuth.currentUser
                currentUser!!.updateProfile(userProfileChangeRequest {
                    displayName = name
                })
                val homeIntent = Intent(this, MainActivity::class.java)
                startActivity(homeIntent)
            } else {
                Log.w(TAG, "createUserWithEmail:failure. name: $name email: $email password: $password", task.exception)
                Toast.makeText(
                    baseContext, "Authentication failed.$name email: $email password: $password",
                    Toast.LENGTH_SHORT
                ).show()


            }
        }
    }
}