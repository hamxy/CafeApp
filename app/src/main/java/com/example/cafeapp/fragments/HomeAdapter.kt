package com.example.cafeapp.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeapp.CartManager
import com.example.cafeapp.R
import com.example.cafeapp.data.Item

class HomeAdapter(private val menuItems : ArrayList<Item>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.home_item_view,
            parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = menuItems[position]
        holder.itemName.text = currentItem.name
        holder.price.text = "$" + currentItem.price.toString()

        holder.addToCart.setOnClickListener {
            CartManager.addItem(currentItem)
            Toast.makeText(holder.itemView.context, "${currentItem.name} added to cart", Toast.LENGTH_SHORT).show()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val itemName : TextView = itemView.findViewById(R.id.itemName)
        val price: TextView = itemView.findViewById(R.id.price)
        val addToCart: Button = itemView.findViewById(R.id.addToCart)
    }
}