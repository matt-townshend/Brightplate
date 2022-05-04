package com.example.brightplate.controllers

import com.example.brightplate.models.Ingredient
import com.example.brightplate.models.RecipeFind
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

object RecipeSearch {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var finalRecipes: ArrayList<String>


    fun findAllRecipes(ingredientFilter: String) : ArrayList<String> {
        var userIngredientList: ArrayList<Ingredient> = arrayListOf()
        finalRecipes = arrayListOf()
        auth = FirebaseAuth.getInstance()

        val userID: String = auth.uid.toString()
        db = FirebaseDatabase.getInstance().getReference("users/$userID/Inventory")
        db.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (ingredientSnapshot in snapshot.children) {

                        val ingName =
                            ingredientSnapshot.child("ingName").getValue().toString()
                        val ingUnit =
                            ingredientSnapshot.child("ingUnit").getValue().toString()
                        val ingAmount =
                            ingredientSnapshot.child("ingAmount").getValue().toString()
                                .toDouble()
                        userIngredientList.add(Ingredient(ingName, ingUnit, ingAmount))
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



        var recipeList: ArrayList<RecipeFind> = arrayListOf()

        db = FirebaseDatabase.getInstance().getReference("Recipes")
        db.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    for (recipeSnapshot in snapshot.children) {
                        var tempIngredientList: ArrayList<Ingredient> = arrayListOf()
                        val recName =
                            recipeSnapshot.child("RecipeName").value.toString()

                        for(recipeIngredientSnapshot in recipeSnapshot.child("Ingredients").children) {
                            val ingName = recipeIngredientSnapshot.child("ingName").getValue().toString()
                            val ingAmount = recipeIngredientSnapshot.child("ingAmount").getValue().toString().toDouble()
                            val ingUnit = recipeIngredientSnapshot.child("ingUnit").getValue().toString()

                            tempIngredientList.add(Ingredient(ingName,ingUnit,ingAmount))

                        }

                        recipeList.add(RecipeFind(recName,tempIngredientList))

                    }

                   finalRecipes = searchRecipe(recipeList, userIngredientList, ingredientFilter)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return finalRecipes
    }

    private fun searchRecipe(recipeList: ArrayList<RecipeFind>, userIngredientList: ArrayList<Ingredient>, ingredientFilter: String) : ArrayList<String>{
        var ingredientCount: Int
        var foundRecipes: ArrayList<String> = arrayListOf()
        val ingredientFilter: String = ingredientFilter

        for (i: RecipeFind in recipeList) {
            ingredientCount = 0
            for (j in i.ingredients) {
                if(ingredientFilter.lowercase().contains(j.ingName.lowercase())) {
                    break
                }
                for (k: Ingredient in userIngredientList) {

                    if (j.ingName.lowercase() == k.ingName.lowercase()
                        && j.ingUnit == k.ingUnit
                        && j.ingAmount <= k.ingAmount) {
                        ingredientCount++
                    }
                }
            }
            if (ingredientCount == i.ingredients.size) {
                foundRecipes.add(i.name)
            }
        }

        return foundRecipes
    }

}