package com.example.newmobileapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var backArrow: ImageView
    private lateinit var emailEditText: TextInputEditText
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var sendResetLinkButton: Button
    private lateinit var backToSignInLink: TextView
    private lateinit var successContainer: LinearLayout
    private lateinit var successIcon: ImageView
    private lateinit var successMessage: TextView
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        Toast.makeText(this, "ForgotPasswordActivity loaded successfully!", Toast.LENGTH_SHORT).show()

        initializeViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        try {
            backArrow = findViewById(R.id.back_arrow)
            emailEditText = findViewById(R.id.et_email)
            emailInputLayout = findViewById(R.id.email_input_layout)
            sendResetLinkButton = findViewById(R.id.btn_send_reset_link)
            backToSignInLink = findViewById(R.id.back_to_signin_link)
            successContainer = findViewById(R.id.success_container)
            successIcon = findViewById(R.id.success_icon)
            successMessage = findViewById(R.id.success_message)

            Toast.makeText(this, "Forgot password views initialized successfully!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error initializing forgot password views: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupClickListeners() {
        // Back arrow click
        backArrow.setOnClickListener {
            Toast.makeText(this, "Back arrow clicked!", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Send reset link button click
        sendResetLinkButton.setOnClickListener {
            Toast.makeText(this, "Send reset link button clicked!", Toast.LENGTH_SHORT).show()
            handleSendResetLink()
        }

        // Back to sign in link click
        backToSignInLink.setOnClickListener {
            Toast.makeText(this, "Back to sign in clicked!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun handleSendResetLink() {
        val email = emailEditText.text.toString().trim()

        // Clear previous errors
        emailInputLayout.error = null

        // Validate email
        if (email.isEmpty()) {
            emailInputLayout.error = getString(R.string.enter_valid_email)
            Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInputLayout.error = getString(R.string.enter_valid_email)
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            return
        }

        // Show loading state
        sendResetLinkButton.text = getString(R.string.sending_reset_link)
        sendResetLinkButton.isEnabled = false

        // Try real Firebase password reset first, fallback to mock
        sendRealPasswordReset(email)
    }

    private fun sendRealPasswordReset(email: String) {
        try {
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Real email sent successfully
                        showSuccessMessage()
                        sendResetLinkButton.text = getString(R.string.send_reset_link)
                        sendResetLinkButton.isEnabled = true
                        Toast.makeText(this, "Reset link sent to $email", Toast.LENGTH_LONG).show()
                    } else {
                        // Firebase failed, show error and fallback to mock
                        Toast.makeText(this, "Firebase error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        Toast.makeText(this, "Using mock implementation...", Toast.LENGTH_SHORT).show()
                        simulateSendResetLink(email)
                    }
                }
        } catch (e: Exception) {
            // Firebase not configured, use mock
            Toast.makeText(this, "Firebase not configured, using mock...", Toast.LENGTH_SHORT).show()
            simulateSendResetLink(email)
        }
    }

    private fun simulateSendResetLink(email: String) {
        // For now, show success message (mock implementation)
        // In production, this would connect to Firebase Auth or email service
        
        emailEditText.postDelayed({
            try {
                // Show success message
                showSuccessMessage()
                
                // Reset button state
                sendResetLinkButton.text = getString(R.string.send_reset_link)
                sendResetLinkButton.isEnabled = true
                
                // Show info about mock implementation
                Toast.makeText(this, "Mock: Reset link would be sent to $email", Toast.LENGTH_LONG).show()
                Toast.makeText(this, "Note: This is a demo - no actual email sent", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Error sending reset link: ${e.message}", Toast.LENGTH_LONG).show()
                sendResetLinkButton.text = getString(R.string.send_reset_link)
                sendResetLinkButton.isEnabled = true
            }
        }, 2000) // 2 second delay to simulate network request
    }

    private fun showSuccessMessage() {
        try {
            // Hide main content
            findViewById<LinearLayout>(R.id.main_content_container).visibility = View.GONE
            
            // Show success container
            successContainer.visibility = View.VISIBLE
            
            Toast.makeText(this, "Success message displayed!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error showing success message: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
