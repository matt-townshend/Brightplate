package com.example.brightplate.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.brightplate.databinding.ActivityHomePageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class HomePage : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var ref: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {

        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().getReference(("users"))
        database.child(auth.uid.toString()).get().addOnSuccessListener {
            val username = it.child("username").value
            binding.welcomeUserTextView.setText("Welcome, "+username)
        }
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}