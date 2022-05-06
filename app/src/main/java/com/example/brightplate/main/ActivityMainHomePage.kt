package com.example.brightplate.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.brightplate.controllers.InventoryEditActivity
import com.example.brightplate.databinding.ActivityMainHomePageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ActivityMainHomePage : AppCompatActivity() {

    private lateinit var binding: ActivityMainHomePageBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        auth = FirebaseAuth.getInstance()
        binding = ActivityMainHomePageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonAllRecipes.setOnClickListener(){
            val goToAllRecipes = Intent(this, AllRecipeListActivity:: class.java)
            startActivity(goToAllRecipes)
        }

        binding.buttonEditInvontory.setOnClickListener(){
            val goToEditInventory = Intent(this, InventoryEditActivity::class.java)
            startActivity(goToEditInventory)
        }

        binding.buttonSearchRecipes.setOnClickListener(){
            val goToSearchRecipe = Intent(this, RecipeListActivity::class.java)
            startActivity(goToSearchRecipe)
        }

        binding.buttonSignOut.setOnClickListener(){
            auth.signOut()
            val goToUserSignIn = Intent(this, UserSignin::class.java)
            startActivity(goToUserSignIn)
        }

        val dbRef = FirebaseDatabase.getInstance().getReference("users")
        dbRef.child(auth.uid.toString()).get().addOnSuccessListener {
            val userName = it.child("username").value.toString()
            binding.textViewHomePageUser.setText("Welcome, $userName")
        }


    }
}