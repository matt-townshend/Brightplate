package com.example.brightplate.controllers


// this interface is used to provide a synchronous approach to retrieving data from a firebase database snapshot
// as firebase data snapshots are retrieved asynchronously, and code within android will continue
// to execute while the data is being retrieved. lists of recipes will be taken from the databse and
// then stored here before being passed off to their respective activities/functions
interface RecipeListCallback {
    fun onCallback(value:ArrayList<String>)
}