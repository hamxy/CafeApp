package com.example.cafeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class MainActivity : AppCompatActivity() {

//    private lateinit var nameEditText: EditText
//    private lateinit var surnameEditText: EditText
//    private lateinit var displayTextView: TextView
//    private lateinit var submitButton: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var submit: Button
    private lateinit var loggedAs: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialise Firebase Auth
        auth = Firebase.auth

        // initialise views
        submit = findViewById(R.id.logoutBtn)
        loggedAs = findViewById(R.id.loggedAsTextView)

        // Check if user is signed in
        // If user isn't signed in, redirect to login activity
        val currentUser = auth.currentUser
        if (currentUser == null) {
            startActivity(Intent(this, SignInActivity::class.java))
        }
        else {
            loggedAs.text = currentUser.email
        }

        // update view of current logged in user


        // logout and redirect to login screen
        submit.setOnClickListener {
            auth.signOut()
            intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }






        // OLD
//        // Initialise views
//        nameEditText = findViewById(R.id.nameEditText)
//        surnameEditText = findViewById(R.id.surnameEditText)
//        submitButton = findViewById(R.id.submitButton)
//
//        // Set onClickListener on Button
//        submitButton.setOnClickListener {
//            val fullName = fullName(nameEditText, surnameEditText)
//            val intent = Intent(this, FullNameActivity::class.java)
//
//            intent.putExtra("fullName", fullName)
//            startActivity(intent)
//
//        }
    }

    private fun fullName(name: EditText, surname: EditText): String {
        return "${name.text.toString()} ${surname.text.toString()}"
    }
}