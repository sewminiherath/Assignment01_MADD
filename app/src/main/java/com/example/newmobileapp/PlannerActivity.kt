package com.example.newmobileapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PlannerActivity : AppCompatActivity() {

    private lateinit var backArrow: ImageView
    private lateinit var sharePlanButton: View
    private lateinit var facebookContainer: LinearLayout
    private lateinit var whatsappContainer: LinearLayout
    private lateinit var instagramContainer: LinearLayout
    private lateinit var linkedinContainer: LinearLayout

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
    }

    private fun setupClickListeners() {
        backArrow.setOnClickListener {
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
    }

    private fun openFacebook() {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Facebook app not found. Opening in browser...", Toast.LENGTH_SHORT).show()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"))
            startActivity(intent)
        }
    }

    private fun openWhatsApp() {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/"))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "WhatsApp app not found. Opening in browser...", Toast.LENGTH_SHORT).show()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://web.whatsapp.com/"))
            startActivity(intent)
        }
    }

    private fun openInstagram() {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/"))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Instagram app not found. Opening in browser...", Toast.LENGTH_SHORT).show()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/"))
            startActivity(intent)
        }
    }

    private fun openLinkedIn() {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/"))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "LinkedIn app not found. Opening in browser...", Toast.LENGTH_SHORT).show()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/"))
            startActivity(intent)
        }
    }
}
