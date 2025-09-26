package com.example.newmobileapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newmobileapp.HomeActivity
import com.example.newmobileapp.R
import com.example.newmobileapp.services.AuthService
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var signInButton: RelativeLayout
    private lateinit var forgotPasswordText: TextView
    private lateinit var signUpButton: Button
    private lateinit var authService: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize authentication service
        authService = AuthService()
        
        // Check if user is already signed in
        checkIfUserIsSignedIn()

        Toast.makeText(this, "SMART TRAVEL APP v1.0 - Sign In Screen!", Toast.LENGTH_LONG).show()

        initializeViews()
        setupClickListeners()
    }
    
    private fun checkIfUserIsSignedIn() {
        // Always show sign-in screen first - no automatic redirect
        Toast.makeText(this, "Welcome! Please sign in or create an account.", Toast.LENGTH_SHORT).show()
    }

    private fun initializeViews() {
        emailEditText = findViewById(R.id.et_email)
        passwordEditText = findViewById(R.id.et_password)
        signInButton = findViewById(R.id.signin_button)
        forgotPasswordText = findViewById(R.id.forgot_password)
        signUpButton = findViewById(R.id.signup_link)
    }

    private fun setupClickListeners() {
        signInButton.setOnClickListener {
            Toast.makeText(this, "Sign in button clicked!", Toast.LENGTH_SHORT).show()
            handleSignIn()
        }

        forgotPasswordText.setOnClickListener {
            handleForgotPassword()
        }

        signUpButton.setOnClickListener {
            Toast.makeText(this, "Sign up button clicked!", Toast.LENGTH_SHORT).show()
            handleSignUp()
        }
    }

    private fun handleSignIn() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        // Clear previous errors
        emailEditText.error = null
        passwordEditText.error = null

        // Validate input
        if (email.isEmpty()) {
            emailEditText.error = "Please enter your email"
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.isEmpty()) {
            passwordEditText.error = "Please enter your password"
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
            return
        }

        // Show loading message
        Toast.makeText(this, "Signing in...", Toast.LENGTH_SHORT).show()

        // Skip Firebase authentication for now - just navigate to home
        Toast.makeText(this, "Sign in successful (Firebase disabled)", Toast.LENGTH_SHORT).show()
        
        // Navigate to home screen
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handleForgotPassword() {
        Toast.makeText(this, "Forgot Password clicked", Toast.LENGTH_SHORT).show()
        try {
            // Navigate to Forgot Password activity
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Navigating to Forgot Password...", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error navigating to Forgot Password: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun handleSignUp() {
        Toast.makeText(this, "Sign up button clicked!", Toast.LENGTH_SHORT).show()
        try {
            // Navigate to Create Account activity
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Navigating to Create Account...", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error navigating to Create Account: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
