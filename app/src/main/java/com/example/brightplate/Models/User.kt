package com.example.brightplate.Models

import java.util.*

class User
{
    var name: String = ""
    var surname: String = ""
    var userID: String = ""
    var gender: String = ""
    var weight: Int = 0
    var height: Float = 0.0F
    val scan: Scanner = Scanner(System.`in`)

    fun createUser(): Boolean
    {
        var flag: Boolean = false

        try {
            val user = User()
            user.userID += 1
            user.name = scan.nextLine()
            user.surname = scan.nextLine()
            user.gender = scan.nextLine()
            user.weight = scan.nextInt()
            user.height = scan.nextFloat()
            flag = true
        }
        catch (e: Exception)
        {
            println("Error creating user")
        }

        return flag
    }




}