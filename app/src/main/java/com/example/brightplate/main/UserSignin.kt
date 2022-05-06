package com.example.brightplate.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.brightplate.databinding.ActivityUserSigninBinding
import com.google.firebase.auth.FirebaseAuth

class UserSignin : AppCompatActivity() {

    private lateinit var binding:ActivityUserSigninBinding //activity binding
    private lateinit var auth: FirebaseAuth //firebase authorisation

    override fun onCreate(savedInstanceState: Bundle?) {

        auth = FirebaseAuth.getInstance() //initializes firebase

        //draws layout
        super.onCreate(savedInstanceState)
        binding= ActivityUserSigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //button to go to the UserSignUp activity
        binding.userNotRegisteredTextView.setOnClickListener{
            val goToRegisterPage = Intent(this, UserSignup::class.java)
            startActivity(goToRegisterPage)
        }

        //button to go to the ActivityMainHomepage activity if the user has provided valid login
        // credentials, otherwise will show a pop up of describing that invalid credentials
        // have been submitted
        binding.loginButton.setOnClickListener{
            val email = binding.editTextLoginEmail.text.toString() //gets email text and assigns to a value
            val password = binding.editTextLoginPassword.text.toString() //gets password text and assigns to value

            //checks that fields are not empty
            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        //navigates to home page
                        val goToHomePage = Intent(this, ActivityMainHomePage::class.java)
                        startActivity(goToHomePage)
                    }
                    //returns error popup as to why the login failed
                    else {
                        Toast.makeText(this, "Email or pass is incorrect", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            //returns error popup as to why the login failed
            else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

    }
}