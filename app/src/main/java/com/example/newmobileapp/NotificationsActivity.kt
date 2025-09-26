package com.example.newmobileapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newmobileapp.ProfileActivity
import com.example.newmobileapp.R

class NotificationsActivity : AppCompatActivity() {

    private lateinit var locationIcon: ImageView
    private lateinit var homeIcon: ImageView
    private lateinit var notificationsIcon: ImageView
    private lateinit var profileIcon: ImageView
    private lateinit var timeText: TextView
    private lateinit var notificationsTitle: TextView
    private lateinit var bellIcon: ImageView
    private lateinit var noNotificationsText1: TextView
    private lateinit var noNotificationsText2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        initializeViews()
        setupClickListeners()
        setupStatusBar()
    }

    private fun initializeViews() {
        // Navigation icons
        locationIcon = findViewById(R.id.locationIcon)
        homeIcon = findViewById(R.id.homeIcon)
        notificationsIcon = findViewById(R.id.notificationsIcon)
        profileIcon = findViewById(R.id.profileIcon)
        
        // Status bar elements
        timeText = findViewById(R.id.timeText)
        
        // Main content
        notificationsTitle = findViewById(R.id.notificationsTitle)
        bellIcon = findViewById(R.id.bellIcon)
        noNotificationsText1 = findViewById(R.id.noNotificationsText1)
        noNotificationsText2 = findViewById(R.id.noNotificationsText2)
    }

    private fun setupStatusBar() {
        // Set current time (you can make this dynamic if needed)
        timeText.text = "10:55"
    }

    private fun setupClickListeners() {
        locationIcon.setOnClickListener {
            val intent = Intent(this, RecommendedActivity::class.java)
            startActivity(intent)
            finish()
        }

        homeIcon.setOnClickListener {
            // Navigate to Home screen
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        notificationsIcon.setOnClickListener {
            // Already on Notifications screen
            Toast.makeText(this, "You are already on Notifications", Toast.LENGTH_SHORT).show()
        }

        profileIcon.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
