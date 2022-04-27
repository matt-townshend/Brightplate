package com.example.brightplate.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.brightplate.databinding.ActivityChosenRecipeBinding
import com.example.brightplate.models.Recipe
import com.google.firebase.database.*


class ChosenRecipe : AppCompatActivity()
{
    private lateinit var binding: ActivityChosenRecipeBinding
    private lateinit var database: DatabaseReference
    private lateinit var noodles: Recipe

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityChosenRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("Recipes")
        database.child("Noodles").get().addOnSuccessListener {

            if(it.exists())
            {
                val desc = it.child("Description").value.toString()
                noodles.recipeDesc = desc
                binding.textViewDescription.text = desc
                Toast.makeText(this, "Successfully read data", Toast.LENGTH_SHORT).show()

            }
            else
            {
                Toast.makeText(this, "Description does not exist for this recipe", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{

            Toast.makeText(this, "Failed to read data", Toast.LENGTH_SHORT).show()
        }


    }
}