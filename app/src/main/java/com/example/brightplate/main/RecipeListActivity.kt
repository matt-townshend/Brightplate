package com.example.brightplate.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brightplate.R
import com.example.brightplate.models.Recipe
import com.google.firebase.database.*

class RecipeListActivity : AppCompatActivity(), RecyclerAdapter.OnRecipeItemClickListener{

    private lateinit var dbref: DatabaseReference
    private lateinit var recipeRecyclerView: RecyclerView
    private lateinit var recipeArrayList: ArrayList<Recipe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        recipeRecyclerView = findViewById(R.id.recyclerView_recipeSelection)
        recipeRecyclerView.layoutManager = LinearLayoutManager(this)
        recipeRecyclerView.setHasFixedSize(true)

        recipeArrayList = arrayListOf<Recipe>()
        getRecipeData()

    }

    private fun getRecipeData()
    {
        dbref = FirebaseDatabase.getInstance().getReference("Recipes")
        dbref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
                {
                    for(recipeSnapshot in snapshot.children)
                    {
                        val recipe = recipeSnapshot.key.toString()
                        recipeArrayList.add(Recipe(recipe))
                    }

                }
                recipeRecyclerView.adapter = RecyclerAdapter(recipeArrayList, this@RecipeListActivity)

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


    }



    override fun onClick(position: Int) {

        var recipe = recipeArrayList[position].getRecipeName()
        Toast.makeText(this, "$recipe: Position $position clicked!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@RecipeListActivity, ChosenRecipe:: class.java)
        intent.putExtra("Recipe", recipe)
        startActivity(intent)
    }


}