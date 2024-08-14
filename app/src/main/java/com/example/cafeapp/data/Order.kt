package com.example.cafeapp.data

data class Order(
    val customerId: String = "",
    val orderDate: String = "",
    val orderTime: String = "",
    val orderStatus: String = "",
    val products: List<Item> = listOf()
)
