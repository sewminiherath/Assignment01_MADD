package com.example.newmobileapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newmobileapp.ProfileActivity
import com.example.newmobileapp.R

class LocationDetailActivity : AppCompatActivity() {

    private lateinit var locationImage: ImageView
    private lateinit var locationName: TextView
    private lateinit var locationAddress: TextView
    private lateinit var addToPlannerButton: TextView
    private lateinit var timeText: TextView
    private lateinit var locationIcon: ImageView
    private lateinit var homeIcon: ImageView
    private lateinit var notificationsIcon: ImageView
    private lateinit var profileIcon: ImageView
    private lateinit var btnPrevious: Button
    private lateinit var btnNext: Button
    
    // Location data
    private val locations = listOf(
        LocationData("Anuradhapura Sacred City", "Anuradhapura City", R.drawable.anuradhapura_sacred_city_detailed),
        LocationData("Nallur Kovil", "Jaffna", R.drawable.nallur_kovil_detailed),
        LocationData("Sigiriya", "Matale", R.drawable.sigiriya_rock_detailed),
        LocationData("Rawana Waterfall", "Wellawaya", R.drawable.rawana_waterfall_detailed),
        LocationData("Yapahuwa", "Kurunegala", R.drawable.yapahuwa_fortress_detailed)
    )
    private var currentLocationIndex = 0
    
    data class LocationData(
        val name: String,
        val address: String,
        val imageRes: Int
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_detail)

        initializeViews()
        setupClickListeners()
        setupStatusBar()
        loadLocationData()
    }

    private fun initializeViews() {
        locationImage = findViewById(R.id.locationImage)
        locationName = findViewById(R.id.locationName)
        locationAddress = findViewById(R.id.locationAddress)
        addToPlannerButton = findViewById(R.id.addToPlannerButton)
        timeText = findViewById(R.id.timeText)
        locationIcon = findViewById(R.id.locationIcon)
        homeIcon = findViewById(R.id.homeIcon)
        notificationsIcon = findViewById(R.id.notificationsIcon)
        profileIcon = findViewById(R.id.profileIcon)
        btnPrevious = findViewById(R.id.btnPrevious)
        btnNext = findViewById(R.id.btnNext)
    }

    private fun setupStatusBar() {
        // Set current time (you can make this dynamic if needed)
        timeText.text = "10:55"
    }

    private fun setupClickListeners() {
        addToPlannerButton.setOnClickListener {
            Toast.makeText(this, "Added to planner", Toast.LENGTH_SHORT).show()
        }

        locationIcon.setOnClickListener {
            // Already on location screen
            Toast.makeText(this, "You are already on Location", Toast.LENGTH_SHORT).show()
        }

        homeIcon.setOnClickListener {
            // Navigate to Home screen
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

        // Location navigation buttons
        btnPrevious.setOnClickListener {
            navigateToPrevious()
        }

        btnNext.setOnClickListener {
            navigateToNext()
        }
    }

    private fun loadLocationData() {
        // Load the first location by default
        displayCurrentLocation()
    }
    
    private fun displayCurrentLocation() {
        val currentLocation = locations[currentLocationIndex]
        locationName.text = currentLocation.name
        locationAddress.text = currentLocation.address
        locationImage.setImageResource(currentLocation.imageRes)
        
        // Update button states
        btnPrevious.isEnabled = currentLocationIndex > 0
        btnNext.isEnabled = currentLocationIndex < locations.size - 1
        
        // Update restaurant data based on location
        updateRestaurantData(currentLocation.name)
    }
    
    private fun navigateToPrevious() {
        if (currentLocationIndex > 0) {
            currentLocationIndex--
            displayCurrentLocation()
        }
    }
    
    private fun navigateToNext() {
        if (currentLocationIndex < locations.size - 1) {
            currentLocationIndex++
            displayCurrentLocation()
        }
    }


    private fun updateRestaurantData(locationName: String) {
        // This is a simplified version - in a real app, you'd load different restaurant data
        // For now, we'll just show a toast with the location name
        Toast.makeText(this, "Now viewing: $locationName", Toast.LENGTH_SHORT).show()
    }
}
