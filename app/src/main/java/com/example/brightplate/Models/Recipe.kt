package com.example.brightplate.models


class Recipe
{
    private lateinit var recipeName: String
    private lateinit var recipeDesc: String

    fun getRecipeName(): String
    {
        return recipeName
    }

    fun getRecipeDescription(): String
    {
        return recipeDesc
    }
}

