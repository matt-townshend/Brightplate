package com.example.brightplate

import android.media.Image


class Recipe (val ingredientList: ArrayList<Ingredient>,
              val review: Review,
              val instructions: String,
              val cookTime: Int,
              val prepTime: Int,
              val equipment: Array<String>,
              var filter: String,
              var image: Image){



}