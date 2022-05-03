package com.example.brightplate.models

class RecipeFind(
    val name : String,
    val ingredients : ArrayList<Ingredient>
) {
    fun getIngredientNameAtIndex(index: Int): String {
        return ingredients[index].ingName.toString()
    }

    fun getIngredientAmountAtIndex(index: Int): Double {
        return ingredients[index].ingAmount.toString().toDouble()
    }

    fun getIngredientUnitAtIndex(index: Int): String {
        return ingredients[index].ingUnit.toString()
    }
}