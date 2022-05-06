package com.example.brightplate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.brightplate.databinding.ActivityBrightPlateSplashBinding
import com.example.brightplate.main.ActivityMainHomePage
import com.example.brightplate.main.UserSignin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth

class BrightPlateSplash : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityBrightPlateSplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityBrightPlateSplashBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser

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