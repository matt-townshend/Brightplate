package com.example.brightplate.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.brightplate.Controllers.RecipeSearch
import com.example.brightplate.databinding.ActivityRecipeSearchBinding



class RecipeSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeSearchBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var userIngredients = RecipeSearch.getUserInventory()
        var recipes = RecipeSearch.getRecipes()

        RecipeSearch.filterSearchByUserIngredients(userIngredients, recipes)



        binding = ActivityRecipeSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}