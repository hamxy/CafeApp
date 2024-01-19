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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SignUpActivity : AppCompatActivity() {

    // references:
    // https://firebase.google.com/docs/auth/android/password-auth

    private lateinit var auth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var passwordRetype: EditText
    private lateinit var signUpButton: Button
    private lateinit var signUpTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Initialise views
        email = findViewById(R.id.emailEditText)
        password = findViewById(R.id.passwordEditText)
        passwordRetype = findViewById(R.id.passwordRetypeEditText)
        signUpButton = findViewById(R.id.signUpBtn)
        signUpTextView = findViewById(R.id.signUpTextView)

        // redirect to login
        signUpTextView.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        // Create a new user
        signUpButton.setOnClickListener {
            val emailText: String = email.text.toString()
            val passwordText: String = password.text.toString()
            val passwordRetypeText: String = passwordRetype.text.toString()

            // TODO: verify if emailText is a valid email address


            // check if views are not empty
            if (emailText.isNotEmpty() && passwordText.isNotEmpty() && passwordRetypeText.isNotEmpty()){
                // check if passwords are matching
                if (passwordText == passwordRetypeText){
                    // register a new user
                    auth.createUserWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(this) {
                        task ->
                        if (task.isSuccessful) {
                            // Sign-up success, log in and redirect to main activity
                            Log.d("success", "createUserWithEmail:success")

                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                        else {
                            // If sign in fails, display a message to the user
                            Log.d("error", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}