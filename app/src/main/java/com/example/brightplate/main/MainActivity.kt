package com.example.brightplate

import android.icu.util.MeasureUnit
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.brightplate.models.Ingredient
import com.example.brightplate.models.Recipe
import com.example.brightplate.models.Review
import kotlin.collections.ArrayList


@RequiresApi(Build.VERSION_CODES.N)
class MainActivity : AppCompatActivity() {

    private var chicken_noodles: Ingredient = Ingredient("Chicken Noodles", 250, MeasureUnit.GRAM)
    var water: Ingredient = Ingredient("Water", 500, MeasureUnit.MILLILITER)
    var review1: Review = Review("28/01/2022", "Yeah it's good I guess...")
    var review2: Review = Review("03/11/2021", "Taste like chicken")


    var chickenNoodles: Recipe = Recipe(
        listOf(chicken_noodles, water) as ArrayList<Ingredient>,
        listOf(review1, review2) as ArrayList<Review>,
        "Just cook the thing g",
        6,
        3,
        listOf("microwave", "measuring jug") as ArrayList<String>,
        "easy quick noodle",
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}