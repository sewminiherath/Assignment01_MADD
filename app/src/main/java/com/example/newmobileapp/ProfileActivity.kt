package com.example.newmobileapp

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newmobileapp.models.User
import com.example.newmobileapp.services.FirebaseService
import com.example.newmobileapp.services.AuthService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException


class ProfileActivity : AppCompatActivity() {

    private lateinit var profileImage: ImageView
    private lateinit var cameraIcon: ImageView
    private lateinit var nameValue: TextView
    private lateinit var mobileValue: TextView
    private lateinit var emailValue: TextView
    private lateinit var genderValue: TextView
    private lateinit var deleteAccount: TextView
    private lateinit var logoutButton: View
    private lateinit var authService: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        Toast.makeText(this, "ProfileActivity loaded successfully!", Toast.LENGTH_SHORT).show()

        // Initialize authentication service
        authService = AuthService()

        initializeViews()
        setupClickListeners()
        loadUserData()
    }

    private fun initializeViews() {
        try {
            profileImage = findViewById(R.id.profile_image)
            cameraIcon = findViewById(R.id.camera_icon)
            nameValue = findViewById(R.id.name_value)
            mobileValue = findViewById(R.id.mobile_value)
            emailValue = findViewById(R.id.email_value)
            genderValue = findViewById(R.id.gender_value)
            deleteAccount = findViewById(R.id.delete_account)
            logoutButton = findViewById(R.id.logout_button)
            
            Toast.makeText(this, "Profile views initialized successfully!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error initializing profile views: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupClickListeners() {
        // Profile picture click to change photo
        profileImage.setOnClickListener {
            // TODO: Implement photo selection functionality
            Toast.makeText(this, "Photo selection coming soon", Toast.LENGTH_SHORT).show()
        }

        // Camera icon click
        cameraIcon.setOnClickListener {
            // TODO: Implement photo selection functionality
            Toast.makeText(this, "Change profile photo coming soon", Toast.LENGTH_SHORT).show()
        }

        // Delete account click
        deleteAccount.setOnClickListener {
            // TODO: Implement delete account functionality
            Toast.makeText(this, "Delete account functionality coming soon", Toast.LENGTH_SHORT).show()
        }

        // Logout button click
        logoutButton.setOnClickListener {
            handleLogout()
        }
    }

    private fun loadUserData() {
        val sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE)
        val userId = sharedPreferences.getString("user_id", "")
        
        // Try to load from Firebase first if user ID exists
        if (!userId.isNullOrEmpty()) {
            loadFromFirebase(userId)
        } else {
            // Fallback to local data
            loadFromLocalStorage()
        }
    }
    
    private fun loadFromFirebase(userId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val firebaseService = FirebaseService()
                val result = firebaseService.getUser(userId)
                
                if (result.isSuccess) {
                    val user = result.getOrNull()
                        if (user != null) {
                            // Display Firebase data
                            nameValue.text = "${user.firstName} ${user.lastName}"
                            mobileValue.text = user.mobile.ifEmpty { "No contact number" }
                            emailValue.text = user.email.ifEmpty { "No email" }
                            genderValue.text = user.gender.ifEmpty { "Not specified" }
                        
                        // Load profile image if available
                        if (user.profileImageUrl.isNotEmpty()) {
                            try {
                                val uri = Uri.parse(user.profileImageUrl)
                                val inputStream = contentResolver.openInputStream(uri)
                                val bitmap = BitmapFactory.decodeStream(inputStream)
                                profileImage.setImageBitmap(bitmap)
                                inputStream?.close()
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                        
                        Toast.makeText(this@ProfileActivity, "Profile loaded from cloud", Toast.LENGTH_SHORT).show()
                    } else {
                        loadFromLocalStorage()
                    }
                } else {
                    loadFromLocalStorage()
                }
            } catch (e: Exception) {
                loadFromLocalStorage()
            }
        }
    }
    
    private fun loadFromLocalStorage() {
        val sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE)
        
        val firstName = sharedPreferences.getString("first_name", "")
        val lastName = sharedPreferences.getString("last_name", "")
        val contactNumber = sharedPreferences.getString("contact_number", "")
        val gender = sharedPreferences.getString("gender", "")
        val email = sharedPreferences.getString("email", "")
        val address = sharedPreferences.getString("address", "")
        val profileImageUri = sharedPreferences.getString("profile_image_uri", "")
        
        // Update profile information
        if (firstName.isNullOrEmpty() && lastName.isNullOrEmpty()) {
            // No user data found, show placeholder
            nameValue.text = "Henri Allen"
            mobileValue.text = "+94765559561"
            emailValue.text = "alex9995@gmail.com"
            genderValue.text = "Female"
        } else {
            // Display saved user data
            nameValue.text = "$firstName $lastName"
            mobileValue.text = contactNumber ?: "No contact number"
            emailValue.text = email ?: "No email"
            genderValue.text = gender ?: "Not specified"
            
            // Load profile image if available
            if (!profileImageUri.isNullOrEmpty()) {
                try {
                    val uri = Uri.parse(profileImageUri)
                    val inputStream = contentResolver.openInputStream(uri)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    profileImage.setImageBitmap(bitmap)
                    inputStream?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error loading profile image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun handleLogout() {
        // Sign out from Firebase Auth
        authService.signOut()
        
        // Clear user session data
        val sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        // Navigate back to MainActivity (Sign In screen)
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()

        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
    }

    // Modern back handling - no need to override onBackPressed
    // The system will handle back navigation automatically
}
