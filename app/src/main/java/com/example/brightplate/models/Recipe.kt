package com.example.brightplate.models


class Recipe
{
    private lateinit var recipeName: String
    private lateinit var recipeDesc: String

    constructor(recipeName: String)
    {
        this.recipeName = recipeName
    }

    fun getRecipeName(): String
    {
        return recipeName
    }

    fun getRecipeDescription(): String
    {
        return recipeDesc
    }
}

