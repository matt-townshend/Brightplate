package com.example.brightplate.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.brightplate.databinding.ActivityRecipeSearchBinding


class RecipeSearch : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeSearchBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecipeSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}