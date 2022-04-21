package com.example.brightplate

import android.content.Intent
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




    }
}