package com.example.brightplate.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.brightplate.controllers.InventoryEditActivity
import com.example.brightplate.controllers.SavedRecipeActivity
import com.example.brightplate.databinding.ActivityMainHomePageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ActivityMainHomePage : AppCompatActivity() {

    private lateinit var binding: ActivityMainHomePageBinding //activity binding
    private lateinit var auth: FirebaseAuth //firebase authorisation
    override fun onCreate(savedInstanceState: Bundle?) {

        auth = FirebaseAuth.getInstance() // initializing firebase authorisation instance

        //drawing layout
        binding = ActivityMainHomePageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //button to go to the AllRecipeListActivity activity
        binding.buttonAllRecipes.setOnClickListener(){
            val goToAllRecipes = Intent(this, AllRecipeListActivity:: class.java)
            goToAllRecipes.putExtra("ingredientFilter",binding.textFilter.text.toString())
            goToAllRecipes.putExtra("recipeFilter",binding.recipeFilter.text.toString())
            startActivity(goToAllRecipes)
        }

        //button to go to the InventoryEditActivity activity
        binding.buttonEditInvontory.setOnClickListener(){
            val goToEditInventory = Intent(this, InventoryEditActivity::class.java)
            startActivity(goToEditInventory)
        }

        //button to go to the RecipeListActivity activity
        binding.buttonSearchRecipes.setOnClickListener(){
            val goToSearchRecipe = Intent(this, RecipeListActivity::class.java)
            goToSearchRecipe.putExtra("ingredientFilter",binding.textFilter.text.toString()) //passes in the ingredient filter text to the activity
            goToSearchRecipe.putExtra("recipeFilter",binding.recipeFilter.text.toString())
            startActivity(goToSearchRecipe)
        }

        //button to go to sign out the currently logged in user, and return to the signin page
        binding.buttonSignOut.setOnClickListener(){
            auth.signOut()
            val goToUserSignIn = Intent(this, UserSignin::class.java)
            startActivity(goToUserSignIn)
        }

        //button to go to the SavedRecipeActivity activity
        binding.buttonSavedRecipes.setOnClickListener() {
            val goToSavedRecipes = Intent(this, SavedRecipeActivity::class.java)
            startActivity(goToSavedRecipes)
        }

        //displays a friendly welcome message containing the user's username which was given at sign up
        val dbRef = FirebaseDatabase.getInstance().getReference("users")
        dbRef.child(auth.uid.toString()).get().addOnSuccessListener {
            val userName = it.child("username").value.toString()
            binding.textViewHomePageUser.setText("Welcome, $userName")
        }


    }
}