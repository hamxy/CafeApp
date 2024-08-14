package com.example.cafeapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeapp.R
import com.example.cafeapp.data.Order
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore


class OrdersFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var ordersRecyclerView: RecyclerView
    private lateinit var ordersAdapter: OrdersAdapter
    private var ordersList = mutableListOf<Order>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        ordersRecyclerView = view.findViewById(R.id.orderRecyclerView) // Assuming you have a RecyclerView in your layout
        ordersRecyclerView.layoutManager = LinearLayoutManager(context)

        fetchOrders()
    }

    private fun fetchOrders() {
        val currentUserEmail = auth.currentUser?.email.toString()
        val db = FirebaseFirestore.getInstance()
        db.collection("orders")
            .whereEqualTo("customerId", currentUserEmail)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val order = document.toObject(Order::class.java)
                    ordersList.add(order)
                }
                ordersAdapter = OrdersAdapter(ordersList)
                ordersRecyclerView.adapter = ordersAdapter
            }
            .addOnFailureListener { exception ->
                Log.w("Firestore", "Error getting documents: ", exception)
            }
    }

}