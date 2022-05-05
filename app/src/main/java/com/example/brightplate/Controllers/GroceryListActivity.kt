package com.example.brightplate.Controllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.brightplate.R
import com.example.brightplate.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



class GroceryListActivity : AppCompatActivity() {
    private lateinit var itemName: String
    private lateinit var itemUnit: String
    private lateinit var itemQuaitiy: String

    var currentuser = FirebaseAuth.getInstance().currentUser!!.uid

    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityMainBinding
    private lateinit var database : DatabaseReference
    private var Databasegocerylist: String = "GroceryList"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grocerylist);

        val AddButton: Button = findViewById(R.id.AddItemToList)

        AddButton.setOnClickListener{
            val additem = Intent(this@GroceryListActivity, GroceryListAdd::class.java)
            startActivity(additem)
        }
    }
}