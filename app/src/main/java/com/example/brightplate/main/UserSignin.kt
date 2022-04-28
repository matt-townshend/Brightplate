package com.example.brightplate.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.brightplate.databinding.ActivityUserSigninBinding
import com.google.firebase.auth.FirebaseAuth

class UserSignin : AppCompatActivity() {

    private lateinit var binding:ActivityUserSigninBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        auth = FirebaseAuth.getInstance()

        super.onCreate(savedInstanceState)
        binding= ActivityUserSigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.userNotRegisteredTextView.setOnClickListener{
            val goToRegisterPage = Intent(this, UserSignup::class.java)
            startActivity(goToRegisterPage)
        }

        binding.loginButton.setOnClickListener{
            val email = binding.editTextLoginEmail.text.toString()
            val password = binding.editTextLoginPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val goToHomePage = Intent(this, HomePage::class.java)
                        startActivity(goToHomePage)
                    } else {
                        Toast.makeText(this, "Email or pass is incorrect", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

    }
}