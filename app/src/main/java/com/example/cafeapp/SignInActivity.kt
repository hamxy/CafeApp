package com.example.cafeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var signIn: Button
    private lateinit var signUpTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Initialize Firebase Auth
        auth = Firebase.auth
        FirebaseApp.initializeApp(this)


        // Check if user is signed in (non-null) and update UI accordingly.
        if (auth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)

            // Clear top to prevent going back to login screen
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            // Start activity
            startActivity(intent)
        }

        // Initialise views
        email = findViewById(R.id.emailEditText)
        password = findViewById(R.id.passwordEditText)
        signIn = findViewById(R.id.signUpBtn)
        signUpTextView = findViewById(R.id.signUpTextView)

        // SignIn
        signIn.setOnClickListener {
            val emailText: String = email.text.toString()
            val passwordText: String = password.text.toString()

            if (emailText.isNotEmpty() && passwordText.isNotEmpty()) {
                auth.signInWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, redirect user to main activity
                            Log.d("success", "signInWithEmail:success")

                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("error", "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }
        }


        // redirect to SignUp activity
        signUpTextView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }




    }
}