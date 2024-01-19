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
        setContentView(R.layout.activity_main)

        // initialise Firebase Auth
        auth = Firebase.auth

        // Check if user is signed in
        // If user isn't signed in, redirect to login activity
        if (auth.currentUser == null) {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        // initialise navigation
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        // switch between fragments
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.bottom_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.bottom_cart -> {
                    replaceFragment(CartFragment())
                    true
                }
                R.id.bottom_orders -> {
                    replaceFragment(OrdersFragment())
                    true
                }
                R.id.bottom_profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }

        // initial fragment
        replaceFragment(HomeFragment())






//        // update view of current logged in user
//        // logout and redirect to login screen
//        submit.setOnClickListener {
//            auth.signOut()
//            val intent = Intent(this, SignInActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
//            finish()
//        }


    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()
    }
}