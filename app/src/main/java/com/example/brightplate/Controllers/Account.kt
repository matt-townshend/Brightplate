package com.example.brightplate.Controllers

import com.example.brightplate.Models.User
import java.util.*

class Account
{
    var accID: Int = 0
    var accEmail: String = ""
    var accPass: String = ""
    var userID: String = ""
    val user = User()
    val scan: Scanner = Scanner(System.`in`)

    constructor() // Empty constructor

    constructor(accID: Int, accEmail: String, accPass: String, userID: String)
    {
        this.accID = accID
        this.accEmail = accEmail
        this.accPass = accPass
        this.userID = userID
    }

    fun createAccount()
    {

    }


}