package com.example.brightplate.controllers

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.brightplate.models.SavedRecipeObj
import com.example.brightplate.databinding.ActivitySavedChosenRecipeBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso


class SavedChosenRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySavedChosenRecipeBinding
    private lateinit var database: DatabaseReference
    private val recipeSelected = "Recipe"

    //Database Data References:
    private val recipes = "Recipes"
    private val description = "Description"
    private val equipment = "Equipment"
    private val cookTime = "Cook Time"
    private val prepTime = "Prep Time"
    private var imageURL = "Image"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedChosenRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        applyCheckBoxState()

        val selectedRecipe = intent.getStringExtra(recipeSelected)
        database = FirebaseDatabase.getInstance().getReference(recipes)
        database.child(selectedRecipe.toString()).get().addOnSuccessListener {

            if (it.exists()) {
                val title = selectedRecipe.toString()
                binding.textViewRecipeMainTitle.text = title //Setting main title of activity

                val desc = it.child(description).value.toString()
                binding.textViewDescription.text = desc //Accessing description and setting it

                val equip = it.child(equipment).value.toString()
                binding.textViewEquipment.text = equip //Setting equipment text to that in the DB

                val cookTime = it.child(cookTime).value.toString()
                binding.textViewCookTime.text = cookTime //Setting cooktime to that in the DB

                val prepTime = it.child(prepTime).value.toString()
                binding.textViewPrepTime.text = prepTime //Setting preptime to that in the DB

                val imageURL = it.child(imageURL).value.toString()
                Picasso.get().load(imageURL).into(binding.imageViewRecipePic) //Image for the Recipe

                for (ingredient in it.child("Ingredients").children) {
                    binding.textViewIngredients.append(
                        ingredient.key.toString() + " " + ingredient.child(
                            "ingAmount"
                        ).value.toString() + ingredient.child("ingUnit").value.toString() + ", "
                    )
                }

                //Getting the ingredient amount for all the ingredients
            } else {
                Toast.makeText(
                    this,
                    "Description does not exist for this recipe",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to read data", Toast.LENGTH_SHORT).show()
        }

        /*
        * checks the state of the favourite checkbox, if the checkbox is true, then the recipe is saved
        * and if the checkbox is false then the recipe is removed from the saved recipe.
        */
        binding.savedCheckbox.setOnCheckedChangeListener { checkBox, isChecked ->

            if (isChecked) {
                SavedRecipeObj.saveRecipe(selectedRecipe.toString())
                Toast.makeText(this, "Recipe Saved", Toast.LENGTH_SHORT).show()
            } else if (!isChecked) {
                SavedRecipeObj.removeSavedRecipe(selectedRecipe.toString());
                Toast.makeText(this, "Saved Recipe Removed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Sets the checkbox condition to true
     */
    private fun applyCheckBoxState() {
        binding.savedCheckbox.isChecked = true
    }
}