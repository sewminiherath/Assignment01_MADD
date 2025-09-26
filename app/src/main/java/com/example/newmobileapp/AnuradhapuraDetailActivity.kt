package com.example.newmobileapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newmobileapp.models.PlannerItem
import com.example.newmobileapp.utils.PlannerManager

class AnuradhapuraDetailActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_anuradhapura_detail)

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
            // Already on location page, do nothing
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
            // Already on Anuradhapura page, do nothing
        }
        
        btnNallurKovil.setOnClickListener {
            val intent = Intent(this, NallurKovilDetailActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    
    private fun addLocationToPlanner() {
        val plannerItem = PlannerItem(
            id = "anuradhapura",
            locationName = "Anuradhapura Sacred City",
            locationAddress = "Anuradhapura City",
            imageResource = R.drawable.anuradhapural
        )
        
        PlannerManager.addToPlanner(plannerItem)
        Toast.makeText(this, "Added to Planner!", Toast.LENGTH_SHORT).show()
        
        // Navigate to Planner2
        val intent = Intent(this, Planner2Activity::class.java)
        startActivity(intent)
    }
}
