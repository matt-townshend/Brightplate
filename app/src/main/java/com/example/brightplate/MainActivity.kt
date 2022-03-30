package com.example.brightplate

import android.icu.util.MeasureUnit
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi


@RequiresApi(Build.VERSION_CODES.N)
class MainActivity : AppCompatActivity() {

    var chicken_noodles: Ingredient = TODO()
    var chickenNoodles: Recipe = TODO()

    init {
        chicken_noodles.name = "Chicken Noodles"
        chicken_noodles.quantity = 250
        chicken_noodles.unit = MeasureUnit.GRAM

        chickenNoodles.ingredientList
        chickenNoodles.equipment[0] = "microwave".lowercase()
        chickenNoodles.equipment[1] = "measuring jug".lowercase()
        chickenNoodles.
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}