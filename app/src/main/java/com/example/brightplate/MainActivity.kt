package com.example.brightplate

import android.icu.util.MeasureUnit
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import java.util.*


@RequiresApi(Build.VERSION_CODES.N)
class MainActivity : AppCompatActivity() {

    var chicken_noodles: Ingredient = TODO()
    var water: Ingredient = TODO()
    var chickenNoodles: Recipe = TODO()

    init {
        chicken_noodles.name = "Chicken Noodles"
        chicken_noodles.quantity = 250
        chicken_noodles.unit = MeasureUnit.GRAM
        water.name = "Water"
        water.quantity = 500
        water.unit = MeasureUnit.MILLILITER

        chickenNoodles.ingredientList.addAll(Arrays.asList(chicken_noodles, water))
        chickenNoodles.review = Review("jdjd","hdhd")
        chickenNoodles.equipment[0] = "microwave".lowercase()
        chickenNoodles.equipment[1] = "measuring jug".lowercase()

    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}