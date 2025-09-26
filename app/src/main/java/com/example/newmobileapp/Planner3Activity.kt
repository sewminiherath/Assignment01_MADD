package com.example.newmobileapp

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class Planner3Activity : AppCompatActivity() {

    private lateinit var backArrow: ImageView
    private lateinit var daySelectorMain: LinearLayout
    private lateinit var dropdownIcon: ImageView
    private lateinit var dropdownMenu: LinearLayout
    private lateinit var day02Item: LinearLayout
    private lateinit var day03Item: LinearLayout
    private lateinit var day04Item: LinearLayout
    private lateinit var selectedDayText: TextView
    private lateinit var checkOutButton: View

    private var isDropdownOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner3)

        initializeViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        backArrow = findViewById(R.id.back_arrow)
        daySelectorMain = findViewById(R.id.day_selector_main)
        dropdownIcon = findViewById(R.id.dropdown_icon)
        dropdownMenu = findViewById(R.id.dropdown_menu)
        day02Item = findViewById(R.id.day_02_item)
        day03Item = findViewById(R.id.day_03_item)
        day04Item = findViewById(R.id.day_04_item)
        checkOutButton = findViewById(R.id.check_out_button)
        
        // Find the selected day text view (first TextView in the LinearLayout)
        selectedDayText = daySelectorMain.getChildAt(0) as TextView
    }

    private fun setupClickListeners() {
        backArrow.setOnClickListener {
            finish()
        }

        // Day selector main click
        daySelectorMain.setOnClickListener {
            toggleDropdown()
        }

        // Day selection items
        day02Item.setOnClickListener {
            selectDay("Day 02")
        }

        day03Item.setOnClickListener {
            selectDay("Day 03")
        }

        day04Item.setOnClickListener {
            selectDay("Day 04")
        }

        checkOutButton.setOnClickListener {
            // Navigate to Planner4 for final checkout
            val intent = Intent(this, Planner4Activity::class.java)
            startActivity(intent)
        }
        
        // Add click listeners to clock icons in the layout
        setupClockClickListeners()
    }

    private fun toggleDropdown() {
        if (isDropdownOpen) {
            dropdownMenu.visibility = View.GONE
            dropdownIcon.rotation = 0f
            isDropdownOpen = false
        } else {
            dropdownMenu.visibility = View.VISIBLE
            dropdownIcon.rotation = 180f
            isDropdownOpen = true
        }
    }

    private fun selectDay(day: String) {
        // Update the selected day text
        selectedDayText.text = day
        
        // Close dropdown
        dropdownMenu.visibility = View.GONE
        dropdownIcon.rotation = 0f
        isDropdownOpen = false
        
        // Here you could update the timeline content based on the selected day
        // For now, we'll just show a toast
        android.widget.Toast.makeText(this, "Selected $day", android.widget.Toast.LENGTH_SHORT).show()
    }
    
    private fun setupClockClickListeners() {
        // Add click listeners to specific clock icon IDs
        val clockIconIds = listOf(
            R.id.clock_icon_01, R.id.clock_icon_02, R.id.clock_icon_03,
            R.id.clock_icon_04, R.id.clock_icon_05, R.id.clock_icon_06
        )
        
        clockIconIds.forEachIndexed { index, iconId ->
            try {
                val clockIcon = findViewById<ImageView>(iconId)
                clockIcon?.setOnClickListener {
                    showTimePicker("Destination ${String.format("%02d", index + 1)}")
                }
            } catch (e: Exception) {
                // Handle case where icon might not exist
                e.printStackTrace()
            }
        }
    }
    
    private fun findClockIconsRecursively(view: View, clockIcons: MutableList<ImageView>) {
        if (view is ImageView && view.drawable != null) {
            // Check if this is a clock icon by checking the drawable resource
            try {
                val resourceName = resources.getResourceEntryName(view.id)
                if (resourceName.contains("clock") || view.tag?.toString()?.contains("clock") == true) {
                    clockIcons.add(view)
                }
            } catch (e: Exception) {
                // If we can't get the resource name, check if it's clickable and has clock drawable
                if (view.isClickable && view.drawable != null) {
                    clockIcons.add(view)
                }
            }
        }
        
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                findClockIconsRecursively(view.getChildAt(i), clockIcons)
            }
        }
    }
    
    private fun showTimePicker(destinationName: String) {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)
        
        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                Toast.makeText(this, "Time set for $destinationName: $selectedTime", Toast.LENGTH_SHORT).show()
            },
            currentHour,
            currentMinute,
            true // 24-hour format
        )
        
        timePickerDialog.setTitle("Set time for $destinationName")
        timePickerDialog.show()
    }
}
