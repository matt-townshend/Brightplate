package com.example.brightplate.Controllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brightplate.Adapter.GrocerylistAdapter
import com.example.brightplate.Models.Ingredient
import com.example.brightplate.R
import com.example.brightplate.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



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

        database = FirebaseDatabase.getInstance().getReference(this.Databasegocerylist)
        //val shoppinglist = GenerateList(100)
        val shoppinglist = ArrayList<Ingredient>()
        shoppinglist.add(Ingredient("chicken","grams",5.0))
        shoppinglist.add(Ingredient("chicken","grams",5.0))
        shoppinglist.add(Ingredient("chicken","grams",5.0))
        shoppinglist.add(Ingredient("chicken","grams",5.0))
        shoppinglist.add(Ingredient("chicken","grams",5.0))
        shoppinglist.add(Ingredient("chicken","grams",5.0))
        shoppinglist.add(Ingredient("chicken","grams",5.0))
        shoppinglist.add(Ingredient("chicken","grams",5.0))



        val RecyclerView=findViewById(R.id.recyclerView) as RecyclerView

        RecyclerView.adapter= GrocerylistAdapter(shoppinglist)
        RecyclerView.layoutManager = LinearLayoutManager(this)
        RecyclerView.setHasFixedSize(true)

        val AddButton: Button = findViewById(R.id.AddItemToList)
        AddButton.setOnClickListener{
            val additem = Intent(this@GroceryListActivity, GroceryListAdd::class.java)
            startActivity(additem)
        }
    }
    private fun GetDatafromDatabase(Size: Int,ingName: String, ingAmount: Double, ingUnitType: String{

        val datalist= ArrayList<Ingredient>()
        for(i in 0 until Size){
            val ingredient = Ingredient(ingName, ingUnitType, ingAmount)
            var Databasegocerylist: String = "GroceryList"
            var currentuser = FirebaseAuth.getInstance().currentUser!!.uid
            var itemtype = FirebaseDatabase.getInstance().getReference(this.Databasegocerylist).child(currentuser).child(currentuser).child(ingName).get()
            var ItemNameSave: String
            var ItemQuaitiySave: Double =FirebaseAuth.getInstance().currentUser.
        }




    }
    private fun GenerateList(Size: Int):List<Ingredient>{
        val list = arrayListOf<Ingredient>()
        for (i in 0 until Size){
                val items= Ingredient("itemnames","itemsuni" )
             list+= list
            }
        return list


        }
}

