package com.example.brightplate.controllers

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.brightplate.R


class InventoryEditActivity : AppCompatActivity() {
    lateinit var ingredientName : EditText
    lateinit var ingredientUnitOption : Spinner
    lateinit var ingredientQuantity : EditText
    lateinit var ingredientUnitType : String
    lateinit var addBtn : Button
    lateinit var removeBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory_edit)

        ingredientName = findViewById(R.id.IngredientNameInput)
        ingredientUnitOption = findViewById(R.id.IngredientUnitTypeSpinner)
        ingredientQuantity = findViewById(R.id.IngredientQuantityInput)
        addBtn = findViewById(R.id.IngredientAddButton)
        removeBtn = findViewById(R.id.IngredientRemoveButton)

        var unitTypeOptions = arrayOf("grams", "kilogram", "liters", "milliliters")
        ingredientUnitOption.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, unitTypeOptions)

        ingredientUnitOption.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                ingredientUnitType = unitTypeOptions.get(p2)
                Toast.makeText(applicationContext, "Unit ${ingredientUnitType} Selected", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
                Toast.makeText(applicationContext, "No Unit Type Selected", Toast.LENGTH_LONG).show()
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

        }

        addBtn.setOnClickListener(){
            Toast.makeText(applicationContext, "${ingredientName.text}, ${ingredientUnitType}, ${ingredientQuantity.text} SELECTED", Toast.LENGTH_LONG).show()
        }

        removeBtn.setOnClickListener(){
            Toast.makeText(applicationContext, "Remove Button", Toast.LENGTH_LONG).show()
        }

    }

}