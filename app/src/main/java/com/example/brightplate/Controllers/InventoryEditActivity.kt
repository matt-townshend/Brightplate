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
    private lateinit var dbIngRef: DatabaseReference
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
        } else if (ingAmount.isNaN()) {
            Toast.makeText(
                applicationContext,
                "Please Input A Number Type Only For Ingredient Amount",
                Toast.LENGTH_LONG
            ).show()
        } else {
            auth = FirebaseAuth.getInstance()
            var db = FirebaseDatabase.getInstance().getReference("users")
            userId = db.child(auth.uid.toString()).get() as String

            val ingredient = Ingredient(ingName, ingUnitType, ingAmount)
            dbRef.child(userId).child(ingName).setValue(ingredient).addOnCompleteListener {
                Toast.makeText(
                    applicationContext,
                    "${ingredientName.toUpperCase()} - ${ingredientAmount} ${ingredientUnitType.toUpperCase()} ADDED",
                    Toast.LENGTH_LONG
                ).show()
            }.addOnFailureListener {
            }

        }
    }

    fun checkExistingIngredients(ingName: String) {
//        dbIngRef = FirebaseDatabase.getInstance().getReference().child(this.dbPath);

        dbIngRef.orderByChild(ingName)
    }

}