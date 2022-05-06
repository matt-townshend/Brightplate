package com.example.brightplate.controllers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.brightplate.R

class RecyclerAdapter(private val recipeList: ArrayList<String>,
private val onRecipeClickListener: OnRecipeItemClickListener
): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_choserecipe_customlayout, parent, false)
        return ViewHolder(v, onRecipeClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentItem = recipeList[position]

        holder.recipeName.text = currentItem
    }

    //returns the number of recipes
    override fun getItemCount(): Int {
        return recipeList.size
    }


    //Inner class which initialises recipe name and recipe index so that the position and the name of
    //recipe in the recycler view can be obtained
    inner class ViewHolder(itemView: View, OnItemClickListener: OnRecipeItemClickListener): RecyclerView.ViewHolder(itemView)
    {
        var recipeName: TextView
        var recipeIndex: Int
        init {
            recipeName = itemView.findViewById(R.id.textView_recipe_selection)
            recipeIndex = adapterPosition
            itemView.setOnClickListener{
                OnItemClickListener.onClick(adapterPosition)
            }
        }


    }


    //Interface for the the Recycler View to override so that each item in the Recycler View
    //can respond to a click
    interface OnRecipeItemClickListener
    {
        fun onClick(position: Int)
    }
}