package com.example.brightplate.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.brightplate.databinding.ActivityRecipeSearchBinding
import com.example.brightplate.models.Ingredient
import com.example.brightplate.controllers.RecipeSearch
import com.example.brightplate.models.Recipe
import com.example.brightplate.models.RecipeFind
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class RecipeSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeSearchBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var userIngredientList: ArrayList<Ingredient> = arrayListOf(Ingredient())

        auth = FirebaseAuth.getInstance()

        val userID: String = auth.uid.toString()
        db = FirebaseDatabase.getInstance().getReference("users/$userID/Inventory")
        db.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (ingredientSnapshot in snapshot.children) {

                        val ingName =
                            snapshot.child("ingName").getValue().toString()
                        val ingUnit =
                            ingredientSnapshot.child("ingUnit").getValue().toString()
                        val ingAmount =
                            ingredientSnapshot.child("ingAmount").getValue().toString()
                                .toDoubleOrNull()
                        userIngredientList.add(Ingredient(ingName, ingUnit, ingAmount))
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        var recipeList: ArrayList<RecipeFind>

        var tempIngredientList: ArrayList<Ingredient> = arrayListOf(Ingredient())
        recipeList = arrayListOf(RecipeFind("def",tempIngredientList))
        db = FirebaseDatabase.getInstance().getReference("Recipes")
        db.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (recipeSnapshot in snapshot.children) {

                        val recName =
                            recipeSnapshot.child("RecipeName").value.toString()

                        for(recipeIngredientSnapshot in recipeSnapshot.child("Ingredients").children) {
                            val ingName = recipeIngredientSnapshot.child("ingName").getValue().toString()
                            val ingAmount = recipeIngredientSnapshot.child("ingAmount").getValue().toString().toDoubleOrNull()
                            val ingUnit = recipeIngredientSnapshot.child("ingUnit").getValue().toString()

                            tempIngredientList.add(Ingredient(ingName,ingUnit, ingAmount))
                        }
                        recipeList.add(RecipeFind(recName,tempIngredientList))
                        tempIngredientList.clear()

                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

//        var ingredientCount: Int
//        var foundRecipes: ArrayList<String> = arrayListOf()
//
//        for(i in recipeList) {
//            ingredientCount = 0
//            for (j in i.ingredients) {
//                for (k in userIngredientList) {
//                    if(j.ingName == k.ingName) {
//                        ingredientCount++
//                    }
//                }
//            }
//            if(ingredientCount == i.ingredients.size) {
//                foundRecipes.add(i.name)
//            }
//        }
//
//        var test = 8

        binding = ActivityRecipeSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}