package com.example.cafeapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.cafeapp.R
import com.example.cafeapp.SignInActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser: TextView
    private lateinit var logout: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Firebase
        auth = Firebase.auth

        // Views
        currentUser = view.findViewById(R.id.currentUser)
        logout = view.findViewById(R.id.logout)

        // Display logged user
        currentUser.text = auth.currentUser?.email.toString()

        // logout
        logout.setOnClickListener {
            auth.signOut()

            val intent = Intent(activity, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

}