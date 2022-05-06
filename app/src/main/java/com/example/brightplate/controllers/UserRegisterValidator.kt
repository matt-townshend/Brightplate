package com.example.brightplate.controllers

import java.util.regex.Pattern

object UserRegisterValidator {


    //will check that the provided password matches our rules as displayed below
    fun checkPass(pass: String): Boolean {
        val passwordREGEX = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +         // at least 1 digit
                    "(?=.*[a-zA-Z])" +      // any letter
                    "(?=.*[!@#$%^&+=])" +    // at least 1 special character
                    "(?=\\S+$)" +           // no white spaces
                    ".{8,}" +               // at least 8 characters
                    "$"
        )
        // will return a boolean value that results in true if the password matched the criteria,
        // and false if it did not match the criteria
        return passwordREGEX.matcher(pass).matches()
    }

    // will check that the provided username matches our rules as displayed below
    fun checkUsername(username: String): Boolean {
        val usernameREGEX = Pattern.compile("^" +
                "[a-zA-" + // any letter
                "Z0-9]" + // at least 1 digit
                "{5,15}" // is between 5 and 15 characters
                + "$")
        // will return a boolean value that results in true if the username matched the criteria,
        // and false if it did not match the criteria
        return usernameREGEX.matcher(username).matches()
    }

    // will generate a message based on the provided credentials and why it succeeded or failed
    fun createUser(
        email: String,
        username: String,
        password: String,
        confirmPassword: String
    ): String {

        if (email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) { // checks that all fields are filled
            if (checkUsername(username)) { // checks that username matches criteria
                if (password == confirmPassword) { // checks that password matches confirmation password
                    if (checkPass(password)) { // checks that password matches criteria
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