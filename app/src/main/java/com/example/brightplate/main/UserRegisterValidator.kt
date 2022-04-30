package com.example.brightplate.main

import java.util.regex.Pattern

object UserRegisterValidator {


    fun checkPass(pass: String): Boolean {
        val passwordREGEX = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[!@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$"
        )
        return passwordREGEX.matcher(pass).matches()
    }

    fun checkUsername(username: String): Boolean {
        val usernameREGEX = Pattern.compile("^" + "[a-zA-Z0-9]{5,15}" + "$")
        return usernameREGEX.matcher(username).matches()
    }

    fun createUser(
        email: String,
        username: String,
        password: String,
        confirmPassword: String
    ): String {

        if (email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
            if (checkUsername(username)) {
                if (password == confirmPassword) {
                    if (checkPass(password)) {
                        return "Account successfully created"

                    } else {
                        return "Password requires a minimum of 8 characters, at least one number, and at least one special character"
                    }
                } else {
                    return "Password's don't match"
                }
            } else {
                return "No special characters allowed in username"
            }
        }
        else {
            return "Please fill out all fields"
        }
    }
}