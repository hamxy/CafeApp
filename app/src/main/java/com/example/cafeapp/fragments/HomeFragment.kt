package com.example.cafeapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeapp.R
import com.example.cafeapp.data.Item
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private lateinit var menuRecyclerview : RecyclerView
    private lateinit var itemArrayList : ArrayList<Item>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Firebase
        val db = FirebaseFirestore.getInstance()

        // RecyclerView
        menuRecyclerview = view.findViewById(R.id.homeRecyclerView)
        menuRecyclerview.layoutManager = LinearLayoutManager(activity)
        menuRecyclerview.setHasFixedSize(true)

        // fetch database and populate arrayList
        itemArrayList = ArrayList<Item>()
        menuRecyclerview.adapter = HomeAdapter(itemArrayList)

        val menuCollection = db.collection("menu")
        menuCollection.get()
            .addOnSuccessListener { documents ->
            for (document in documents) {
                // Convert each document to an Item object
                val item = document.toObject(Item::class.java)
                itemArrayList.add(item)
            }

                // Notify the adapter that data has changed
                menuRecyclerview.adapter?.notifyDataSetChanged()
        }.addOnFailureListener { exception ->
            Log.e("FirestoreError", "Error fetching documents", exception)
            // You can also show a toast or debug message
            Toast.makeText(context, "Error: ${exception.message}", Toast.LENGTH_LONG).show()
        }


    }





}