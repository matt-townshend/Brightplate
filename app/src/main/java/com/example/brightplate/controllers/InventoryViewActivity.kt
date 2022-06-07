package com.example.brightplate.controllers

/**
 * Edit Inventory Activity
 */
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brightplate.R
import com.example.brightplate.models.Ingredient
import com.example.brightplate.models.IngredientViewObj
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class InventoryViewActivity : AppCompatActivity() {

    private lateinit var list: ArrayList<Ingredient>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory_view)

        val RecyclerView = findViewById(R.id.recViewIngredients) as RecyclerView

        RecyclerView.layoutManager = LinearLayoutManager(this)
        RecyclerView.setHasFixedSize(true)

        list = arrayListOf()

        IngredientViewObj.getInventoryIngredients(object : IngredientListCallBack {
            override fun IngredientyCallback(recipes: ArrayList<Ingredient>) {
                list = recipes
                RecyclerView.adapter = IngredientAdapter(list)
            }
        })

    }
}