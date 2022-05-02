package com.example.brightplate.Controllers

import com.example.brightplate.main.RecyclerAdapter
import com.example.brightplate.models.Ingredient
import com.example.brightplate.models.Recipe
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

object RecipeSearch {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference




    fun getUserInventory() {
        var ingredientList: ArrayList<Ingredient>
        ingredientList = arrayListOf(Ingredient())

        auth = FirebaseAuth.getInstance()

        val userID: String = auth.uid.toString()
        db = FirebaseDatabase.getInstance().getReference("Users/"+userID+"/Ingredients")
        db.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (ingredientSnapshot in snapshot.children) {
                        val ingredient = ingredientSnapshot.key.toString()
                        val ingName =
                            ingredientSnapshot.child(ingredient + "/imgName").value.toString()
                        val ingUnit =
                            ingredientSnapshot.child(ingredient + "/imgUnit").value.toString()
                        val ingAmount =
                            ingredientSnapshot.child(ingredient + "/imgAmount").value.toString()
                                .toDouble()
                        ingredientList.add(Ingredient(ingName, ingUnit, ingAmount))
                    }
                    val test ="hsdh"
                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}