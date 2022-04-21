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

    constructor() //Empty constructor

    constructor(name: String, surname: String, userID: String, gender: String, weight: Int, height: Float)
    {
        this.name = name
        this.surname = surname
        this.userID = userID
        this.gender = gender
        this.weight = weight
        this.height = height
    }

    fun createUser()
    {

    }

}