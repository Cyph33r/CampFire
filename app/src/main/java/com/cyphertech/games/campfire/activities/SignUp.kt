package com.cyphertech.games.campfire.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.cyphertech.games.campfire.*
import com.cyphertech.games.campfire.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.cyphertech.games.campfire.models.LogInSignUpModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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
        if (mAuth.currentUser != null) {
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
                val db = Firebase.firestore
                val user = hashMapOf(
                    KEY_USER_DISPLAY_NAME to name,
                    KEY_USER_EMAIL to currentUser.email,
                )
                // Add a new document with a generated ID
                db.collection(DB_USER_COLLECTION_PATH)
                    .document(currentUser.uid)
                    .set(user)
//                    .addOnSuccessListener { documentReference ->
//                        Log.wtf(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//                    }
//                    .addOnFailureListener { e ->
//                        Log.wtf(TAG, "Error adding document", e)
//                    }


                val homeIntent = Intent(this, MainActivity::class.java)
                startActivity(homeIntent)
            } else {
                Log.w(
                    TAG,
                    "createUserWithEmail:failure. name: $name email: $email password: $password",
                    task.exception
                )
                Toast.makeText(
                    baseContext, "Authentication failed.$name email: $email password: $password",
                    Toast.LENGTH_SHORT
                ).show()


            }
        }
    }
}