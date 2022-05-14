package com.example.brightplate.models

import com.example.brightplate.controllers.RecipeListCallback
import com.example.brightplate.controllers.SavedChosenRecipeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

/**
 * Save Recipe Object
 * Used to implement save recipe operations
 */
object SavedRecipeObj {
    private lateinit var dbRef: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var recipeSavedList: ArrayList<String>


    /**
     * @param callback
     * this method gets the name of the recipes saved for the users and store that
     * recipe name into the recipeSavedList arraylist
     */
    fun getSavedRecipes(callback: RecipeListCallback) {
        auth = FirebaseAuth.getInstance()
        val userID = auth.uid.toString()

        recipeSavedList = arrayListOf()

        dbRef = FirebaseDatabase.getInstance().getReference("users/$userID/SavedRecipes")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (savedRecipes in snapshot.children) {
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

    /**
     * @param recipeName
     * the method gets the user id and reference to the "SavedRecipes" table from firebase
     * and saves the title of the recipe to that table
     */
    fun saveRecipe(recipeName: String) {
        auth = FirebaseAuth.getInstance()
        val userID = auth.uid.toString()
        dbRef = FirebaseDatabase.getInstance()
            .getReference("users/$userID/SavedRecipes/$recipeName")
        dbRef.child("Recipe Name").setValue(recipeName)
    }


    /**
     * @param recipeName
     * the method gets the user id and reference to the "SavedRecipes" table from firebase
     * and removes the title of the recipe to that table
     */
    fun removeSavedRecipe(recipeName: String) {
        auth = FirebaseAuth.getInstance()
        val userID = auth.uid.toString()
        dbRef = FirebaseDatabase.getInstance()
            .getReference("users/$userID/SavedRecipes/$recipeName")
        dbRef.child("Recipe Name").removeValue();
    }
}