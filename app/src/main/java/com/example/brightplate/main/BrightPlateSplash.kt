package com.example.brightplate.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.brightplate.databinding.ActivityBrightPlateSplashBinding
import com.google.firebase.auth.FirebaseAuth

class BrightPlateSplash : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityBrightPlateSplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityBrightPlateSplashBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser

        //button to go to the ActivityMainHomnePage activity and checks if the user is signed in, if
        //the user is not signed in then the button will go to the UserSignin activity
        binding.buttonContinue.setOnClickListener()
        {
            if(currentUser != null){
                val goToHomePage = Intent(this, ActivityMainHomePage::class.java)
                startActivity(goToHomePage)
            }
            else
            {
                val goToSignIn = Intent(this, UserSignin::class.java)
                startActivity(goToSignIn)
            }
        }

    }
}