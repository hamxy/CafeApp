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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging

class SignInActivity : AppCompatActivity() {

    // Lateinit variables for Firebase authentication and UI elements
    private lateinit var auth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var signIn: Button
    private lateinit var signUpTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in) // Sets the layout for the activity

        // Initialize Firebase Authentication
        auth = Firebase.auth
        FirebaseApp.initializeApp(this)

        // Redirect to MainActivity if the user is already signed in
        if (auth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // Prevents returning to SignInActivity
            startActivity(intent)
        }

        // Initialize UI components by finding them in the layout
        email = findViewById(R.id.emailEditText)
        password = findViewById(R.id.passwordEditText)
        signIn = findViewById(R.id.signUpBtn)
        signUpTextView = findViewById(R.id.signUpTextView)

        // Set a click listener on the sign-in button
        signIn.setOnClickListener {
            val emailText = email.text.toString()
            val passwordText = password.text.toString()

            // Check if the email and password fields are not empty
            if (emailText.isNotEmpty() && passwordText.isNotEmpty()) {
                // Sign in with email and password using Firebase Authentication
                auth.signInWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Successful sign in
                            Log.d("success", "signInWithEmail:success")

                            // Fetch the FCM token for push notifications
                            FirebaseMessaging.getInstance().token.addOnCompleteListener { tokenTask ->
                                if (tokenTask.isSuccessful) {
                                    val fcmToken = tokenTask.result
                                    // Store the FCM token in Firestore
                                    storeFcmTokenForUser(fcmToken)
                                }
                            }

                            // Redirect to MainActivity after sign in
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        } else {
                            // Sign in failed
                            Log.w("error", "signInWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        // Set a click listener on the sign-up TextView to redirect to the SignUpActivity
        signUpTextView.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    // Function to store the FCM token in Firestore
    private fun storeFcmTokenForUser(fcmToken: String) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val usersCollection = FirebaseFirestore.getInstance().collection("users")
            val userDocument = usersCollection.document(currentUser.email.toString())
            userDocument.update("fcmToken", fcmToken)
                .addOnSuccessListener {
                    Log.d("FCM", "FCM token updated successfully")
                }
                .addOnFailureListener { e ->
                    Log.w("FCM", "Error updating FCM token", e)
                }
        }
    }
}