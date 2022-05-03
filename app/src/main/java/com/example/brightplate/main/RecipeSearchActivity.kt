package com.example.brightplate.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.brightplate.controllers.RecipeSearch

import com.example.brightplate.databinding.ActivityRecipeSearchBinding
import com.example.brightplate.models.Ingredient
import com.example.brightplate.models.RecipeFind
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class RecipeSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeSearchBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       RecipeSearch.findAllRecipes()



        binding = ActivityRecipeSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}