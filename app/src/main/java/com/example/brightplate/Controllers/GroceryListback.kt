package com.example.brightplate.Controllers

import com.example.brightplate.Models.Ingredient

interface GroceryListback {
    fun GroceryCallback(value:ArrayList<Ingredient>)
}