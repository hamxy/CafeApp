package com.example.cafeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var signIn: Button
    private lateinit var signUpTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Check if user is signed in non-null


        // initialise views
        email = findViewById(R.id.emailEditText)
        password = findViewById(R.id.passwordEditText)
        signIn = findViewById(R.id.signUpBtn)
        signUpTextView = findViewById(R.id.signUpTextView)


        // redirect to SignUp activity
        signUpTextView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }




    }
}