package com.example.brightplate.main

import com.example.brightplate.controllers.UserRegisterValidator
import org.junit.Assert.*

import org.junit.Test

class UserSignupTest {



    @Test
    fun checkPass() {
        val pass1 = "password@" //no number present: should return false
        val pass2 = "pass" //not enough characters: should return false
        val pass3 = "pass word" //white spaces present: should return false
        val pass4 = "password2" //no special character present: should return false
        val pass5 = "password@2" //successful password: should return true

        assertEquals(false, UserRegisterValidator.checkPass(pass1))
        assertEquals(false, UserRegisterValidator.checkPass(pass2))
        assertEquals(false, UserRegisterValidator.checkPass(pass3))
        assertEquals(false, UserRegisterValidator.checkPass(pass4))
        assertEquals(true, UserRegisterValidator.checkPass(pass5))
    }

    @Test
    fun checkUsername() {
        val user1 = "user123@" //contains special character: should return false
        val user2 = "us1" //less than 5 characters: should return false
        val user3 = "useruseruseruseruseruser123" //more than 15 characters: should return false
        val user4 = "user123" //successful username: should return true

        assertEquals(false, UserRegisterValidator.checkUsername(user1))
        assertEquals(false, UserRegisterValidator.checkUsername(user2))
        assertEquals(false, UserRegisterValidator.checkUsername(user3))
        assertEquals(true, UserRegisterValidator.checkUsername(user4))
    }

    @Test
    fun createUser() {

        //contains mis-matching passwords: should return "Password's don't match"
        val email1 = "test@gmail.com"
        val username1 = "test123"
        val pass1 = "password123!"
        val confirmPass1 = "password122!"

        //contains password with less than 8 charcters: should return "Password requires a minimum of 8 characters, at least one number, and at least one special character"
        val email2 = "test@gmail.com"
        val username2 = "test123"
        val pass2 = "pass1!"
        val confirmPass2 = "pass1!"

        //contains special charcters in username: should return "No special characters allowed in username"
        val email3 = "test@gmail.com"
        val username3 = "test123@!"
        val pass3 = "password123!"
        val confirmPass3 = "password123!"

        //contains missing field or fields: should return "Please fill out all fields"
        val email4 = "test@gmail.com"
        val username4 = ""
        val pass4 = "password123!"
        val confirmPass4 = "password123!"

        //successful user: should return "Account successfully created"
        val email5 = "test@gmail.com"
        val username5 = "test123"
        val pass5 = "password123!"
        val confirmPass5 = "password123!"

        assertEquals("Password's don't match",
            UserRegisterValidator.createUser(email1,username1,pass1,confirmPass1))
        assertEquals("Password requires a minimum of 8 characters, at least one number, and at least one special character",
            UserRegisterValidator.createUser(email2,username2,pass2,confirmPass2))
        assertEquals("No special characters allowed in username",
            UserRegisterValidator.createUser(email3,username3,pass3,confirmPass3))
        assertEquals("Please fill out all fields",
            UserRegisterValidator.createUser(email4,username4,pass4,confirmPass4))
        assertEquals("Account successfully created",
            UserRegisterValidator.createUser(email5,username5,pass5,confirmPass5))

    }
}