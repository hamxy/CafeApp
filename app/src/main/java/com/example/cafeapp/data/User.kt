package com.example.cafeapp.data

data class User(
    val firstName: String,
    val lastName: String,
    val email: String

) {
    constructor(): this("", "", "")
}
