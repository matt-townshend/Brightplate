package com.example.brightplate.controllers

import com.example.brightplate.models.Ingredient
import com.example.brightplate.models.RecipeFind
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

object FinishCookingRecipe {

    private lateinit var auth: FirebaseAuth // firebase authorisation
    private lateinit var db: DatabaseReference // firebase database reference



    fun finishCookingRecipe(recipeName: String, myCallback:UpdateInventoryWhenCookedCallback) {

        db = FirebaseDatabase.getInstance().getReference("Recipes/$recipeName") // changes database reference to the recipes directory
        // gets a data snapshot of all the recipes currently saved in the database
        db.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var ingredientList: ArrayList<Ingredient> = arrayListOf() // creates a list of ingredients for a recipe, that will be reinitialized when a new recipe is iterated
                for(recipeIngredientSnapshot in snapshot.child("Ingredients").children) {
                    val ingName = recipeIngredientSnapshot.child("ingName").getValue().toString() // gets an ingredient name from a recipe and assigns as value
                    val ingAmount = recipeIngredientSnapshot.child("ingAmount").getValue().toString().toDouble() // gets an ingredient quanity from a recipe and assigns as value
                    val ingUnit = recipeIngredientSnapshot.child("ingUnit").getValue().toString() // gets an ingredient unit from a recipe and assigns as value

                    ingredientList.add(Ingredient(ingName,ingUnit,ingAmount)) // adds ingredient from a recipe to the temporary ingredient list

                }
                removeIngredientsFromUserInventory(ingredientList)
                myCallback.onCallback(true)
            }

            override fun onCancelled(error: DatabaseError) {
                myCallback.onCallback(false)
            }
        })



    }

    private fun removeIngredientsFromUserInventory(ingredientList: ArrayList<Ingredient>) {
        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().getReference("users/${auth.uid.toString()}/Inventory")
        for (ingredients in ingredientList) {
            db.child(ingredients.ingName.toLowerCase()).child("ingAmount").get().addOnSuccessListener {
                db.child(ingredients.ingName.toLowerCase()).child("ingAmount").setValue(it.value.toString().toDouble().minus(ingredients.ingAmount))
            }
        }
    }

}