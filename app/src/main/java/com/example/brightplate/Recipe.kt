package com.example.brightplate

import android.media.Image


class Recipe (val ingredientList: ArrayList<Ingredient>,
              val review: ArrayList<Review>,
              val instructions: String,
              val cookTime: Int,
              val prepTime: Int,
              val equipment: ArrayList<String>,
              var filter: String,){



}