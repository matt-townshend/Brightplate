package com.example.brightplate.controllers

import com.example.brightplate.models.Ingredient
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.brightplate.R

class IngredientAdapter(private val itemlist: List<Ingredient>) :
    RecyclerView.Adapter<IngredientAdapter.GroceryListViewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryListViewholder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.ingredient,
            parent, false
        )
        return GroceryListViewholder(itemView)
    }

    override fun onBindViewHolder(holder: GroceryListViewholder, position: Int) {
        val currentItem = itemlist[position]
        holder.itemName.text = currentItem.ingName
        holder.itemUnit.text = currentItem.ingUnit
        holder.itemQuaitiy.text = currentItem.ingAmount.toString()
    }

    override fun getItemCount() = itemlist.size

    class GroceryListViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemName: TextView = itemView.findViewById(R.id.ingName)
        var itemUnit: TextView = itemView.findViewById(R.id.ingAmount)
        var itemQuaitiy: TextView = itemView.findViewById(R.id.ingAmount)

    }


}

