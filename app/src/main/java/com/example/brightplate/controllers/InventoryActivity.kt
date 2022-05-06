package com.example.brightplate.controllers

/**
 * Main Inventory Activity
 */
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.brightplate.R

class InventoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory)

        val viewButton: Button = findViewById(R.id.view_inventory_btn)
        val editButton: Button = findViewById(R.id.edit_inventory_btn)

        viewButton.setOnClickListener {
            val viewInventory = Intent(this@InventoryActivity, InventoryEditActivity::class.java)
            startActivity(viewInventory)
        }

        editButton.setOnClickListener {
            val editInventory = Intent(this@InventoryActivity, InventoryViewActivity::class.java)
            startActivity(editInventory)
        }

    }


}