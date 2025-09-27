package com.example.newmobileapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
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
    private lateinit var searchInput: EditText
    private lateinit var plannerButton: View
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
    
    // Destination cards
    private lateinit var destination1Card: View
    private lateinit var destination2Card: View
    private lateinit var destination3Card: View
    private lateinit var destination4Card: View
    
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
            searchInput = findViewById(R.id.search_input)
            plannerButton = findViewById(R.id.planner_button_container)
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
            
            // Initialize destination cards
            destination1Card = findViewById(R.id.destination1_card)
            destination2Card = findViewById(R.id.destination2_card)
            destination3Card = findViewById(R.id.destination3_card)
            destination4Card = findViewById(R.id.destination4_card)
            
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

        // Search functionality - search in Chrome when user presses enter
        searchInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                true
            } else {
                false
            }
        }

        plannerButton.setOnClickListener {
            Toast.makeText(this, "Planner button clicked!", Toast.LENGTH_SHORT).show()
            try {
                val intent = Intent(this, Planner2Activity::class.java)
                Toast.makeText(this, "Intent created for Planner2Activity", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                Toast.makeText(this, "Navigating to Planner...", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Error navigating to Planner: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

        exploreButton.setOnClickListener {
            Toast.makeText(this, "Exploring destinations...", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AnuradhapuraDetailActivity::class.java)
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
        
        // Destination card click listeners
        destination1Card.setOnClickListener {
            openWebsite("https://www.srilanka.travel/anuradhapura")
        }
        
        destination2Card.setOnClickListener {
            openWebsite("https://www.srilanka.travel/anuradhapura")
        }
        
        destination3Card.setOnClickListener {
            openWebsite("https://www.srilanka.travel/jaffna")
        }
        
        destination4Card.setOnClickListener {
            openWebsite("https://www.srilanka.travel/rawana-falls")
        }
    }

    private fun setupBottomNavigation() {
        navLocationIcon.setOnClickListener {
            Toast.makeText(this, "Opening Google Maps...", Toast.LENGTH_SHORT).show()
            openGoogleMaps("7.8731,80.7718") // Sri Lanka center coordinates
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
    
    private fun openWebsite(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
                Toast.makeText(this, "Opening website...", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No browser app found", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error opening website: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun performSearch() {
        try {
            val searchQuery = searchInput.text.toString().trim()
            
            if (searchQuery.isEmpty()) {
                Toast.makeText(this, "Please enter a search term", Toast.LENGTH_SHORT).show()
                return
            }
            
            // Create Google search URL
            val searchUrl = "https://www.google.com/search?q=${Uri.encode(searchQuery)}"
            
            // Try to open in Chrome first
            val chromeIntent = Intent(Intent.ACTION_VIEW, Uri.parse(searchUrl)).apply {
                setPackage("com.android.chrome")
            }
            
            if (chromeIntent.resolveActivity(packageManager) != null) {
                startActivity(chromeIntent)
                Toast.makeText(this, "Searching for '$searchQuery' in Chrome", Toast.LENGTH_SHORT).show()
            } else {
                // Fallback to default browser
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(searchUrl))
                if (browserIntent.resolveActivity(packageManager) != null) {
                    startActivity(browserIntent)
                    Toast.makeText(this, "Searching for '$searchQuery' in browser", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "No browser app found", Toast.LENGTH_SHORT).show()
                }
            }
            
            // Clear the search input
            searchInput.setText("")
            
        } catch (e: Exception) {
            Toast.makeText(this, "Error performing search: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
