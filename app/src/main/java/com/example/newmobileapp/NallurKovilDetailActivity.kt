package com.example.newmobileapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newmobileapp.models.PlannerItem
import com.example.newmobileapp.utils.PlannerManager

class NallurKovilDetailActivity : AppCompatActivity() {

    private lateinit var locationImage: ImageView
    private lateinit var locationName: TextView
    private lateinit var locationAddress: TextView
    private lateinit var addToPlannerButton: LinearLayout
    private lateinit var locationIcon: ImageView
    private lateinit var homeIcon: ImageView
    private lateinit var notificationsIcon: ImageView
    private lateinit var profileIcon: ImageView
    
    // Location navigation buttons
    private lateinit var btnAnuradhapura: Button
    private lateinit var btnNallurKovil: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nallur_kovil_detail)

        initializeViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        locationImage = findViewById(R.id.locationImage)
        locationName = findViewById(R.id.locationName)
        locationAddress = findViewById(R.id.locationAddress)
        addToPlannerButton = findViewById(R.id.addToPlannerButton)
        locationIcon = findViewById(R.id.locationIcon)
        homeIcon = findViewById(R.id.homeIcon)
        notificationsIcon = findViewById(R.id.notificationsIcon)
        profileIcon = findViewById(R.id.profileIcon)
        
        // Initialize location navigation buttons
        btnAnuradhapura = findViewById(R.id.btnAnuradhapura)
        btnNallurKovil = findViewById(R.id.btnNallurKovil)
    }

    private fun setupClickListeners() {
        // Bottom navigation click listeners
        locationIcon.setOnClickListener {
            Toast.makeText(this, "Opening Google Maps for Nallur Kovil...", Toast.LENGTH_SHORT).show()
            openGoogleMaps("9.6833,79.9056") // Nallur Kovil coordinates
        }

        homeIcon.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        notificationsIcon.setOnClickListener {
            val intent = Intent(this, NotificationsActivity::class.java)
            startActivity(intent)
            finish()
        }

        profileIcon.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Add to planner button
        addToPlannerButton.setOnClickListener {
            addLocationToPlanner()
        }
        
        // Location navigation buttons
        btnAnuradhapura.setOnClickListener {
            val intent = Intent(this, AnuradhapuraDetailActivity::class.java)
            startActivity(intent)
            finish()
        }
        
        btnNallurKovil.setOnClickListener {
            // Already on Nallur Kovil page, do nothing
        }
    }
    
    private fun addLocationToPlanner() {
        val plannerItem = PlannerItem(
            id = "nallur_kovil",
            locationName = "Nallur Kovil",
            locationAddress = "Jaffna",
            imageResource = R.drawable.nallurl
        )
        
        PlannerManager.addToPlanner(plannerItem)
        Toast.makeText(this, "Added to Planner!", Toast.LENGTH_SHORT).show()
        
        // Navigate to Planner3
        val intent = Intent(this, Planner3Activity::class.java)
        startActivity(intent)
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
