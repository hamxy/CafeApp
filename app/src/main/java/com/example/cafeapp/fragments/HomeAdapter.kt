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

// HomeAdapter is a RecyclerView adapter for displaying menu items
class HomeAdapter(private val menuItems : ArrayList<Item>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    // Creates new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the custom layout for each item
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.home_item_view,
            parent, false)
        return ViewHolder(itemView)
    }

    // Returns the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return menuItems.size
    }

    // Replaces the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the contents of the view
        val currentItem = menuItems[position]
        holder.itemName.text = currentItem.name
        holder.price.text = "$" + currentItem.price.toString()

        // Set an OnClickListener on the 'Add to Cart' button
        holder.addToCart.setOnClickListener {
            // Add item to the cart and show a toast message
            CartManager.addItem(currentItem)
            Toast.makeText(holder.itemView.context, "${currentItem.name} added to cart", Toast.LENGTH_SHORT).show()
        }
    }

    // Provide a reference to the type of views that you are using (custom ViewHolder)
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val itemName : TextView = itemView.findViewById(R.id.itemName)
        val price: TextView = itemView.findViewById(R.id.price)
        val addToCart: Button = itemView.findViewById(R.id.addToCart)
    }
}
