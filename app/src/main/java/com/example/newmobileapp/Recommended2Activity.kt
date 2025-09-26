package com.example.newmobileapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Recommended2Activity : AppCompatActivity() {

    private lateinit var navLocation: ImageView
    private lateinit var navHome: ImageView
    private lateinit var navNotifications: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var dotActive: View
    private lateinit var dotInactive: View
    
    // Destination cards
    private lateinit var destination1Card: RelativeLayout
    private lateinit var destination2Card: RelativeLayout
    private lateinit var destination3Card: RelativeLayout
    private lateinit var destination4Card: RelativeLayout
    private lateinit var destination5Card: RelativeLayout
    private lateinit var destination6Card: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommended2)

        initializeViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        navLocation = findViewById(R.id.nav_location)
        navHome = findViewById(R.id.nav_home)
        navNotifications = findViewById(R.id.nav_notifications)
        navProfile = findViewById(R.id.nav_profile)
        dotActive = findViewById(R.id.dot_active)
        dotInactive = findViewById(R.id.dot_inactive)
        
        // Initialize destination cards
        destination1Card = findViewById(R.id.destination1_card)
        destination2Card = findViewById(R.id.destination2_card)
        destination3Card = findViewById(R.id.destination3_card)
        destination4Card = findViewById(R.id.destination4_card)
        destination5Card = findViewById(R.id.destination5_card)
        destination6Card = findViewById(R.id.destination6_card)
    }

    private fun setupClickListeners() {
        // Navigation click listeners
        navLocation.setOnClickListener {
            // Already on recommended page, do nothing or show feedback
        }

        navHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        navNotifications.setOnClickListener {
            val intent = Intent(this, NotificationsActivity::class.java)
            startActivity(intent)
            finish()
        }

        navProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Pagination dots
        dotActive.setOnClickListener {
            // Already on page 2, do nothing
        }

        dotInactive.setOnClickListener {
            val intent = Intent(this, RecommendedActivity::class.java)
            startActivity(intent)
            finish()
        }
        
        // Destination card click listeners
        destination1Card.setOnClickListener {
            openWebsite("https://www.srilanka.travel/polonnaruwa")
        }
        
        destination2Card.setOnClickListener {
            openWebsite("https://www.srilanka.travel/yapahuwa")
        }
        
        destination3Card.setOnClickListener {
            openWebsite("https://www.srilanka.travel/jaffna")
        }
        
        destination4Card.setOnClickListener {
            openWebsite("https://www.srilanka.travel/beaches")
        }
        
        destination5Card.setOnClickListener {
            openWebsite("https://www.srilanka.travel/sinharaja")
        }
        
        destination6Card.setOnClickListener {
            openWebsite("https://www.srilanka.travel/waterfalls")
        }
    }
    
    private fun openWebsite(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Unable to open website", Toast.LENGTH_SHORT).show()
        }
    }
}
