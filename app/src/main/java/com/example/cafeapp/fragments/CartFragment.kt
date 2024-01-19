package com.example.cafeapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeapp.CartManager
import com.example.cafeapp.R


class CartFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView for cart items
        val cartRecyclerView: RecyclerView = view.findViewById(R.id.cartRecyclerView)
        cartRecyclerView.layoutManager = LinearLayoutManager(activity)
        cartRecyclerView.setHasFixedSize(true)

        // Use an adapter to display the items
        cartRecyclerView.adapter = CartAdapter(CartManager.getItems().toMutableList())

        // views
        val totalValueText: TextView = view.findViewById(R.id.textView)
        val orderButton: Button = view.findViewById(R.id.button)
        val totalValue = CartManager.getTotalValue()



        val cartItems = CartManager.getItems().toMutableList()

        if (cartItems.isEmpty()) {
            totalValueText.text = "Your cart is empty"
            orderButton.visibility = View.GONE
        } else {
            totalValueText.text = "Total: $${totalValue}"
            orderButton.visibility = View.VISIBLE
        }

    }




}