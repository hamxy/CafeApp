package com.example.cafeapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FullNameActivity : AppCompatActivity() {

    private lateinit var fullNameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_name)

        // initialise variables
        fullNameTextView = findViewById(R.id.fullNameTextView)
        fullNameTextView.text = intent.getStringExtra("fullName")
    }
}