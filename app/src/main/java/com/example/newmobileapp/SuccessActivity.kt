package com.example.newmobileapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.newmobileapp.R

class SuccessActivity : AppCompatActivity() {

    private lateinit var logInButton: Button
    private lateinit var timeText: TextView
    private lateinit var successTitle: TextView
    private lateinit var successMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        initializeViews()
        setupClickListeners()
        setupStatusBar()
    }

    private fun initializeViews() {
        logInButton = findViewById(R.id.logInButton)
        timeText = findViewById(R.id.timeText)
        successTitle = findViewById(R.id.successTitle)
        successMessage = findViewById(R.id.successMessage)
    }

    private fun setupStatusBar() {
        // Set current time (you can make this dynamic if needed)
        timeText.text = "10:55"
    }

    private fun setupClickListeners() {
        logInButton.setOnClickListener {
            handleLogIn()
        }
    }

    private fun handleLogIn() {
        // Navigate to Home screen after successful login
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}
