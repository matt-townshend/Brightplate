package com.example.brightplate.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.brightplate.Controllers.GroceryListObject.deleteItemonList
import com.example.brightplate.Models.Ingredient
import com.example.brightplate.R

class GrocerylistAdapter (private val itemlist: List<Ingredient> ) : RecyclerView.Adapter<GrocerylistAdapter.GroceryListViewholder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryListViewholder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_grocerylist,
       parent, false)
        return GroceryListViewholder(itemView)

    }

    override fun onBindViewHolder(holder: GroceryListViewholder, position: Int) {
        val currentItem =itemlist[position]
        holder.itemName.text=currentItem.ingName
        holder.itemUnit.text=currentItem.ingUnit
        holder.itemQuaitiy.text= currentItem.ingAmount.toString()

    }

    override fun getItemCount() = itemlist.size

    class GroceryListViewholder(itemView: View, OnclickDelete: OnclickDeleteListener): RecyclerView.ViewHolder(itemView) {
        var itemName: TextView=itemView.findViewById(R.id.GoceryitemName)
        var itemUnit: TextView =itemView.findViewById(R.id.Goceryitemquantity)
        var itemQuaitiy: TextView=itemView.findViewById(R.id.GoceryUnit)
        var Deletebutton: Button =itemView.findViewById(R.id.GoceryDelete)
        init{
            Deletebutton.setOnClickListener {
                OnclickDelete
            }
        }




    }

    interface OnclickDeleteListener{
        fun onDelete(position:Int)
    }



}

