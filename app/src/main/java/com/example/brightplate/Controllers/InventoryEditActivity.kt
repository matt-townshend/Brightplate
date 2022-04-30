package com.example.brightplate.controllers

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.brightplate.Models.Ingredient
import com.example.brightplate.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class InventoryEditActivity : AppCompatActivity() {
    private lateinit var addBtn: Button
    private lateinit var removeBtn: Button
    private lateinit var ingredientNameInput: EditText
    private lateinit var ingredientUnitOption: Spinner
    private lateinit var ingredientQuantity: EditText

    private var ingredientAmount: Double = 0.0
    private lateinit var ingredientUnitType: String
    private lateinit var ingredientName: String

    private lateinit var dbRef: DatabaseReference
    private lateinit var userId: String
    private var dbPath: String = "Inventory"
    private lateinit var auth: FirebaseAuth

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
                Toast.makeText(applicationContext, "No Unit Type Selected", Toast.LENGTH_LONG)
                    .show()
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {}
        }

        addBtn.setOnClickListener() {
            ingredientName = ingredientNameInput.text.toString().toLowerCase()
            ingredientAmount = ingredientQuantity.text.toString().toDouble()
            saveIngredient(ingredientName, ingredientAmount, ingredientUnitType)
        }

        removeBtn.setOnClickListener() {
            ingredientName = ingredientNameInput.text.toString().toLowerCase()
            ingredientAmount = ingredientQuantity.text.toString().toDouble()
            removeIngredient(ingredientName, ingredientAmount, ingredientUnitType)
        }
    }

    fun saveIngredient(ingName: String, ingAmount: Double, ingUnitType: String) {

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
            dbRef.child(getUserId()).child(ingName).setValue(ingredient).addOnCompleteListener {
                Toast.makeText(
                    applicationContext,
                    "${ingredientName.toUpperCase()} - ${ingredientAmount} ${ingredientUnitType.toUpperCase()} ADDED",
                    Toast.LENGTH_LONG
                ).show()
            }.addOnFailureListener {
            }
        }
    }

    fun getUserId(): String {
        auth = FirebaseAuth.getInstance()
        var db = FirebaseDatabase.getInstance().getReference("users")
        userId = auth.uid.toString()
        return userId
    }

    fun checkExistingIngredients(ingName: String) {}

    fun removeIngredient(ingName: String, ingAmount: Double, ingUnitType: String) {

        if (ingName.isEmpty() || ingUnitType.isEmpty()) {
            Toast.makeText(
                applicationContext,
                "Please Make Sure All Fields Are Filled In",
                Toast.LENGTH_LONG
            ).show()
        } else {
            dbRef = FirebaseDatabase.getInstance().getReference(this.dbPath)
            dbRef.child(getUserId()).child(ingName).get().addOnSuccessListener {
                var amount = it.child("ingAmount").value.toString().toDouble()
                var unit = it.child("ingUnit").value.toString()
                var newAmount: Double

                if (ingAmount < amount && ingAmount > 0.0) {
                    if (ingUnitType != unit) {
                        Toast.makeText(
                            applicationContext,
                            "Incorrect Unit Selected",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        newAmount = amount - ingAmount
                        dbRef.child(getUserId()).child(ingName).child("ingAmount")
                            .setValue(newAmount)

                        Toast.makeText(
                            applicationContext,
                            "Value Removed, New ingredient amount is ${newAmount} ${unit}",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } else if (ingAmount == amount) {
                    if (ingUnitType != unit) {
                        Toast.makeText(
                            applicationContext,
                            "Incorrect Unit Selected",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        dbRef.child(getUserId()).child(ingName).removeValue()
                        Toast.makeText(
                            applicationContext,
                            "Value Removed Successfully",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Invalid Amount, Inventory Amount is  ${amount} ${unit}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

}