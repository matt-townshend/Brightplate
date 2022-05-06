package com.example.brightplate.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.brightplate.controllers.SavedRecipeObj
import com.example.brightplate.databinding.ActivityChosenRecipeBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso


class ChosenRecipe : AppCompatActivity()
{
    private lateinit var binding: ActivityChosenRecipeBinding
    private lateinit var database: DatabaseReference
    private val recipeSelected = "Recipe"

    //Database Data References:
    private val recipes = "Recipes"
    private val description = "Description"
    private val equipment = "Equipment"
    private val cookTime = "Cook Time"
    private val prepTime = "Prep Time"
    private var imageURL = "Image"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityChosenRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedRecipe = intent.getStringExtra(recipeSelected)
        database = FirebaseDatabase.getInstance().getReference(recipes)
        database.child(selectedRecipe.toString()).get().addOnSuccessListener {

            if(it.exists())
            {
                val title = selectedRecipe.toString()
                binding.textViewRecipeMainTitle.text = title

                val desc = it.child(description).value.toString()
                binding.textViewDescription.text = desc
                Toast.makeText(this, "Successfully read description", Toast.LENGTH_SHORT).show()

                val equip = it.child(equipment).value.toString()
                binding.textViewEquipment.text = equip

                val cookTime = it.child(cookTime).value.toString()
                binding.textViewCookTime.text =  cookTime

                val prepTime = it.child(prepTime).value.toString()
                binding.textViewPrepTime.text = prepTime

                val imageURL = it.child(imageURL).value.toString()
                Picasso.get().load(imageURL).into(binding.imageViewRecipePic)

                for(ingredient in  it.child("Ingredients").children) {
                    binding.textViewIngredients.append(ingredient.key.toString()+" "+ingredient.child("ingAmount").value.toString()+ingredient.child("ingUnit").value.toString()+", ")
                }

            }
            else
            {
                Toast.makeText(this, "Description does not exist for this recipe", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{

            Toast.makeText(this, "Failed to read data", Toast.LENGTH_SHORT).show()
        }

        binding.saveRecipeBtn.setOnClickListener() {
            SavedRecipeObj.saveRecipe(selectedRecipe.toString())

        }


    }
}