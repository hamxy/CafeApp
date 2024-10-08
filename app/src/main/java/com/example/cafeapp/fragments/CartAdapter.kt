package com.example.cafeapp.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeapp.R
import com.example.cafeapp.data.Item
import com.example.cafeapp.helpers.CartManager


class CartAdapter(private val cartItems: MutableList<Item>, private val listener: CartItemListener) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    interface CartItemListener {
        fun onItemRemoved()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.cart_item_view,
            parent, false)
        return CartAdapter.ViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = cartItems[position]
        holder.itemName.text = currentItem.name
        holder.price.text = "$${currentItem.price}"

        holder.removeFromCart.setOnClickListener {
            CartManager.removeItem(currentItem)
            cartItems.removeAt(holder.adapterPosition)
            notifyItemRemoved(holder.adapterPosition)
            listener.onItemRemoved() // Notify the listener
            Toast.makeText(holder.itemView.context, "${currentItem.name} removed from cart", Toast.LENGTH_SHORT).show()
        }
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val itemName : TextView = itemView.findViewById(R.id.itemName)
        val price: TextView = itemView.findViewById(R.id.price)
        val removeFromCart : Button = itemView.findViewById(R.id.removeFromCart)
    }



    override fun getItemCount(): Int {
        return cartItems.size
    }
}