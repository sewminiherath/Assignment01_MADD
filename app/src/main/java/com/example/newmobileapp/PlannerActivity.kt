package com.example.newmobileapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newmobileapp.utils.PlannerManager
import java.text.SimpleDateFormat
import java.util.*

class PlannerActivity : AppCompatActivity() {

    private lateinit var backArrow: ImageView
    private lateinit var sharePlanButton: View
    private lateinit var facebookContainer: LinearLayout
    private lateinit var whatsappContainer: LinearLayout
    private lateinit var instagramContainer: LinearLayout
    private lateinit var linkedinContainer: LinearLayout
    private lateinit var calendarIcon: ImageView
    private lateinit var clockIcon: ImageView
    private lateinit var dateText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner)

        initializeViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        backArrow = findViewById(R.id.back_arrow)
        sharePlanButton = findViewById(R.id.share_plan_button)
        facebookContainer = findViewById(R.id.facebook_container)
        whatsappContainer = findViewById(R.id.whatsapp_container)
        instagramContainer = findViewById(R.id.instagram_container)
        linkedinContainer = findViewById(R.id.linkedin_container)
        calendarIcon = findViewById(R.id.calendar_icon)
        clockIcon = findViewById(R.id.clock_icon)
        dateText = findViewById(R.id.date_text)
    }

    private fun setupClickListeners() {
        backArrow.setOnClickListener {
            Toast.makeText(this, "Back arrow clicked - going to Home", Toast.LENGTH_SHORT).show()
            // Navigate back to Home
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        sharePlanButton.setOnClickListener {
            // Navigate to Planner2 for enhanced sharing
            val intent = Intent(this, Planner2Activity::class.java)
            startActivity(intent)
        }

        // Social Media Click Listeners
        facebookContainer.setOnClickListener {
            openFacebook()
        }

        whatsappContainer.setOnClickListener {
            openWhatsApp()
        }

        instagramContainer.setOnClickListener {
            openInstagram()
        }

        linkedinContainer.setOnClickListener {
            openLinkedIn()
        }
        
        // Calendar icon click listener
        calendarIcon.setOnClickListener {
            showDatePickerDialog()
        }
        
        // Clock icon click listener
        clockIcon.setOnClickListener {
            showTimePickerDialog()
        }
    }

    private fun openFacebook() {
        try {
            val shareText = createTripShareText()
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
                setPackage("com.facebook.katana") // Facebook app package
            }
            startActivity(intent)
        } catch (e: Exception) {
            // Fallback to general sharing
            shareTripPlan()
        }
    }

    private fun openWhatsApp() {
        try {
            val shareText = createTripShareText()
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
                setPackage("com.whatsapp") // WhatsApp app package
            }
            startActivity(intent)
        } catch (e: Exception) {
            // Fallback to general sharing
            shareTripPlan()
        }
    }

    private fun openInstagram() {
        try {
            val shareText = createTripShareText()
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
                setPackage("com.instagram.android") // Instagram app package
            }
            startActivity(intent)
        } catch (e: Exception) {
            // Fallback to general sharing
            shareTripPlan()
        }
    }

    private fun openLinkedIn() {
        try {
            val shareText = createTripShareText()
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
                setPackage("com.linkedin.android") // LinkedIn app package
            }
            startActivity(intent)
        } catch (e: Exception) {
            // Fallback to general sharing
            shareTripPlan()
        }
    }
    
    private fun createTripShareText(): String {
        val plannerItems = PlannerManager.getPlannerItems()
        val shareText = StringBuilder()
        
        shareText.append("🗺️ My Amazing Trip Plan!\n\n")
        
        if (plannerItems.isNotEmpty()) {
            shareText.append("📍 Destinations:\n")
            plannerItems.forEachIndexed { index, item ->
                shareText.append("${index + 1}. ${item.locationName} at ${item.time}\n")
            }
        } else {
            shareText.append("📍 Destinations:\n")
            shareText.append("1. Jaya Sri Maha Bodhiya at 06:30\n")
            shareText.append("2. Thuparamaya at 19:30\n")
            shareText.append("3. Abhayagiriya at 20:30\n")
        }
        
        shareText.append("\n✨ Created with Smart Travel App")
        shareText.append("\n#Travel #SriLanka #TripPlan #SmartTravel")
        
        return shareText.toString()
    }
    
    private fun shareTripPlan() {
        try {
            val shareText = createTripShareText()
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
                putExtra(Intent.EXTRA_SUBJECT, "My Trip Plan")
            }
            startActivity(Intent.createChooser(intent, "Share Trip Plan"))
        } catch (e: Exception) {
            Toast.makeText(this, "Error sharing trip plan: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                
                val dateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                
                // Update the date text
                dateText.text = formattedDate
                
                Toast.makeText(this, "Date set to $formattedDate", Toast.LENGTH_SHORT).show()
            },
            currentYear,
            currentMonth,
            currentDay
        )
        
        datePickerDialog.setTitle("Select Trip Date")
        datePickerDialog.show()
    }
    
    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)
        
        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                
                Toast.makeText(this, "Time set to $selectedTime", Toast.LENGTH_SHORT).show()
            },
            currentHour,
            currentMinute,
            true // 24-hour format
        )
        
        timePickerDialog.setTitle("Select Time")
        timePickerDialog.show()
    }
}
