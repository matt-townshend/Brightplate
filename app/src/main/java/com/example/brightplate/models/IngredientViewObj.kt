package com.example.brightplate.models

import com.example.brightplate.controllers.IngredientListCallBack
import com.example.brightplate.databinding.ActivityInventoryViewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


object IngredientViewObj {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityInventoryViewBinding
    private lateinit var database: DatabaseReference
    private lateinit var datalist: ArrayList<Ingredient>

    fun getInventoryIngredients(callback: IngredientListCallBack) {
        datalist = arrayListOf()
        auth = FirebaseAuth.getInstance()

        database = FirebaseDatabase.getInstance().getReference("users")
        var dataloction = database.child("${auth.uid}/Inventory)")
        dataloction.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ingredient in snapshot.children) {
                    val ingName = ingredient.child("ingName").getValue().toString()
                    val ingAmount =
                        ingredient.child("ingAmount").getValue().toString().toDouble()
                    val ingUnit = ingredient.child("ingUnit").getValue().toString()
                    datalist.add(Ingredient(ingName, ingUnit, ingAmount))
                }
                callback.IngredientyCallback(datalist)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}