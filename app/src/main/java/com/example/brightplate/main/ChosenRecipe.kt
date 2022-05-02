package com.example.brightplate.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.brightplate.R
import com.example.brightplate.RecipeListActivity
import com.example.brightplate.databinding.ActivityChosenRecipeBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ChosenRecipe : AppCompatActivity()
{
    private lateinit var binding: ActivityChosenRecipeBinding
    private lateinit var database: DatabaseReference
    private var recipeActivity = RecipeListActivity()

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityChosenRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recyclerView_recipeSelection)

        database = FirebaseDatabase.getInstance().getReference("Recipes")
        database.child("Noodles").get().addOnSuccessListener {

            if(it.exists())
            {
                val desc = it.child("Description").value.toString()
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