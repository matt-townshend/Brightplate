package com.example.brightplate.controllers

import com.example.brightplate.models.Ingredient

interface IngredientListCallBack {
    fun IngredientyCallback(value: ArrayList<Ingredient>)
}
