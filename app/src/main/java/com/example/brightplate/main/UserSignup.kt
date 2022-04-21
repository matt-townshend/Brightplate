package com.example.brightplate.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.brightplate.databinding.ActivityUserSignupBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Matcher
import java.util.regex.Pattern

class UserSignup : AppCompatActivity() {

    private lateinit var binding:ActivityUserSignupBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserSignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        fun checkPass(pass: String) : Boolean{
            val passwordREGEX = Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[!@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$")
            return passwordREGEX.matcher(pass).matches()
        }

        binding.alreadyUserTextView.setOnClickListener(){
            val goToSignin = Intent(this, UserSignin::class.java)
            startActivity(goToSignin)
        }

        binding.createAccountButton.setOnClickListener{
            val email = binding.editTextEmailAddress.text.toString()
            val password = binding.editTextPassword.text.toString()
            val confirmPassword = binding.editTextConfirmPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if(password == confirmPassword) {
                    if(checkPass(password)) {
                        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                            if (it.isSuccessful) {
                                auth.signInWithEmailAndPassword(email, password)
                                val goToHomePage = Intent(this, HomePage::class.java)
                                startActivity(goToHomePage)
                            } else {
                                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                    else {
                        Toast.makeText(this, "Password requires a minimum of 8 characters, at least one number, and at least one special character", Toast.LENGTH_LONG).show()
                    }
                }
                else {
                    Toast.makeText(this, "Password's don't match", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}