package com.example.brightplate.Controllers

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import com.example.brightplate.Models.Ingredient
import com.example.brightplate.R
import com.example.brightplate.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class GroceryListAdd : AppCompatActivity() {
    private lateinit var addbutton : Button
    private lateinit var cancelbutton : Button
    private lateinit var itemName: EditText
    private lateinit var itemUnit: Spinner
    private lateinit var itemQuaitiy: EditText
    private lateinit var itemType: String
    private lateinit var ItemNameSave: String
    private  var ItemQuaitiySave: Double =0.0


    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityMainBinding
    private lateinit var database : DatabaseReference
    private var Databasegocerylist: String = "GroceryList"

    var currentuser = FirebaseAuth.getInstance().currentUser!!.uid


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_grocerypopup)
        cancelbutton= findViewById(R.id.CancelItem)
        addbutton= findViewById(R.id.SavetoListitem)
        itemName= findViewById(R.id.ItemName)
        itemUnit= findViewById(R.id.ItemUnit)
        itemQuaitiy= findViewById(R.id.ItemQuanitity)


        var unitTypeOptions = arrayOf("grams", "kilograms", "liters", "milliliters")
        itemUnit.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, unitTypeOptions)
        database = FirebaseDatabase.getInstance().getReference(this.Databasegocerylist)

        itemUnit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,AdapterView.OnItemClickListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                itemType = unitTypeOptions.get(p2)
                Toast.makeText(
                    applicationContext,
                    "Unit ${itemType} Selected",
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(applicationContext, "No Unit Type Selected", Toast.LENGTH_LONG)
                    .show()
            }
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {}
        }
        addbutton.setOnClickListener (){
            ItemNameSave = itemName.text.toString().toLowerCase()
            ItemQuaitiySave = itemQuaitiy.text.toString().toDouble()
            SavetogoerceyDB(ItemNameSave, ItemQuaitiySave, itemType)
        }




        cancelbutton.setOnClickListener{
            val cancel = Intent(this@GroceryListAdd, GroceryListActivity::class.java)
            startActivity(cancel)
        }


    }
    //From Ajits Code.
    fun SavetogoerceyDB(ingName: String, ingAmount: Double, ingUnitType: String) {
        if (ingName.isEmpty() || ingUnitType.isEmpty()) {
            Toast.makeText(
                applicationContext,
                "Please Make Sure All Fields Have Been Filled",
                Toast.LENGTH_LONG
            ).show()
        } else if (ingAmount.isNaN()) {
            Toast.makeText(
                applicationContext,
                "Please Input A Number Type Only For Ingredient Amount",
                Toast.LENGTH_LONG
            ).show()
        } else {
            val ingredient = Ingredient(ingName, ingUnitType, ingAmount)
            database.child(currentuser).child(ingName).setValue(ingredient).addOnCompleteListener {
                Toast.makeText(
                    applicationContext,
                    "${ItemNameSave.toUpperCase()} - ${ItemQuaitiySave} ${itemType.toUpperCase()} ADDED",
                    Toast.LENGTH_LONG
                ).show()
            }.addOnFailureListener {
            }
        }
    }

}