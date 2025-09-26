package com.example.newmobileapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EmergencyActivity : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var contact1Text: TextView
    private lateinit var contact2Text: TextView
    private lateinit var policeCard: View
    private lateinit var fireCard: View
    private lateinit var ambulanceCard: View
    private lateinit var customerCareCard: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency)
        
        Toast.makeText(this, "EmergencyActivity onCreate called!", Toast.LENGTH_LONG).show()

        initializeViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        backButton = findViewById(R.id.back_button)
        contact1Text = findViewById(R.id.contact1_text)
        contact2Text = findViewById(R.id.contact2_text)
        policeCard = findViewById(R.id.police_card)
        fireCard = findViewById(R.id.fire_card)
        ambulanceCard = findViewById(R.id.ambulance_card)
        customerCareCard = findViewById(R.id.customer_care_card)
    }

    private fun setupClickListeners() {
        backButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        contact1Text.setOnClickListener {
            handleAddEmergencyContact(1)
        }

        contact2Text.setOnClickListener {
            handleAddEmergencyContact(2)
        }

        policeCard.setOnClickListener {
            handleEmergencyService("Police", "119")
        }

        fireCard.setOnClickListener {
            handleEmergencyService("Fire", "110")
        }

        ambulanceCard.setOnClickListener {
            handleEmergencyService("Ambulance", "1990")
        }

        customerCareCard.setOnClickListener {
            handleEmergencyService("Customer Care", "1331")
        }
    }

    private fun handleAddEmergencyContact(contactNumber: Int) {
        Toast.makeText(this, "Add emergency contact $contactNumber", Toast.LENGTH_SHORT).show()
        // Implement add emergency contact functionality
        // You can open a contact picker or add contact form
    }

    private fun handleEmergencyService(serviceName: String, phoneNumber: String) {
        Toast.makeText(this, "Calling $serviceName", Toast.LENGTH_SHORT).show()

        // Create intent to make phone call
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "No phone app available", Toast.LENGTH_SHORT).show()
        }
    }
}
