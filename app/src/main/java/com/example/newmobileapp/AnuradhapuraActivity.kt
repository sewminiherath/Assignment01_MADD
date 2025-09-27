package com.example.newmobileapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newmobileapp.models.PlannerItem
import com.example.newmobileapp.utils.PlannerManager

class AnuradhapuraActivity : AppCompatActivity() {

    private lateinit var addToPlannerButton: LinearLayout
    private lateinit var previousButton: ImageView
    private lateinit var nextButton: ImageView
    private lateinit var navHome: ImageView
    private lateinit var navLocation: ImageView
    private lateinit var navNotifications: ImageView
    private lateinit var navProfile: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anuradhapura)

        initializeViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        try {
            addToPlannerButton = findViewById(R.id.addToPlannerButton)
            previousButton = findViewById(R.id.previousButton)
            nextButton = findViewById(R.id.nextButton)
            navHome = findViewById(R.id.nav_home)
            navLocation = findViewById(R.id.nav_location)
            navNotifications = findViewById(R.id.nav_notifications)
            navProfile = findViewById(R.id.nav_profile)
        } catch (e: Exception) {
            Toast.makeText(this, "Error initializing views: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupClickListeners() {
        // Add to Planner Button
        addToPlannerButton.setOnClickListener {
            Toast.makeText(this, "Adding Anuradhapura to planner...", Toast.LENGTH_SHORT).show()
            addToPlanner()
        }

        // Navigation Buttons
        previousButton.setOnClickListener {
            Toast.makeText(this, "Going to Sigiriya...", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SigiriyaActivity::class.java)
            startActivity(intent)
            finish()
        }

        nextButton.setOnClickListener {
            Toast.makeText(this, "Going to next location...", Toast.LENGTH_SHORT).show()
            // For now, go back to Sigiriya as we only have 2 locations
            val intent = Intent(this, SigiriyaActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Bottom Navigation
        navHome.setOnClickListener {
            Toast.makeText(this, "Navigating to Home", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        navLocation.setOnClickListener {
            Toast.makeText(this, "Opening Google Maps for Anuradhapura...", Toast.LENGTH_SHORT).show()
            openGoogleMaps("8.3114,80.4037") // Anuradhapura coordinates
        }

        navNotifications.setOnClickListener {
            Toast.makeText(this, "Navigating to Notifications", Toast.LENGTH_SHORT).show()
            // Navigate to NotificationsActivity when implemented
        }

        navProfile.setOnClickListener {
            Toast.makeText(this, "Navigating to Profile", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun addToPlanner() {
        try {
            val anuradhapuraItem = PlannerItem(
                id = "anuradhapura_sacred_city",
                locationName = "Anuradhapura Sacred City",
                locationAddress = "Anuradhapura, North Central Province, Sri Lanka",
                imageResource = R.drawable.anuradhapura,
                time = "08:00" // Default time
            )

            // Check if already in planner
            if (PlannerManager.isInPlanner(anuradhapuraItem.id)) {
                Toast.makeText(this, "Anuradhapura is already in your planner!", Toast.LENGTH_SHORT).show()
            } else {
                PlannerManager.addToPlanner(anuradhapuraItem)
                Toast.makeText(this, "✅ Anuradhapura added to your planner!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error adding to planner: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun openGoogleMaps(coordinates: String) {
        try {
            // Try to open Google Maps app first
            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=$coordinates"))
            mapIntent.setPackage("com.google.android.apps.maps")
            
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            } else {
                // Fallback to web browser with Google Maps
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps?q=$coordinates"))
                startActivity(webIntent)
                Toast.makeText(this, "Opening Google Maps in browser...", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error opening maps: ${e.message}", Toast.LENGTH_SHORT).show()
            // Final fallback - try generic maps intent
            try {
                val fallbackIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:$coordinates"))
                startActivity(fallbackIntent)
            } catch (e2: Exception) {
                Toast.makeText(this, "No maps app found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
