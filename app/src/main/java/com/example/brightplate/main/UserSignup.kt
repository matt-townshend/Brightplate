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

    private lateinit var binding: ActivityUserSignupBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityUserSignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.alreadyUserTextView.setOnClickListener() {
            val goToSignin = Intent(this, UserSignin::class.java)
            startActivity(goToSignin)
        }

        binding.createAccountButton.setOnClickListener {
            val email = binding.editTextEmailAddress.text.toString()
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
            val confirmPassword = binding.editTextConfirmPassword.text.toString()
            val userMessage =
                UserRegisterValidator.createUser(email, username, password, confirmPassword)
            Toast.makeText(this, userMessage, Toast.LENGTH_SHORT).show()
            if (userMessage == "Account successfully created") {
                auth = FirebaseAuth.getInstance()
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        auth.signInWithEmailAndPassword(email, password)

                        var database = FirebaseDatabase.getInstance()
                            .getReference("users/" + auth.uid.toString())
                        database.child("username").setValue(username)



                    }
                    else {
                        Toast.makeText(this, it.exception.toString(),Toast.LENGTH_SHORT).show()
                    }


                }
            }
        }
    }
}