package com.example.brightplate.controllers

import com.example.brightplate.models.Ingredient
import com.example.brightplate.models.RecipeFind
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

object RecipeSearch {

    private lateinit var auth: FirebaseAuth // firebase authorisation
    private lateinit var db: DatabaseReference // firebase database reference


    // this function will get all the relevant data from firebase and will return with a list of
    // recipes that a user can currently do, in regards to their current inventory by taking the name
    // of ingredient, quanity of the ingredient, and any given filter of an ingredient which will
    // exclude recipes containing that ingredient
    fun findAllRecipes(ingredientFilter: String, recipeFilter:String, myCallback:RecipeListCallback) {
        var userIngredientList: ArrayList<Ingredient> = arrayListOf() // initializes arraylist of user's ingredients in inventory

        auth = FirebaseAuth.getInstance() // initializes firebase authorisation
        val userID: String = auth.uid.toString() // gets user ID and stores as a string
        db = FirebaseDatabase.getInstance().getReference("users/$userID/Inventory") // initializes firebase database and changes reference to the user's inventory

        // gets a snapshot of the users inventory and will generate an arraylist of Ingredient
        // objects that contain the details of the users ingredients in their inventory
        db.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // if the snapshot was valid
                if (snapshot.exists()) {
                    // iterate through all ingredients in the users inventory
                    for (ingredientSnapshot in snapshot.children) {

                        val ingName =
                            ingredientSnapshot.child("ingName").getValue().toString() // gets the name of an ingredient and assigns as value
                        val ingUnit =
                            ingredientSnapshot.child("ingUnit").getValue().toString() // gets the unit of an ingredient and assigns as value
                        val ingAmount =
                            ingredientSnapshot.child("ingAmount").getValue().toString().toDouble() // gets the quantity of an ingredient and assigns as value

                        userIngredientList.add(Ingredient(ingName, ingUnit, ingAmount)) // adds the ingredient data to the arraylist
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        var recipeList: ArrayList<RecipeFind> = arrayListOf() // initializes an arraylist to store recipes from database

        db = FirebaseDatabase.getInstance().getReference("Recipes") // changes database reference to the recipes directory

        // gets a data snapshot of all the recipes currently saved in the database
        db.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                    // iterates through all the recipes in the snapshot
                    for (recipeSnapshot in snapshot.children) {
                        var tempIngredientList: ArrayList<Ingredient> = arrayListOf() // creates a list of ingredients for a recipe, that will be reinitialized when a new recipe is iterated
                        val recName =
                            recipeSnapshot.child("RecipeName").value.toString() // gets the recipe name and assigns as value

                        for(recipeIngredientSnapshot in recipeSnapshot.child("Ingredients").children) {
                            val ingName = recipeIngredientSnapshot.child("ingName").getValue().toString() // gets an ingredient name from a recipe and assigns as value
                            val ingAmount = recipeIngredientSnapshot.child("ingAmount").getValue().toString().toDouble() // gets an ingredient quanity from a recipe and assigns as value
                            val ingUnit = recipeIngredientSnapshot.child("ingUnit").getValue().toString() // gets an ingredient unit from a recipe and assigns as value

                            tempIngredientList.add(Ingredient(ingName,ingUnit,ingAmount)) // adds ingredient from a recipe to the temporary ingredient list

                        }

                        recipeList.add(RecipeFind(recName,tempIngredientList)) // once all ingredients have been iterated, the recipe name and ingredient list is added to a recipe list

                    }

                    var ingredientCount: Int // count of ingredients that pass the algorithm requirements
                    var foundRecipes: ArrayList<String> = arrayListOf() // list of recipe names that will be the result of the algorithm
                    val ingredientFilter: String = ingredientFilter // ingredient filter provided by use

                    // iterates through all recipes in the recipe list
                    for (i: RecipeFind in recipeList) {
                        if((recipeFilter.isNotEmpty() && i.name.lowercase().contains(recipeFilter.lowercase())) || recipeFilter.isEmpty()) {

                            ingredientCount = 0 // sets ingredient count to 0 as a bnew recipe is being iterated through at this point
                            // iterates through all ingredients within a recipe
                            for (j in i.ingredients) {
                                // checks if there ia a filter provided, and whether that matches the ingredient currently being checked
                                if (ingredientFilter.isNotEmpty() && ingredientFilter.lowercase()
                                        .contains(j.ingName.lowercase())
                                ) {
                                    break // will break out of loop and wont do any more checking for this recipe
                                }
                                // iterates through all ingredients in a users inventory to find a match for the recipe ingredient currently being checked
                                for (k: Ingredient in userIngredientList) {

                                    if (j.ingName.lowercase() == k.ingName.lowercase() // checks that ingredient name matches
                                        && j.ingUnit == k.ingUnit // checks that ingredient unit matches
                                        && j.ingAmount <= k.ingAmount
                                    )  // checks that user ingredient quantity is equal or greater than the recipe ingredient quantity
                                    {
                                        ingredientCount++ // if all conditions are met, the ingredient count for the recipe is incremented
                                    }
                                }
                            }
                            // after all ingredients for a recipe have been checked, the ingredient
                            // count is compared to the quantity oif ingredients in the recipe being checked
                            if (ingredientCount == i.ingredients.size) {
                                foundRecipes.add(i.name) // if all ingredients are present in users inventory, the recipe name will be added to the list of found recipes
                            }
                        }
                    }
                myCallback.onCallback(foundRecipes) // calls the onCallback function of the RecipeListCallback interface, so that it may pass the list back

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

}