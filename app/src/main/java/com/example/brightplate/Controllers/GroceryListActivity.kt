package com.example.brightplate.Controllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brightplate.Adapter.GrocerylistAdapter
import com.example.brightplate.Controllers.GroceryListObject.deleteItemonList
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

    private lateinit var Datalist: ArrayList<Ingredient>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grocerylist);

        //val shoppinglist = GenerateList(100)
        //val createdlist = GetDatafromDatabase()

        val RecyclerView=findViewById(R.id.recyclerView) as RecyclerView

        RecyclerView.layoutManager = LinearLayoutManager(this)
        RecyclerView.setHasFixedSize(true)

        Datalist = arrayListOf()
        GroceryListObject.creategroceryList(object : GroceryListback {
            override fun GroceryCallback(recipes: ArrayList<Ingredient>) {
                Datalist = recipes
                RecyclerView.adapter = GrocerylistAdapter(Datalist)
            }
        })
        val deletebutton:Button=findViewById(R.id.GoceryDelete)
        deletebutton.setOnClickListener {
            deleteItemonList()
        }


        val AddButton: Button = findViewById(R.id.AddItemToList)
        AddButton.setOnClickListener{
            val additem = Intent(this@GroceryListActivity, GroceryListAdd::class.java)
            startActivity(additem)
        }
    }

}



