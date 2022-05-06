package com.example.brightplate.controllers

import com.example.brightplate.models.Ingredient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

object SavedRecipeObj {
    private lateinit var dbRef: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var recipeSavedList: ArrayList<String>

    fun getSavedRecipes(callback: RecipeListCallback) {
        auth = FirebaseAuth.getInstance()
        val userID = auth.uid.toString()

        recipeSavedList = arrayListOf()

        dbRef = FirebaseDatabase.getInstance().getReference("users/$userID/SavedRecipes")
        dbRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for(savedRecipes in snapshot.children){
                        val recipeName = savedRecipes.key.toString()
                        recipeSavedList.add(recipeName)
                    }
                    callback.onCallback(recipeSavedList)
                }

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



    }

    fun saveRecipe(
        recipeName: String
    ){
        auth = FirebaseAuth.getInstance()
        val userID = auth.uid.toString()
        // Users -> UserID -> SavedRecipe -> ...
        dbRef = FirebaseDatabase.getInstance().getReference("users/$userID/SavedRecipes/$recipeName")
        // saving the object to the firebase database
        // path: Users -> UserID -> SavedRecipes
        dbRef.child("Recipe Name").setValue(recipeName)
    }
}