package com.example.brightplate.models

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseReference


class SavedRecipes {
    private lateinit var dbRef: DatabaseReference
    private var dbInnerPath: String = "SavedRecipes"
    private var dbOuterPath: String = "users"

    private lateinit var userId: String
    private lateinit var auth: FirebaseAuth

//    constructor(recipeName: String) {
//        this.saveRecipe(recipeName)
//    }

    /**
     *
     * @return userID of the user logged in
     */
    fun getUserId(): String {
        auth = FirebaseAuth.getInstance()
        userId = auth.uid.toString()
        return userId
    }

    // method one (1)
    // has the prams value pushed to the saved recipes database
    fun saveRecipe(
        recipeName: String
    ): Boolean {
        var isSaved = false
        // Users -> UserID -> SavedRecipe -> ...
        dbRef = FirebaseDatabase.getInstance().getReference(this.dbOuterPath)
        var path = dbRef.child(getUserId()).child(this.dbInnerPath)

        // saving the object to the firebase databse
        // path: Users -> UserID -> SavedRecipes
        path.child(recipeName).child("Recipe Name").setValue(recipeName).addOnSuccessListener {
            isSaved = true
        }.addOnFailureListener {
            isSaved = false
        }
        return isSaved
    }

    fun saveRecipeIngredients(
        ingName: String,
        ingUnit: String,
        ingAmount: String,
        recipeName: String
    ) {
        var isIngredientsSaved = false
        var dbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")
        var srPath = dbRef.child(getUserId()).child("SavedRecipes").child(recipeName)

        var ingredient = Ingredient(ingName, ingUnit, ingAmount.toDouble())
        srPath.child("Ingredients").child(ingName).setValue(ingredient)
            .addOnSuccessListener { isIngredientsSaved = true }
    }
}

