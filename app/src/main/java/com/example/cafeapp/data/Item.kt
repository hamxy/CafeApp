package com.example.cafeapp.data

data class Item(
    val category: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val available: Boolean = true
) {

    // Explicit no-argument constructor
    constructor() : this("", "", 0.0)
}