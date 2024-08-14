package com.example.cafeapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeapp.MainActivity
import com.example.cafeapp.R
import com.example.cafeapp.data.Order
import com.example.cafeapp.helpers.CartManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class CartFragment : Fragment(), CartAdapter.CartItemListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var userEmail: String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // auth
        auth = Firebase.auth
        if (auth.currentUser != null){
            userEmail = auth.currentUser!!.email.toString()
        }


        // Initialize RecyclerView for cart items
        val cartRecyclerView: RecyclerView = view.findViewById(R.id.cartRecyclerView)
        cartRecyclerView.layoutManager = LinearLayoutManager(activity)
        cartRecyclerView.setHasFixedSize(true)

        val cartAdapter = CartAdapter(CartManager.getItems().toMutableList(), this)
        cartRecyclerView.adapter = cartAdapter


        // views
        val totalValueText: TextView = view.findViewById(R.id.textView)
        val orderButton: Button = view.findViewById(R.id.button)
        val totalValue = CartManager.getTotalValue()

        // get cart items
        val cartItems = CartManager.getItems().toMutableList()


        if (cartItems.isEmpty()) {
            totalValueText.text = "Your cart is empty"
            orderButton.visibility = View.GONE
        } else {
            totalValueText.text = "Total: $${totalValue}"
            orderButton.visibility = View.VISIBLE
        }

        orderButton.setOnClickListener {
            placeOrder(userEmail)
        }

    }

    override fun onItemRemoved() {
        updateCartView()
    }

    private fun updateCartView() {
        val totalValue = CartManager.getTotalValue()
        val totalValueText: TextView = requireView().findViewById(R.id.textView)
        val orderButton: Button = requireView().findViewById(R.id.button)

        if (CartManager.getItems().isEmpty()) {
            totalValueText?.text = "Your cart is empty"
            orderButton?.visibility = View.GONE
        } else {
            totalValueText?.text = "Total: $${totalValue}"
            orderButton?.visibility = View.VISIBLE
        }
    }

    private fun placeOrder(userEmail: String) {
        // Prepare the order data here
        val formatterDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formatterTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val currentDate = formatterDate.format(Date())
        val currentTime = formatterTime.format(Date())

        val cartItems = CartManager.getItems() // Cart items

        val newOrder = Order(
            customerId = userEmail,
            orderDate = currentDate,
            orderTime = currentTime,
            orderStatus = "Pending",
            products = cartItems
        )
        // Upload order to Firestore
        uploadOrderToFirestore(newOrder)

    }

    private fun uploadOrderToFirestore(order: Order) {
        val db = FirebaseFirestore.getInstance()
        db.collection("orders")
            .add(order)
            .addOnSuccessListener { documentReference ->
                Log.d("Firestore", "DocumentSnapshot added with ID: ${documentReference.id}")

                // Clear the cart
                CartManager.clearCart()

                // Navigate the user to orders fragment
                navigateToOrdersFragment()

                // Show a long toast message
                Toast.makeText(requireContext(), "Order placed successfully!", Toast.LENGTH_LONG).show()

            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error adding document", e)
                // Handle the error
            }
    }

    private fun navigateToOrdersFragment() {
        if (activity is MainActivity) {
            val mainActivity = activity as MainActivity
            mainActivity.replaceFragment(OrdersFragment())
            mainActivity.updateBottomNavigationSelection(R.id.bottom_orders)
        }
    }
}