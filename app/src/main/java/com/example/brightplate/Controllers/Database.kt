package com.example.brightplate.controllers

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Database
{
    val url: String = "https://brightplate-b45c1-default-rtdb.firebaseio.com/"
    val database = Firebase.database(url)
    val myRef = database.getReference("")
}