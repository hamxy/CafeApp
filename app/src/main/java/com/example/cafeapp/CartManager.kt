package com.example.cafeapp

import com.example.cafeapp.data.Item

object CartManager {
    private val cartItems = mutableListOf<Item>()

    fun addItem(item: Item) {
        cartItems.add(item)
    }

    fun removeItem(item: Item) {
        cartItems.remove(item)
    }

    fun getItems(): List<Item> {
        return cartItems
    }

    // Optional: Method to clear the cart
    fun clearCart() {
        cartItems.clear()
    }

    fun getTotalValue(): Double {
        return cartItems.sumOf { it.price }
    }
}