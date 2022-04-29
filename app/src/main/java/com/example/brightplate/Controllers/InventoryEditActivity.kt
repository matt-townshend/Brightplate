package com.example.brightplate.controllers

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.brightplate.Models.Ingredient
import com.example.brightplate.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class InventoryEditActivity : AppCompatActivity() {
    lateinit var addBtn: Button
    lateinit var removeBtn: Button
    lateinit var ingredientNameInput: EditText
    lateinit var ingredientUnitOption: Spinner
    lateinit var ingredientQuantity: EditText

    var ingredientAmount: Double = 0.0
    lateinit var ingredientUnitType: String
    lateinit var ingredientName: String

    lateinit var dbRef: DatabaseReference
    var dbPath: String = "Inventory"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory_edit)

        ingredientNameInput = findViewById(R.id.IngredientNameInput)
        ingredientUnitOption = findViewById(R.id.IngredientUnitTypeSpinner)
        ingredientQuantity = findViewById(R.id.IngredientQuantityInput)
        addBtn = findViewById(R.id.IngredientAddButton)
        removeBtn = findViewById(R.id.IngredientRemoveButton)

        var unitTypeOptions = arrayOf("grams", "kilograms", "liters", "milliliters")
        ingredientUnitOption.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, unitTypeOptions)

        dbRef = FirebaseDatabase.getInstance().getReference(this.dbPath)

        ingredientUnitOption.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                ingredientUnitType = unitTypeOptions.get(p2)
                Toast.makeText(
                    applicationContext,
                    "Unit ${ingredientUnitType} Selected",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
                Toast.makeText(applicationContext, "No Unit Type Selected", Toast.LENGTH_LONG)
                    .show()
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

        }

        addBtn.setOnClickListener() {
            ingredientName = ingredientNameInput.text.toString()
            ingredientAmount = ingredientQuantity.text.toString().toDouble()
            saveIngredient(ingredientName, ingredientAmount, ingredientUnitType)
        }

        removeBtn.setOnClickListener() {
            Toast.makeText(applicationContext, "Remove Button Selected", Toast.LENGTH_LONG).show()
        }

    }

    fun saveIngredient(ingName: String, ingAmount: Double, ingUnitType: String) {

        if (ingName.isEmpty() || ingUnitType.isEmpty()) {
            Toast.makeText(
                applicationContext,
                "Please Make Sure All Fields Have Been Filled",
                Toast.LENGTH_LONG
            ).show()
        }

        if (ingAmount.isNaN()) {
            Toast.makeText(
                applicationContext,
                "Please Input A Number Type Only For Ingredient Amount",
                Toast.LENGTH_LONG
            ).show()
        }

//        val ingId = dbRef.push().key!!

        val ingredient = Ingredient(ingName, ingUnitType, ingAmount)
        dbRef.child(ingName).setValue(ingredient).addOnCompleteListener {
            Toast.makeText(
                applicationContext,
                "${ingredientName.toUpperCase()} - ${ingredientAmount} ${ingredientUnitType.toUpperCase()} ADDED",
                Toast.LENGTH_LONG
            ).show()
        }.addOnFailureListener {
        }
    }

    fun checkExistingIngredients(ingName : String) : Boolean {

        return false
    }


}