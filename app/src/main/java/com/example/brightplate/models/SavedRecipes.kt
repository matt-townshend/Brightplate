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

    constructor(
        recipeName: String, recipeDescription: String, recipeEquipment: String,
        recipeCookTime: String, recipePrepTime: String
    ) {
        this.saveRecipe(
            recipeName, recipeDescription, recipeEquipment,
            recipeCookTime, recipePrepTime
        )
    }

    constructor(name: String, unit: String, amount: String, recipeName : String) {
        this.saveRecipeIngredients(name, unit, amount, recipeName)
    }

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
        recipeName: String, recipeDescription: String, recipeEquipment: String,
        recipeCookTime: String, recipePrepTime: String,
    ): Boolean {
        var isSaved = false
        // Users -> UserID -> SavedRecipe -> ...
        dbRef = FirebaseDatabase.getInstance().getReference(this.dbOuterPath)
        var path = dbRef.child(getUserId()).child(this.dbInnerPath)

        // savedRecipeData Object
        val recipeDate = SavedRecipeData(
            recipeName,
            recipeDescription,
            recipeEquipment,
            recipeCookTime,
            recipePrepTime
        )
        // saving the object to the firebase databse
        // path: Users -> UserID -> SavedRecipes
        path.child(recipeName).setValue(recipeDate).addOnSuccessListener {
            isSaved = true
        }.addOnFailureListener {
            isSaved = false
        }
        return isSaved
    }

    fun saveRecipeIngredients(ingName: String, ingUnit: String, ingAmount: String, recipeName: String) {
        var isIngredientsSaved = false
        var dbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")
        var srPath = dbRef.child(getUserId()).child("SavedRecipes").child(recipeName)

        var ingredient = Ingredient(ingName, ingUnit, ingAmount.toDouble())
        srPath.child("Ingredients").child(ingName).setValue(ingredient)
            .addOnSuccessListener { isIngredientsSaved = true }
    }
}

