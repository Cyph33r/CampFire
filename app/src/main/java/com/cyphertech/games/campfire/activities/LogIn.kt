package com.cyphertech.games.campfire.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.cyphertech.games.campfire.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.cyphertech.games.campfire.models.LogInSignUpModel

class LogIn : AppCompatActivity() {
    private val TAG = LogIn::class.simpleName
    private var _logInBinding: ActivityLogInBinding? = null
    private val logInBinding: ActivityLogInBinding
        get() = _logInBinding!!
    private val logInSignUpModel: LogInSignUpModel by viewModels()
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        _logInBinding = ActivityLogInBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()
        if(mAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        logInBinding.textviewNoAccount.setOnClickListener {
            val signInIntent = Intent(this, SignUp::class.java)
            startActivity(signInIntent)
        }
        logInBinding.edittextEmailAddress.setOnFocusChangeListener { view, _ ->
            logInSignUpModel.logInEmail = (view as EditText).text.toString()
        }
        logInBinding.edittextPassword.setOnFocusChangeListener { view, _ ->
            logInSignUpModel.logInPassword = (view as EditText).text.toString()
        }
        logInBinding.buttonSignUp.setOnClickListener(::login)
        setContentView(logInBinding.root)
    }

    override fun onResume() {
        logInBinding.edittextEmailAddress.setText(logInSignUpModel.logInEmail)
        logInBinding.edittextPassword.setText(logInSignUpModel.logInPassword)
        super.onResume()
    }

    private fun login(view: View?) {
        val email = logInBinding.edittextEmailAddress.text.toString().trim()
        val password = logInBinding.edittextPassword.text.toString().trim()
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.

                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }

    }

    private fun updateUI(user: FirebaseUser?) {
        if (user == null) return
        val homeIntent = Intent(this, MainActivity::class.java)

        startActivity(homeIntent)
    }
}