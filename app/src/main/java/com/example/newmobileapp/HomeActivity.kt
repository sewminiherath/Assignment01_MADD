package com.example.newmobileapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var greetingText: TextView
    private lateinit var locationText: TextView
    private lateinit var profileImage: ImageView
    private lateinit var searchBar: TextView
    private lateinit var plannerButton: TextView
    private lateinit var exploreButton: TextView
    private lateinit var itinerariesButton: TextView
    private lateinit var emergencyButton: View
    private lateinit var emergencyIcon: ImageView
    private lateinit var emergencyText: TextView
    private lateinit var viewAllText: TextView
    private lateinit var navLocationIcon: ImageView
    private lateinit var navHomeIcon: ImageView
    private lateinit var navNotificationsIcon: ImageView
    private lateinit var navProfileIcon: ImageView
    // private lateinit var recommendedRecyclerView: RecyclerView // Commented out for static layout
    // private lateinit var bottomNavigation: BottomNavigationView // Commented out for static layout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        try {
            setContentView(R.layout.activity_home)
            Toast.makeText(this, "HomeActivity loaded successfully!", Toast.LENGTH_LONG).show()

            initializeViews()
            setupClickListeners()
            setupBottomNavigation()
            // setupRecyclerView() // Commented out for static layout
        } catch (e: Exception) {
            Toast.makeText(this, "Error in HomeActivity onCreate: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun initializeViews() {
        try {
            greetingText = findViewById(R.id.greeting_text)
            locationText = findViewById(R.id.location_text)
            profileImage = findViewById(R.id.profile_image)
            searchBar = findViewById(R.id.search_placeholder)
            plannerButton = findViewById(R.id.planner_text)
            exploreButton = findViewById(R.id.explore_text)
            itinerariesButton = findViewById(R.id.itineraries_text)
            emergencyButton = findViewById(R.id.emergency_button_container)
            emergencyIcon = findViewById(R.id.emergency_icon)
            emergencyText = findViewById(R.id.emergency_text)
            
            // Debug: Check if emergency button is found
            if (emergencyButton != null) {
                Toast.makeText(this, "Emergency button found successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Emergency button NOT found!", Toast.LENGTH_LONG).show()
            }
            viewAllText = findViewById(R.id.view_all_text)
            navLocationIcon = findViewById(R.id.nav_location_icon)
            navHomeIcon = findViewById(R.id.nav_home_icon)
            navNotificationsIcon = findViewById(R.id.nav_notifications_icon)
            navProfileIcon = findViewById(R.id.nav_profile_icon)
            
                // Debug: Check if profile icon is found
                if (navProfileIcon != null) {
                    Toast.makeText(this, "Profile icon found successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Profile icon NOT found!", Toast.LENGTH_LONG).show()
                }
            // Remove RecyclerView for now since we're using static layout
            // recommendedRecyclerView = findViewById(R.id.recommendedRecyclerView)
            // bottomNavigation = findViewById(R.id.bottomNavigation)
        } catch (e: Exception) {
            Toast.makeText(this, "Error initializing views: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }


    private fun setupClickListeners() {
        profileImage.setOnClickListener {
            Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
        }

        searchBar.setOnClickListener {
            Toast.makeText(this, "Search clicked", Toast.LENGTH_SHORT).show()
        }

        plannerButton.setOnClickListener {
            val intent = Intent(this, Planner2Activity::class.java)
            startActivity(intent)
        }

        exploreButton.setOnClickListener {
            val intent = Intent(this, RecommendedActivity::class.java)
            startActivity(intent)
        }

        itinerariesButton.setOnClickListener {
            val intent = Intent(this, Planner2Activity::class.java)
            startActivity(intent)
        }

        // Test emergency button click
        emergencyButton.setOnClickListener { 
            Toast.makeText(this, "Emergency container clicked!", Toast.LENGTH_SHORT).show()
            handleEmergencyClick() 
        }
        emergencyIcon.setOnClickListener { 
            Toast.makeText(this, "Emergency icon clicked!", Toast.LENGTH_SHORT).show()
            handleEmergencyClick() 
        }
        emergencyText.setOnClickListener { 
            Toast.makeText(this, "Emergency text clicked!", Toast.LENGTH_SHORT).show()
            handleEmergencyClick() 
        }

        viewAllText.setOnClickListener {
            val intent = Intent(this, RecommendedActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupBottomNavigation() {
        navLocationIcon.setOnClickListener {
            val intent = Intent(this, RecommendedActivity::class.java)
            startActivity(intent)
        }

        navHomeIcon.setOnClickListener {
            Toast.makeText(this, "Home clicked", Toast.LENGTH_SHORT).show()
        }

        navNotificationsIcon.setOnClickListener {
            val intent = Intent(this, NotificationsActivity::class.java)
            startActivity(intent)
        }

        navProfileIcon.setOnClickListener {
            Toast.makeText(this, "Profile icon clicked!", Toast.LENGTH_SHORT).show()
            try {
                Toast.makeText(this, "Creating intent for ProfileActivity...", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ProfileActivity::class.java)
                Toast.makeText(this, "Intent created successfully", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                Toast.makeText(this, "Navigating to Profile...", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Error navigating to Profile: ${e.message}", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }
    }


    private fun handleEmergencyClick() {
        try {
            Toast.makeText(this, "Emergency button clicked!", Toast.LENGTH_SHORT).show()
            
            // Check if EmergencyActivity class exists
            try {
                val intent = Intent(this, EmergencyActivity::class.java)
                Toast.makeText(this, "Intent created successfully", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                Toast.makeText(this, "Opening Emergency Screen...", Toast.LENGTH_SHORT).show()
            } catch (e: ClassNotFoundException) {
                Toast.makeText(this, "EmergencyActivity class not found: ${e.message}", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Error creating intent: ${e.message}", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error in handleEmergencyClick: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
