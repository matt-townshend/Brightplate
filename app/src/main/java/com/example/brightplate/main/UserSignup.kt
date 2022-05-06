package com.example.brightplate.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.brightplate.controllers.UserRegisterValidator
import com.example.brightplate.databinding.ActivityUserSignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class UserSignup : AppCompatActivity() {

    private lateinit var binding: ActivityUserSignupBinding //activity binding
    private lateinit var auth: FirebaseAuth //firebase authentication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // draw layout
        binding = ActivityUserSignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // button to go to the UserSignIn activity
        binding.alreadyUserTextView.setOnClickListener() {
            val goToSignin = Intent(this, UserSignin::class.java)
            startActivity(goToSignin)
        }

        // button to begin registering the user with firebase authentication with the credentials provided
        binding.createAccountButton.setOnClickListener {
            val email = binding.editTextEmailAddress.text.toString() // gets email text and assigns to value
            val username = binding.editTextUsername.text.toString() // gets username text and assigns to value
            val password = binding.editTextPassword.text.toString() // gets password text and assigns to value
            val confirmPassword = binding.editTextConfirmPassword.text.toString() // gets password confirmation text and assigns to value

            // checks that all credentials are valid with the rules we set for passwords/usernames, and will
            // return a message indicating the result and/or what would have been wrong with the
            // credentials provided in the case of failure
            val userMessage =
                UserRegisterValidator.createUser(email, username, password, confirmPassword)
            Toast.makeText(this, userMessage, Toast.LENGTH_SHORT).show()
            if (userMessage == "Account successfully created") {
                auth = FirebaseAuth.getInstance() // firebase authorisation initialization

               // sends a request to firebase authorisation to create a new user with the provided credentials
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

                    // if the request is sucessful
                    if (it.isSuccessful) {
                        auth.signInWithEmailAndPassword(email, password) //user is signed in with newly generated account

                        // changes database reference to create a key of 'username' under the user's ID and will assign their username as a value
                        var database = FirebaseDatabase.getInstance()
                            .getReference("users/" + auth.uid.toString())
                        database.child("username").setValue(username)

                        // registration is complete so the user is sent to the home page
                        val goToHomePage = Intent(this, ActivityMainHomePage::class.java)
                        startActivity(goToHomePage)


                    }
                    // if a firebase authorisation error occurs an error will pop up
                    else {
                        Toast.makeText(this, it.exception.toString(),Toast.LENGTH_SHORT).show()
                    }


                }
            }
        }
    }
}