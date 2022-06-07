package com.example.brightplate.controllers

/**
 * User Edit Inventory
 * Performs add and remove ingredient operations for the user inventory
 */

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.brightplate.R
import com.example.brightplate.models.Ingredient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class InventoryEditActivity : AppCompatActivity() {
    private lateinit var addBtn: Button
    private lateinit var removeBtn: Button
    private lateinit var ingredientNameInput: EditText
    private lateinit var ingredientUnitOption: Spinner
    private lateinit var ingredientQuantity: EditText
    private lateinit var ingredientLimitInput: EditText

    private var ingredientAmount: Double = 0.0
    private var ingredientLimit: Double = 0.0
    private lateinit var ingredientName: String
    private lateinit var ingredientUnitType: String

    private lateinit var userId: String
    private lateinit var auth: FirebaseAuth
    private var dbOuterPath: String = "users"
    private var dbInnerPath: String = "Inventory"
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory_edit)

        // init values
        ingredientLimitInput=findViewById(R.id.IngredientLimitInput)
        ingredientNameInput = findViewById(R.id.IngredientNameInput)
        ingredientUnitOption = findViewById(R.id.IngredientUnitTypeSpinner)
        ingredientQuantity = findViewById(R.id.IngredientQuantityInput)
        addBtn = findViewById(R.id.IngredientAddButton)
        removeBtn = findViewById(R.id.IngredientRemoveButton)

        var unitTypeOptions = arrayOf("grams", "kilograms", "liters", "milliliters", "each")
        ingredientUnitOption.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, unitTypeOptions)

        dbRef = FirebaseDatabase.getInstance().getReference(this.dbOuterPath)

        ingredientUnitOption.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                ingredientUnitType = unitTypeOptions.get(p2)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(applicationContext, "No Unit Type Selected", Toast.LENGTH_LONG)
                    .show()
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {}
        }

        addBtn.setOnClickListener {
            ingredientName = ingredientNameInput.text.toString().toLowerCase()
            ingredientAmount = ingredientQuantity.text.toString().toDouble()
            if (ingredientLimitInput.text.isEmpty()){
                ingredientLimit=0.0
            }else if(ingredientLimitInput.text.isNotEmpty()){
                ingredientLimit=ingredientLimitInput.text.toString().toDouble()
            }
            saveIngredient(ingredientName, ingredientAmount, ingredientUnitType,ingredientLimit)
        }

        removeBtn.setOnClickListener {
            ingredientName = ingredientNameInput.text.toString().toLowerCase()
            ingredientAmount = ingredientQuantity.text.toString().toDouble()
            removeIngredient(ingredientName, ingredientAmount, ingredientUnitType)
        }
    }

    /**
     *
     * @return userID of the user logged in
     */
    fun getUserId(): String {
        auth = FirebaseAuth.getInstance()
        var db = FirebaseDatabase.getInstance().getReference("users")
        userId = auth.uid.toString()
        return userId
    }

    /**
     * @param ingName
     * @param ingUnitType
     *
     * @return isInputValid
     * true if the input is valid (not empty) and false if the input is invalid (empty)
     */
    fun isInputValid(ingName: String, ingUnitType: String): Boolean {
        var isInputValid = true
        if (ingName.isEmpty() || ingUnitType.isEmpty()) {
            isInputValid = false
        }
        return isInputValid
    }

    /**
     * @param requiredUnit
     * @param initUnit
     *
     * @return isUnitValid
     * true if the two unit matches and false if the two units do not match
     */
    fun isUnitValid(requiredUnit: String, initUnit: String): Boolean {
        var isUnitValid = true

        if (initUnit.toString().toLowerCase() != requiredUnit.toString().toLowerCase()) {
            isUnitValid = false
        }

        return isUnitValid
    }

    /**
     *  @param ingName
     *  @param ingAmount
     *  @param ingUnitType
     *  saves the ingredient values given by the user into the firebase database named "Users -> Inventory"
     */
    fun saveIngredient(ingName: String, ingAmount: Double, ingUnitType: String, ingLimit:Double) {
        var ingredientExists = false

        if (isInputValid(ingName, ingUnitType)) {
            dbRef = FirebaseDatabase.getInstance().getReference(this.dbOuterPath)
            dbRef.child(getUserId()).child("Inventory").child(ingName).get().addOnSuccessListener {
                var dbIngName = it.child("ingName").value.toString()

                if (dbIngName == ingName) {
                    var amount = it.child("ingAmount").value.toString().toDouble()
                    var unit = it.child("ingUnit").value.toString()
                    var newAmount: Double
                    var newLimit: Double

                    if (isUnitValid(unit, ingUnitType)) {
                        newAmount = amount + ingAmount
                        dbRef.child(getUserId()).child("Inventory").child(ingName)
                            .child("ingAmount").setValue(newAmount)
                        dbRef.child(getUserId()).child("Inventory").child(ingName)
                            .child("ingLimit").setValue(ingLimit)

                        Toast.makeText(
                            applicationContext,
                            "${ingredientName.toUpperCase()} - ${newAmount} ${ingredientUnitType.toUpperCase()} ADDED",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Incorrect Unit Selected",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            if (!ingredientExists) {
                val ingredient = Ingredient(ingName, ingUnitType, ingAmount)
                dbRef.child(getUserId()).child("Inventory").child(ingName).setValue(ingredient)
                    .addOnCompleteListener {
                        Toast.makeText(
                            applicationContext,
                            "${ingredientName.toUpperCase()} - ${ingredientAmount} ${ingredientUnitType.toUpperCase()} ADDED",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                dbRef.child(getUserId()).child("Inventory").child(ingName).get().addOnSuccessListener {
                    dbRef.child(getUserId()).child("Inventory").child(ingName)
                        .child("ingLimit").setValue(ingLimit)
                }
            }

        } else {
            Toast.makeText(
                applicationContext,
                "Please Make Sure All Fields Have Been Filled",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     *  @param ingName
     *  @param ingAmount
     *  @param ingUnitType
     *  removes the ingredient values given by the user into the firebase database named "Inventory"
     */
    fun removeIngredient(ingName: String, ingAmount: Double, ingUnitType: String) {
        if (isInputValid(ingName, ingUnitType)) {
            dbRef = FirebaseDatabase.getInstance().getReference(this.dbOuterPath)
            dbRef.child(getUserId()).child(this.dbInnerPath).child(ingName).get()
                .addOnSuccessListener {
                    var amount = it.child("ingAmount").value.toString().toDouble()
                    var unit = it.child("ingUnit").value.toString()
                    var newAmount: Double

                    if (ingAmount < amount && ingAmount > 0.0) {
                        if (isUnitValid(unit, ingUnitType)) {
                            newAmount = amount - ingAmount
                            dbRef.child(getUserId()).child(this.dbInnerPath).child(ingName)
                                .child("ingAmount")
                                .setValue(newAmount)

                            Toast.makeText(
                                applicationContext,
                                "Value Removed, New ingredient amount is ${newAmount} ${unit}",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (!isUnitValid(unit, ingUnitType)) {
                            Toast.makeText(
                                applicationContext,
                                "Incorrect Unit Selected",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else if (ingAmount == amount) {
                        if (ingUnitType != unit) {
                            Toast.makeText(
                                applicationContext,
                                "Incorrect Unit Selected",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            dbRef.child(getUserId()).child(this.dbInnerPath).child(ingName)
                                .removeValue()
                            Toast.makeText(
                                applicationContext,
                                "Value Removed Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Invalid Amount, Inventory Amount is  ${amount} ${unit}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(
                        applicationContext,
                        "Ingredient Does Not Exist In The Inventory",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            Toast.makeText(
                applicationContext,
                "Please Make Sure All Fields Are Filled In",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}