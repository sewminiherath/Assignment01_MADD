package com.example.newmobileapp

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.newmobileapp.models.User
import com.example.newmobileapp.services.FirebaseService
import com.example.newmobileapp.services.AuthService
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class ProfileActivity : AppCompatActivity() {

    private lateinit var backArrow: ImageView
    private lateinit var profileImage: ImageView
    private lateinit var cameraIcon: ImageView
    
    // Input fields
    private lateinit var firstNameInput: TextInputEditText
    private lateinit var lastNameInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var mobileInput: TextInputEditText
    private lateinit var addressInput: TextInputEditText
    private lateinit var emergencyContact1Input: TextInputEditText
    private lateinit var emergencyContact2Input: TextInputEditText
    
    // Radio buttons
    private lateinit var genderRadioGroup: RadioGroup
    private lateinit var genderMale: RadioButton
    private lateinit var genderFemale: RadioButton
    
    // Buttons
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button
    
    private lateinit var authService: AuthService
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        Toast.makeText(this, "ProfileActivity loaded successfully!", Toast.LENGTH_SHORT).show()

        // Initialize authentication service
        authService = AuthService()
        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)

        initializeViews()
        setupClickListeners()
        loadUserData()
        
        // Initialize image picker launcher
        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    try {
                        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                        profileImage.setImageBitmap(bitmap)
                        Toast.makeText(this, "Profile image updated!", Toast.LENGTH_SHORT).show()
                    } catch (e: IOException) {
                        Toast.makeText(this, "Error loading image: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun initializeViews() {
        try {
            // Navigation
            backArrow = findViewById(R.id.back_arrow)
            
            // Profile image
            profileImage = findViewById(R.id.profile_image)
            cameraIcon = findViewById(R.id.camera_icon)
            
            // Input fields
            firstNameInput = findViewById(R.id.first_name_input)
            lastNameInput = findViewById(R.id.last_name_input)
            emailInput = findViewById(R.id.email_input)
            mobileInput = findViewById(R.id.mobile_input)
            addressInput = findViewById(R.id.address_input)
            emergencyContact1Input = findViewById(R.id.emergency_contact1_input)
            emergencyContact2Input = findViewById(R.id.emergency_contact2_input)
            
            // Radio buttons
            genderRadioGroup = findViewById(R.id.gender_radio_group)
            genderMale = findViewById(R.id.gender_male)
            genderFemale = findViewById(R.id.gender_female)
            
            // Buttons
            saveButton = findViewById(R.id.save_button)
            cancelButton = findViewById(R.id.cancel_button)
            
            Toast.makeText(this, "Views initialized successfully!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error initializing views: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupClickListeners() {
        // Back arrow
        backArrow.setOnClickListener {
            Toast.makeText(this, "Back arrow clicked", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Profile image click listener
        profileImage.setOnClickListener {
            Toast.makeText(this, "Profile image clicked - opening image picker", Toast.LENGTH_SHORT).show()
            openImagePicker()
        }
        
        // Camera icon for profile image
        cameraIcon.setOnClickListener {
            Toast.makeText(this, "Camera icon clicked - opening image picker", Toast.LENGTH_SHORT).show()
            openImagePicker()
        }

        // Save button
        saveButton.setOnClickListener {
            Toast.makeText(this, "Save button clicked", Toast.LENGTH_SHORT).show()
            saveUserData()
        }

        // Cancel button
        cancelButton.setOnClickListener {
            Toast.makeText(this, "Cancel button clicked", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun loadUserData() {
        try {
            // Load from SharedPreferences first
            val firstName = sharedPreferences.getString("first_name", "") ?: ""
            val lastName = sharedPreferences.getString("last_name", "") ?: ""
            val email = sharedPreferences.getString("email", "") ?: ""
            val mobile = sharedPreferences.getString("mobile", "") ?: ""
            val address = sharedPreferences.getString("address", "") ?: ""
            val gender = sharedPreferences.getString("gender", "") ?: ""
            
            // Load emergency contacts from both profile and emergency preferences
            val profilePrefs = getSharedPreferences("user_profile", MODE_PRIVATE)
            val emergencyContact1 = profilePrefs.getString("emergency_contact1", "") 
                ?: sharedPreferences.getString("emergency_contact1", "") ?: ""
            val emergencyContact2 = profilePrefs.getString("emergency_contact2", "") 
                ?: sharedPreferences.getString("emergency_contact2", "") ?: ""

            // Populate input fields
            firstNameInput.setText(firstName)
            lastNameInput.setText(lastName)
            emailInput.setText(email)
            mobileInput.setText(mobile)
            addressInput.setText(address)
            emergencyContact1Input.setText(emergencyContact1)
            emergencyContact2Input.setText(emergencyContact2)

            // Set gender radio button
            when (gender.lowercase()) {
                "male" -> genderMale.isChecked = true
                "female" -> genderFemale.isChecked = true
            }

            Toast.makeText(this, "User data loaded successfully!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error loading user data: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun saveUserData() {
        try {
            // Get input values
            val firstName = firstNameInput.text.toString().trim()
            val lastName = lastNameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val mobile = mobileInput.text.toString().trim()
            val address = addressInput.text.toString().trim()
            val emergencyContact1 = emergencyContact1Input.text.toString().trim()
            val emergencyContact2 = emergencyContact2Input.text.toString().trim()
            
            // Get selected gender
            val selectedGender = when (genderRadioGroup.checkedRadioButtonId) {
                R.id.gender_male -> "Male"
                R.id.gender_female -> "Female"
                else -> ""
            }

            // Validate required fields
            if (firstName.isEmpty()) {
                firstNameInput.error = "First name is required"
                return
            }
            if (lastName.isEmpty()) {
                lastNameInput.error = "Last name is required"
                return
            }
            if (email.isEmpty()) {
                emailInput.error = "Email is required"
                return
            }
            if (mobile.isEmpty()) {
                mobileInput.error = "Mobile number is required"
                return
            }

            // Save to SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("first_name", firstName)
            editor.putString("last_name", lastName)
            editor.putString("email", email)
            editor.putString("mobile", mobile)
            editor.putString("address", address)
            editor.putString("gender", selectedGender)
            editor.putString("emergency_contact1", emergencyContact1)
            editor.putString("emergency_contact2", emergencyContact2)
            editor.apply()
            
            // Also save to profile preferences for emergency activity integration
            val profilePrefs = getSharedPreferences("user_profile", MODE_PRIVATE)
            val profileEditor = profilePrefs.edit()
            profileEditor.putString("emergency_contact1", emergencyContact1)
            profileEditor.putString("emergency_contact2", emergencyContact2)
            profileEditor.apply()

            Toast.makeText(this, "Profile saved successfully!", Toast.LENGTH_SHORT).show()
            
            // Navigate back to home
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
            
        } catch (e: Exception) {
            Toast.makeText(this, "Error saving profile: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        // Clear previous errors
        firstNameInput.error = null
        lastNameInput.error = null
        emailInput.error = null
        mobileInput.error = null

        // Validate first name
        if (firstNameInput.text.toString().trim().isEmpty()) {
            firstNameInput.error = "First name is required"
            isValid = false
        }

        // Validate last name
        if (lastNameInput.text.toString().trim().isEmpty()) {
            lastNameInput.error = "Last name is required"
            isValid = false
        }

        // Validate email
        val email = emailInput.text.toString().trim()
        if (email.isEmpty()) {
            emailInput.error = "Email is required"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.error = "Please enter a valid email"
            isValid = false
        }

        // Validate mobile
        val mobile = mobileInput.text.toString().trim()
        if (mobile.isEmpty()) {
            mobileInput.error = "Mobile number is required"
            isValid = false
        } else if (mobile.length < 10) {
            mobileInput.error = "Please enter a valid mobile number"
            isValid = false
        }

        return isValid
    }
    
    private fun openImagePicker() {
        try {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            imagePickerLauncher.launch(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Error opening image picker: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}