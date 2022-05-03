package com.example.brightplate.Controllers

import android.widget.Toast
import com.example.brightplate.models.RecipeFind
import com.example.brightplate.models.Ingredient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

object RecipeSearch {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference




    fun getUserInventory() : ArrayList<Ingredient>{
        var ingredientList: ArrayList<Ingredient>
        ingredientList = arrayListOf(Ingredient())

        auth = FirebaseAuth.getInstance()

        val userID: String = auth.uid.toString()

        //TEST USER
        db = FirebaseDatabase.getInstance().getReference("users/"+userID+"/Inventory")

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
                        ingredientList.add(Ingredient(ingName, ingUnit, ingAmount))
                    }
                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return ingredientList
    }

    fun getRecipes() : ArrayList<RecipeFind>{

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

        return recipeList
    }

    fun filterSearchByUserIngredients() {

        var userIngredientList = getUserInventory()
        var recipeList = getRecipes()
        var ingredientCount = 0

        for(i in recipeList) {
            for (j in i.ingredients) {
                for(k in userIngredientList) {
                    if(k.ingName==j.ingName && k.ingUnit==j.ingName && k.ingAmount!! >= j.ingAmount!!) {
                        ingredientCount++
                        break
                    }
                }
                if(ingredientCount == i.ingredients.size) {
                   break
                }
                else {
                    recipeList.remove(i)
                }
            }
            ingredientCount = 0
        }


    val h: Int = 723

    }

}