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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class InventoryViewActivity : AppCompatActivity() {

    private lateinit var list: ArrayList<Ingredient>
    private lateinit var recylerView: RecyclerView
    private lateinit var ingAdapter: IngredientAdapter
    private lateinit var dbRef: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory_view)

        recylerView = findViewById(R.id.recViewIngredients)
        recylerView.layoutManager = LinearLayoutManager(this)
        auth = FirebaseAuth.getInstance()
        list = ArrayList()

        dbRef = FirebaseDatabase.getInstance().getReference("users/${auth.uid}/Inventory")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ingredientSnapshot in snapshot.children) {
                    val ing: Ingredient? = ingredientSnapshot.getValue(Ingredient::class.java)
                    list.add(ing!!)
                }
                ingAdapter = IngredientAdapter(list)
                recylerView.adapter = ingAdapter

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}