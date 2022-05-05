package com.example.brightplate.main

import com.example.brightplate.controllers.InventoryEditActivity
import org.junit.Assert.*
import org.junit.Test

class InventoryEditActivityTest {

    private val ingredientActv: InventoryEditActivity = InventoryEditActivity()
    @Test
    fun testUnitValidation() {
        var result1 = ingredientActv.isInputValid("grams", "kilograms")
        var expectedResult1 = false

        var result2 = ingredientActv.isInputValid("grams", "KILOGRAMS")
        var expectedResult2 = false

        var result3 = ingredientActv.isInputValid("grams", "GRAMS")
        var expectedResult3 = true

        var result4 = ingredientActv.isInputValid("KILOGRAMS", "kilograms")
        var expectedResult4 = true

        assertEquals(expectedResult1, result1)
        assertEquals(expectedResult2, result2)
        assertEquals(expectedResult3, result3)
        assertEquals(expectedResult4, result4)
    }

    @Test
    fun testInputValidation() {
        var result1 = ingredientActv.isInputValid("", "")
        var expectedResult1 = false

        var result2 = ingredientActv.isInputValid("sugar", "grams")
        var expectedResult2 = false

        assertEquals(expectedResult1, result1)
        assertEquals(expectedResult2, result2)
    }

}