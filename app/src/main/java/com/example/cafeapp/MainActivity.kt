package com.example.cafeapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.cafeapp.fragments.CartFragment
import com.example.cafeapp.fragments.HomeFragment
import com.example.cafeapp.fragments.OrdersFragment
import com.example.cafeapp.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Set the layout for this activity

        // Initialize Firebase Authentication
        auth = Firebase.auth

        // Redirect to login activity if no user is currently signed in
        if (auth.currentUser == null) {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        // Initialize the bottom navigation view
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        // Set up navigation item selection handling
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.bottom_home -> {
                    replaceFragment(HomeFragment()) // Switch to HomeFragment
                    true
                }
                R.id.bottom_cart -> {
                    replaceFragment(CartFragment()) // Switch to CartFragment
                    true
                }
                R.id.bottom_orders -> {
                    replaceFragment(OrdersFragment()) // Switch to OrdersFragment
                    true
                }
                R.id.bottom_profile -> {
                    replaceFragment(ProfileFragment()) // Switch to ProfileFragment
                    true
                }
                else -> false
            }
        }

        // Set the initial fragment to HomeFragment when the activity starts
        replaceFragment(HomeFragment())
    }

    // Function to replace the current fragment in the frame container
    fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()
    }

    // Function to update the selected item in the bottom navigation
    fun updateBottomNavigationSelection(itemId: Int) {
        bottomNavigationView.selectedItemId = itemId
    }
}
