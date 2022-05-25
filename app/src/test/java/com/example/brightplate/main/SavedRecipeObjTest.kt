package com.example.brightplate.main

import com.example.brightplate.models.SavedRecipeObj
import org.junit.Assert.*

import org.junit.Test

class SavedRecipeObjTest {

//    @Test
//    fun saveRecipeTest() {
//    }
//
//    @Test
//    fun removeSavedRecipeTest() {
//    }

    @Test
    fun isInputValid() {
        var pass1 = "rice"
        var unit1 = "GRAMS"
        var expected1 = true
        var pass2 = "sugar"
        var unit2 = "kilograms"
        var expected2 = true
        var pass3 = "water"
        var unit3 = "liters"
        var expected3 = true
        var pass4 = ""
        var unit4 = ""
        var expected4 = false;

        assertEquals(expected1, SavedRecipeObj.isInputValid(pass1, unit1))
        assertEquals(expected2, SavedRecipeObj.isInputValid(pass2, unit2))
        assertEquals(expected3, SavedRecipeObj.isInputValid(pass3, unit3))
        assertEquals(expected4, SavedRecipeObj.isInputValid(pass4, unit4))
    }

    @Test
    fun isUnitValid() {
        var unit1 = "grams"
        var unit2 = "GRAMS"
        var expected1 = true
        var unit3 = "kg"
        var unit4 = "KG"
        var expected2 = true
        var unit5 = "kilograms"
        var unit6 = "liters"
        var expected3 = false

        assertEquals(expected1, SavedRecipeObj.isUnitValid(unit1, unit2))
        assertEquals(expected2, SavedRecipeObj.isUnitValid(unit3, unit4))
        assertEquals(expected3, SavedRecipeObj.isUnitValid(unit5, unit6))
    }



}