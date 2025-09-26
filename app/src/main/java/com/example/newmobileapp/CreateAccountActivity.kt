package com.example.newmobileapp

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.material.textfield.TextInputEditText
import android.widget.EditText
import com.example.newmobileapp.R
import com.example.newmobileapp.models.User
import com.example.newmobileapp.services.FirebaseService
import com.example.newmobileapp.services.AuthService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: TextInputEditText
    private lateinit var addressEditText: TextInputEditText
    private lateinit var contactNumberEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var genderEditText: TextInputEditText
    private lateinit var nextButton: Button
    private lateinit var createAccountTitle: TextView
    
    // Profile photo variables
    private lateinit var profilePhotoContainer: LinearLayout
    private lateinit var profilePhoto: ImageView
    private var selectedImageUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 1001
    private lateinit var authService: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        try {
            // Initialize authentication service
            authService = AuthService()

            initializeViews()
            setupGenderDropdown()
            setupClickListeners()
            setupStatusBar()
            
            Toast.makeText(this, "CreateAccountActivity loaded successfully!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error in CreateAccountActivity: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun initializeViews() {
        try {
            firstNameEditText = findViewById(R.id.et_first_name)
            lastNameEditText = findViewById(R.id.et_last_name)
            addressEditText = findViewById(R.id.et_address)
            contactNumberEditText = findViewById(R.id.et_contact_number)
            emailEditText = findViewById(R.id.et_email)
            genderEditText = findViewById(R.id.et_gender)
            nextButton = findViewById(R.id.btn_next)
            createAccountTitle = findViewById(R.id.createAccountTitle)
            
            // Initialize profile photo elements
            profilePhotoContainer = findViewById(R.id.profilePhotoContainer)
            profilePhoto = findViewById(R.id.profilePhoto)
            
            Toast.makeText(this, "Views initialized successfully", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error initializing views: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupStatusBar() {
        // Status bar setup removed as timeText is not available
    }

    private fun setupGenderDropdown() {
        val genderOptions = arrayOf("Male", "Female", "Other", "Prefer not to say")

        genderEditText.setOnClickListener {
            val dialog = android.app.AlertDialog.Builder(this)
                .setTitle("Select Gender")
                .setItems(genderOptions) { _, which ->
                    genderEditText.setText(genderOptions[which])
                }
                .create()
            dialog.show()
        }
    }

    private fun setupClickListeners() {
        nextButton.setOnClickListener {
            handleNext()
        }
        
        // Profile photo click listener
        profilePhotoContainer.setOnClickListener {
            openImagePicker()
        }
    }

    private fun handleNext() {
        Toast.makeText(this, "Next button clicked!", Toast.LENGTH_SHORT).show()
        
        if (validateInputs()) {
            // Show loading message
            Toast.makeText(this, "Creating account...", Toast.LENGTH_SHORT).show()
            
            // Create Firebase user account first
            createFirebaseAccount()
        } else {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun createFirebaseAccount() {
        val firstName = firstNameEditText.text.toString().trim()
        val lastName = lastNameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        
        Toast.makeText(this, "Creating account locally...", Toast.LENGTH_SHORT).show()
        
        try {
            // Save user details to SharedPreferences (local storage)
            saveUserDetails()
            Toast.makeText(this, "User details saved successfully!", Toast.LENGTH_SHORT).show()
            
            // Navigate to Success screen
            Toast.makeText(this, "Navigating to Success screen...", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SuccessActivity::class.java)
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            Toast.makeText(this, "Error creating account: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
    
    private fun saveUserDetails() {
        val firstName = firstNameEditText.text.toString().trim()
        val lastName = lastNameEditText.text.toString().trim()
        val address = addressEditText.text.toString().trim()
        val contactNumber = contactNumberEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val gender = genderEditText.text.toString().trim()
        
        // Save to SharedPreferences (for offline access)
        val sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        
        editor.putString("first_name", firstName)
        editor.putString("last_name", lastName)
        editor.putString("address", address)
        editor.putString("contact_number", contactNumber)
        editor.putString("email", email)
        editor.putString("gender", gender)
        
        // Save profile image URI if selected
        selectedImageUri?.let { uri ->
            editor.putString("profile_image_uri", uri.toString())
        }
        
        editor.apply()
        
        // Also save to Firebase Database
        saveToFirebase(firstName, lastName, address, contactNumber, email, gender)
    }
    
    private fun saveToFirebase(firstName: String, lastName: String, address: String, 
                              contactNumber: String, email: String, gender: String) {
        // Skip Firebase for now - save locally only
        Toast.makeText(this, "Account saved locally (Firebase disabled)", Toast.LENGTH_SHORT).show()
    }

    private fun validateInputs(): Boolean {
        val firstName = firstNameEditText.text.toString().trim()
        val lastName = lastNameEditText.text.toString().trim()
        val address = addressEditText.text.toString().trim()
        val contactNumber = contactNumberEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val gender = genderEditText.text.toString().trim()

        Toast.makeText(this, "Validating inputs: First=$firstName, Last=$lastName, Address=$address, Contact=$contactNumber, Email=$email, Gender=$gender", Toast.LENGTH_LONG).show()

        return when {
            firstName.isEmpty() -> {
                firstNameEditText.error = "First name is required"
                Toast.makeText(this, "First name is missing", Toast.LENGTH_SHORT).show()
                false
            }
            lastName.isEmpty() -> {
                lastNameEditText.error = "Last name is required"
                Toast.makeText(this, "Last name is missing", Toast.LENGTH_SHORT).show()
                false
            }
            address.isEmpty() -> {
                addressEditText.error = "Address is required"
                Toast.makeText(this, "Address is missing", Toast.LENGTH_SHORT).show()
                false
            }
            contactNumber.isEmpty() -> {
                contactNumberEditText.error = "Contact number is required"
                Toast.makeText(this, "Contact number is missing", Toast.LENGTH_SHORT).show()
                false
            }
            email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                emailEditText.error = "Valid email is required"
                Toast.makeText(this, "Email is invalid or missing", Toast.LENGTH_SHORT).show()
                false
            }
            gender.isEmpty() -> {
                genderEditText.error = "Gender is required"
                Toast.makeText(this, "Gender is missing", Toast.LENGTH_SHORT).show()
                false
            }
            else -> {
                Toast.makeText(this, "All inputs are valid!", Toast.LENGTH_SHORT).show()
                true
            }
        }
    }
    
    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.data
            selectedImageUri?.let { uri ->
                try {
                    val inputStream = contentResolver.openInputStream(uri)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    profilePhoto.setImageBitmap(bitmap)
                    inputStream?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
