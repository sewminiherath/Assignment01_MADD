package com.example.newmobileapp

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class EmergencyActivity : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var emergencyContact1Input: TextInputEditText
    private lateinit var emergencyPhone1Input: TextInputEditText
    private lateinit var emergencyContact2Input: TextInputEditText
    private lateinit var emergencyPhone2Input: TextInputEditText
    private lateinit var saveEmergencyContactsButton: Button
    private lateinit var policeCard: View
    private lateinit var fireCard: View
    private lateinit var ambulanceCard: View
    private lateinit var customerCareCard: View
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency)
        
        sharedPreferences = getSharedPreferences("emergency_prefs", MODE_PRIVATE)

        initializeViews()
        setupClickListeners()
        loadEmergencyContacts()
    }

    private fun initializeViews() {
        backButton = findViewById(R.id.back_button)
        emergencyContact1Input = findViewById(R.id.emergency_contact1_input)
        emergencyPhone1Input = findViewById(R.id.emergency_phone1_input)
        emergencyContact2Input = findViewById(R.id.emergency_contact2_input)
        emergencyPhone2Input = findViewById(R.id.emergency_phone2_input)
        saveEmergencyContactsButton = findViewById(R.id.save_emergency_contacts_button)
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

        saveEmergencyContactsButton.setOnClickListener {
            saveEmergencyContacts()
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
    
    private fun loadEmergencyContacts() {
        try {
            // Load saved emergency contact 1
            val contact1Name = sharedPreferences.getString("emergency_contact1_name", "")
            val contact1Phone = sharedPreferences.getString("emergency_contact1_phone", "")
            
            emergencyContact1Input.setText(contact1Name)
            emergencyPhone1Input.setText(contact1Phone)
            
            // Load saved emergency contact 2
            val contact2Name = sharedPreferences.getString("emergency_contact2_name", "")
            val contact2Phone = sharedPreferences.getString("emergency_contact2_phone", "")
            
            emergencyContact2Input.setText(contact2Name)
            emergencyPhone2Input.setText(contact2Phone)
        } catch (e: Exception) {
            Toast.makeText(this, "Error loading emergency contacts: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun saveEmergencyContacts() {
        try {
            val emergencyEditor = sharedPreferences.edit()
            val profilePrefs = getSharedPreferences("user_profile", MODE_PRIVATE)
            val profileEditor = profilePrefs.edit()
            
            val contact1Name = emergencyContact1Input.text.toString().trim()
            val contact1Phone = emergencyPhone1Input.text.toString().trim()
            val contact2Name = emergencyContact2Input.text.toString().trim()
            val contact2Phone = emergencyPhone2Input.text.toString().trim()
            
            // Validate input
            if (contact1Name.isEmpty() || contact1Phone.isEmpty()) {
                Toast.makeText(this, "Please fill in Emergency Contact 1 details", Toast.LENGTH_SHORT).show()
                return
            }
            
            if (contact2Name.isEmpty() || contact2Phone.isEmpty()) {
                Toast.makeText(this, "Please fill in Emergency Contact 2 details", Toast.LENGTH_SHORT).show()
                return
            }
            
            // Save to emergency preferences
            emergencyEditor.putString("emergency_contact1_name", contact1Name)
            emergencyEditor.putString("emergency_contact1_phone", contact1Phone)
            emergencyEditor.putString("emergency_contact2_name", contact2Name)
            emergencyEditor.putString("emergency_contact2_phone", contact2Phone)
            
            // Save to profile preferences (for profile layout integration)
            profileEditor.putString("emergency_contact1", contact1Name)
            profileEditor.putString("emergency_phone1", contact1Phone)
            profileEditor.putString("emergency_contact2", contact2Name)
            profileEditor.putString("emergency_phone2", contact2Phone)
            
            emergencyEditor.apply()
            profileEditor.apply()
            
            Toast.makeText(this, "Emergency contacts saved successfully!", Toast.LENGTH_SHORT).show()
            
            // Navigate back to profile after saving
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
            
        } catch (e: Exception) {
            Toast.makeText(this, "Error saving emergency contacts: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onPause() {
        super.onPause()
        // Auto-save emergency contacts when user leaves the activity
        saveEmergencyContacts()
    }
}
