package com.example.brightplate.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brightplate.R
import com.example.brightplate.controllers.RecyclerAdapter
import com.google.firebase.database.*

class AllRecipeListActivity : AppCompatActivity(), RecyclerAdapter.OnRecipeItemClickListener{

    private lateinit var dbref: DatabaseReference //reference to database
    private lateinit var recipeRecyclerView: RecyclerView
    private lateinit var recipeArrayList: ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)



        recipeRecyclerView = findViewById(R.id.recyclerView_recipeSelection) //reference to the recycler
        //view holding the recipes
        recipeRecyclerView.layoutManager = LinearLayoutManager(this)
        recipeRecyclerView.setHasFixedSize(true)

        recipeArrayList = arrayListOf()
        getRecipeData() // calling the getRecipeData function


    }


    fun getRecipeData()
    {
        //val ingredientFilter: String = intent.getStringExtra("ingredientFilter").toString()
        val recipeFilter: String = intent.getStringExtra("recipeFilter").toString()
        dbref = FirebaseDatabase.getInstance().getReference("Recipes") //Database reference to
        //the node "Recipes" so that its children can be accessed
        dbref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
                {
                    for(recipeSnapshot in snapshot.children) //All the children of the "Recipes" node
                    {
                        if ((recipeFilter.isNotEmpty() && recipeSnapshot.key.toString().lowercase()//adding recipes to array list based on filter
                                .contains(recipeFilter.lowercase())) || recipeFilter.isEmpty()
                        ) {
                            val recipe = recipeSnapshot.key.toString()
                            recipeArrayList.add(recipe)

                        }
                    }
                    recipeRecyclerView.adapter = RecyclerAdapter(recipeArrayList, this@AllRecipeListActivity)  //Initialising the recycler view adapter
                }

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


    }


    override fun onClick(position: Int) {

        var recipe = recipeArrayList[position]
        Toast.makeText(this, "$recipe: Position $position clicked!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@AllRecipeListActivity, ChosenRecipe:: class.java)
        intent.putExtra("Recipe", recipe)
        startActivity(intent)
    }


}