package com.example.brightplate.controllers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.brightplate.R
import com.example.brightplate.models.Ingredient


private class IngredientAdapter(list: ArrayList<Ingredient>) :
    RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    private var list: ArrayList<Ingredient> = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view: View = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.ingredient, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ing: Ingredient = list.get(position)
        holder.ingName.setText(ing.ingName)
        holder.ingUnit.setText(ing.ingUnit)
        holder.ingAmount.setText(ing.ingAmount.toString())
    }

    override fun getItemCount(): Int {
        return list.size
    }

    internal class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ingName: TextView
        var ingUnit: TextView
        var ingAmount: TextView

        init {
            ingName = itemView.findViewById(R.id.ingName)
            ingUnit = itemView.findViewById(R.id.ingUnit)
            ingAmount = itemView.findViewById(R.id.ingAmount)
        }
    }
}