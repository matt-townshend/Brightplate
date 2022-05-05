package com.example.brightplate.models

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class SavedRecipes {
    private lateinit var dbRef: DatabaseReference
    private var dbInnerPath: String = "SavedRecipes"
    private var dbOuterPath: String = "users"


    private lateinit var userId: String
    private lateinit var auth: FirebaseAuth

    constructor(recipePath: DatabaseReference, savedRecipePath: DatabaseReference) {
        this.copyRecipe(recipePath, savedRecipePath)
    }


    constructor(
        recipeName: String, recipeDescription: String, recipeEquipment: String,
        recipeCookTime: String, recipePrepTime: String
    ) {
        this.saveRecipe(
            recipeName, recipeDescription, recipeEquipment,
            recipeCookTime, recipePrepTime
        )
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

    // has the prams value pushed to the saved recipes database
    fun saveRecipe(
        recipeName: String, recipeDescription: String, recipeEquipment: String,
        recipeCookTime: String, recipePrepTime: String,
    ): Boolean {
        var isSaved = false
        // UserID -> User -> SavedRecipe -> ...
        dbRef = FirebaseDatabase.getInstance().getReference(this.dbOuterPath)
        var path = dbRef.child(getUserId()).child(this.dbInnerPath)
        val recipeDate = SavedRecipeData(
            recipeName,
            recipeDescription,
            recipeEquipment,
            recipeCookTime,
            recipePrepTime
        )
        path.child(recipeName).setValue(recipeDate).addOnSuccessListener {
            isSaved = true
        }.addOnFailureListener {
            isSaved = false
        }

        return isSaved
    }


    private fun copyRecipe(fromPath: DatabaseReference, toPath: DatabaseReference): Boolean {
        var isCopied = false
        val valueEventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                toPath.setValue(dataSnapshot.value).addOnCompleteListener { task ->
                    isCopied = task.isComplete
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        fromPath.addListenerForSingleValueEvent(valueEventListener)

        return isCopied
    }

}

