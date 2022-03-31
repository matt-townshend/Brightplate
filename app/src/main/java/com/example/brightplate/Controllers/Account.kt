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

    fun createAccount(): Boolean
    {
        var flag: Boolean = false

        try
        {
            val account = Account()

            user.createUser()
            account.accEmail = scan.nextLine()
            account.accPass = scan.nextLine()
            account.accID += 1
            account.userID = user.userID

            flag = true
        }
        catch (e: Exception)
        {
            println("Error creating account")
        }

        return flag
    }

}