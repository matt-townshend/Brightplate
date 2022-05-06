package com.example.brightplate.Controllers

import com.example.brightplate.Models.Ingredient
import com.example.brightplate.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

object GroceryListObject {
    var currentuser = FirebaseAuth.getInstance().currentUser!!.uid

    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityMainBinding
    private lateinit var database : DatabaseReference
    private var Databasegocerylist: String = "GroceryList"
    private lateinit var datalist: ArrayList<Ingredient>

    fun creategroceryList(callback: GroceryListback){

        datalist= arrayListOf()

        database = FirebaseDatabase.getInstance().getReference(this.Databasegocerylist)
        var dataloction=database.child(currentuser)
        dataloction.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(gocerylistSnapshot in snapshot.children) {
                    val ingName = gocerylistSnapshot.child("ingName").getValue().toString()
                    val ingAmount = gocerylistSnapshot.child("ingAmount").getValue().toString().toDouble()
                    val ingUnit = gocerylistSnapshot.child("ingUnit").getValue().toString()
                    datalist.add(Ingredient(ingName,ingUnit,ingAmount))
                }
                callback.GroceryCallback(datalist)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })}

    fun deleteItemonList( ingName:String){
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference(this.Databasegocerylist)
        var datadelete=database.child(currentuser)
        database.child("ingName").removeValue()


    }

}