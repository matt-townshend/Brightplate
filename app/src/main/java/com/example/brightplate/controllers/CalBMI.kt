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
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.roundToInt


class CalBMI : AppCompatActivity() {
    private lateinit var saveButton: Button
    private lateinit var CalculateButton: Button
    private lateinit var HeightInput: EditText
    private lateinit var WeightInput: EditText
    private lateinit var DisplayBMI: TextView

    private var calculatedBMI: Double = 0.0
    private var Height: Double = 0.0
    private var Weight: Double = 0.0

    private lateinit var userID: String
    private lateinit var auth: FirebaseAuth
    private var dbOuterPath: String = "users"
    private var dbInnerPath: String = "Profile"
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_bmi)


        HeightInput = findViewById(R.id.BMIinputHeight)
        WeightInput = findViewById(R.id.BMIWeightInput)
        CalculateButton = findViewById(R.id.CalculateBMI)
        saveButton = findViewById(R.id.SaveBMI)
        DisplayBMI = findViewById(R.id.displayBMI)

        dbRef = FirebaseDatabase.getInstance().getReference(this.dbOuterPath)



        CalculateButton.setOnClickListener {
            Height = HeightInput.text.toString().toDouble()
            Weight = WeightInput.text.toString().toDouble()
            CalculateBMI(Height, Weight)
            DisplayBMI.text = ("Your BMI is: $calculatedBMI")

        }
        saveButton.setOnClickListener { {
        } }
    }
    fun getUserId(): String {
        auth = FirebaseAuth.getInstance()
        var db = FirebaseDatabase.getInstance().getReference("users")
        userID = auth.uid.toString()
        return userID
    }

    fun saveBMI(CalculatedBMI: Double) {
        dbRef = FirebaseDatabase.getInstance().getReference(this.dbOuterPath)
        dbRef.child(getUserId()).child("Profile").get().addOnSuccessListener {
            dbRef.child(getUserId()).child(dbInnerPath).
        }
    }


    fun CalculateBMI(height: Double, weight: Double) {
        val tempwheight= height/100
        val tempBMI : Double=weight / (tempwheight*tempwheight)
        tempBMI
        calculatedBMI=tempBMI.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()



    }

    fun getPower(height: Double): Double {
        val exponent = 2
        val result = Math.pow(height.toDouble(), exponent.toDouble())

        return result

    }
}