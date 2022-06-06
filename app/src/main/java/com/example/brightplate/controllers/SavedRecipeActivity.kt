package com.example.brightplate.controllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brightplate.R
import com.example.brightplate.databinding.ActivitySavedRecipeBinding
import com.example.brightplate.main.ChosenRecipe
import com.example.brightplate.models.SavedRecipeObj

/**
 * Activity which is used to display the saved recipes from the user's saved recipe table
 * from firebase
 */
class SavedRecipeActivity : AppCompatActivity(), RecyclerAdapter.OnRecipeItemClickListener {
    private lateinit var recipeRecyclerView: RecyclerView
    private lateinit var recipeSavedList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        recipeRecyclerView = findViewById(R.id.recyclerView_recipeSelection)
        recipeRecyclerView.layoutManager = LinearLayoutManager(this)
        recipeRecyclerView.setHasFixedSize(true)

        recipeSavedList = arrayListOf()

        SavedRecipeObj.getSavedRecipes(object : RecipeListCallback {
            override fun onCallback(recipes: ArrayList<String>) {
                recipeSavedList = recipes
                recipeRecyclerView.adapter = RecyclerAdapter(recipeSavedList, this@SavedRecipeActivity)
            }
        })
    }

    override fun onClick(position: Int) {
        var recipe = recipeSavedList[position]
        Toast.makeText(this, "$recipe: Position $position clicked!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@SavedRecipeActivity, SavedChosenRecipeActivity::class.java)
        intent.putExtra("Recipe", recipe)
        startActivity(intent)
    }

}