package com.example.brightplate.Controllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brightplate.Adapter.GrocerylistAdapter
import com.example.brightplate.Models.Ingredient
import com.example.brightplate.R
import com.example.brightplate.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class GroceryListActivity : AppCompatActivity() {
    private lateinit var itemName: TextView
    private lateinit var itemUnit: TextView
    private lateinit var itemQuaitiy: TextView

    var currentuser = FirebaseAuth.getInstance().currentUser!!.uid

    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityMainBinding
    private lateinit var database : DatabaseReference
    private var Databasegocerylist: String = "GroceryList"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grocerylist);

        //val shoppinglist = GenerateList(100)
        //val createdlist = GetDatafromDatabase()

        val RecyclerView=findViewById(R.id.recyclerView) as RecyclerView

        RecyclerView.adapter= GrocerylistAdapter()
        RecyclerView.layoutManager = LinearLayoutManager(this)
        RecyclerView.setHasFixedSize(true)

        val AddButton: Button = findViewById(R.id.AddItemToList)
        AddButton.setOnClickListener{
            val additem = Intent(this@GroceryListActivity, GroceryListAdd::class.java)
            startActivity(additem)
        }
    }
   /* private fun GetDatafromDatabase(): ArrayList<Ingredient> {
        var datalist= ArrayList<Ingredient>()

        var currentuser = FirebaseAuth.getInstance().currentUser!!.uid
        database = FirebaseDatabase.getInstance().getReference(this.Databasegocerylist)
        var dataloction=database.child(currentuser)
        dataloction.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(gocerylistSnapshot in snapshot.children) {
                    val ingName = gocerylistSnapshot.child("ingName").getValue().toString()
                    val ingAmount = gocerylistSnapshot.child("ingAmount").getValue().toString().toDouble()
                    val ingUnit = gocerylistSnapshot.child("ingUnit").getValue().toString()
                    datalist.add(Ingredient(ingName,ingUnit,ingAmount))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return datalist



    }*/

}



