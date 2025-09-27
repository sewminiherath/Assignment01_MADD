package com.example.newmobileapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newmobileapp.utils.PlannerManager
import com.example.newmobileapp.models.PlannerItem
import java.text.SimpleDateFormat
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
    
    // Calendar and Date
    private lateinit var calendarIcon: ImageView
    private lateinit var dateInputField: TextView
    private lateinit var dateText: TextView
    
    // Destination TextViews
    private lateinit var destination1Text: TextView
    private lateinit var destination2Text: TextView
    private lateinit var destination3Text: TextView
    private lateinit var destination4Text: TextView
    private lateinit var destination5Text: TextView
    private lateinit var destination6Text: TextView
    
    // Time Input Fields
    private lateinit var timeInput1: EditText
    private lateinit var timeInput2: EditText
    private lateinit var timeInput3: EditText
    private lateinit var timeInput4: EditText
    private lateinit var timeInput5: EditText
    private lateinit var timeInput6: EditText

    private var isDropdownOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner3)

        initializeViews()
        loadPlannerItems()
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
        
        // Initialize calendar and date
        calendarIcon = findViewById(R.id.calendar_icon)
        dateInputField = findViewById(R.id.date_input_field)
        dateText = findViewById(R.id.date_text)
        
        // Find the selected day text view (first TextView in the LinearLayout)
        selectedDayText = daySelectorMain.getChildAt(0) as TextView
        
        // Initialize destination TextViews
        destination1Text = findViewById(R.id.destination_01_text)
        destination2Text = findViewById(R.id.destination_02_text)
        destination3Text = findViewById(R.id.destination_03_text)
        destination4Text = findViewById(R.id.destination_04_text)
        destination5Text = findViewById(R.id.destination_05_text)
        destination6Text = findViewById(R.id.destination_06_text)
        
        // Initialize time input fields
        timeInput1 = findViewById(R.id.time_input_01)
        timeInput2 = findViewById(R.id.time_input_02)
        timeInput3 = findViewById(R.id.time_input_03)
        timeInput4 = findViewById(R.id.time_input_04)
        timeInput5 = findViewById(R.id.time_input_05)
        timeInput6 = findViewById(R.id.time_input_06)
    }
    
    private fun loadPlannerItems() {
        try {
            val plannerItems = PlannerManager.getPlannerItems()
            
            Toast.makeText(this, "Loading ${plannerItems.size} planner items", Toast.LENGTH_SHORT).show()
            
            // Get all destination TextViews
            val destinationTexts = listOf(
                destination1Text, destination2Text, destination3Text,
                destination4Text, destination5Text, destination6Text
            )
            
            // Clear all destinations first
            destinationTexts.forEach { textView ->
                textView.text = ""
                textView.visibility = View.GONE
            }
            
            // Populate with actual planner items
            plannerItems.forEachIndexed { index, item ->
                if (index < destinationTexts.size) {
                    destinationTexts[index].text = item.locationName
                    destinationTexts[index].visibility = View.VISIBLE
                    Toast.makeText(this, "Added: ${item.locationName}", Toast.LENGTH_SHORT).show()
                }
            }
            
            // If no items, show default destinations
            if (plannerItems.isEmpty()) {
                destinationTexts.forEachIndexed { index, textView ->
                    textView.text = "Destination ${String.format("%02d", index + 1)}"
                    textView.visibility = View.VISIBLE
                }
            }
            
        } catch (e: Exception) {
            Toast.makeText(this, "Error loading planner items: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupClickListeners() {
        backArrow.setOnClickListener {
            finish()
        }

        // Calendar icon click
        calendarIcon.setOnClickListener {
            showDatePickerDialog()
        }
        
        // Date input field click (near calendar) - show day selector
        dateInputField.setOnClickListener {
            showDaySelectorDialog()
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
            // Navigate to PlannerActivity for sharing planner
            val intent = Intent(this, PlannerActivity::class.java)
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
        
        // Also update the date input field near calendar
        dateInputField.text = day
        
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
                    val destinationTexts = listOf(destination1Text, destination2Text, destination3Text, destination4Text, destination5Text, destination6Text)
                    val destinationName = destinationTexts[index].text.toString().takeIf { it.isNotEmpty() } ?: "Destination ${String.format("%02d", index + 1)}"
                    showTimePickerForDestination(destinationName, index + 1)
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

                // Update only the day selector date text field (calendar icon opens date picker)
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
    
    private fun showDaySelectorDialog() {
        val days = arrayOf("Day 01", "Day 02", "Day 03", "Day 04", "Day 05", "Day 06", "Day 07")
        
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Select Day")
        builder.setItems(days) { _, which ->
            val selectedDay = days[which]
            dateInputField.text = selectedDay
            
            // Also update the main day selector if it exists
            try {
                dateText.text = selectedDay
            } catch (e: Exception) {
                // Handle case where dateText might not be initialized
            }
            
            Toast.makeText(this, "Selected $selectedDay", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Cancel", null)
        builder.show()
    }
    
    private fun showTimePickerForDestination(destinationName: String, destinationIndex: Int) {
        try {
            // Get current time from the corresponding input field
            val currentTimeText = when (destinationIndex) {
                1 -> timeInput1.text.toString()
                2 -> timeInput2.text.toString()
                3 -> timeInput3.text.toString()
                4 -> timeInput4.text.toString()
                5 -> timeInput5.text.toString()
                6 -> timeInput6.text.toString()
                else -> "09:00am"
            }
            
            // Parse current time to get hour and minute
            val timeParts = currentTimeText.replace("am", "").replace("pm", "").split(":")
            val isPM = currentTimeText.contains("pm")
            var hour = timeParts[0].toInt()
            val minute = timeParts[1].toInt()

            // Convert to 24-hour format for TimePickerDialog
            if (isPM && hour != 12) {
                hour += 12
            } else if (!isPM && hour == 12) {
                hour = 0
            }

            val timePickerDialog = TimePickerDialog(
                this,
                { _, selectedHour, selectedMinute ->
                    // Convert back to 12-hour format
                    val displayHour = if (selectedHour == 0) 12 else if (selectedHour > 12) selectedHour - 12 else selectedHour
                    val amPm = if (selectedHour < 12) "am" else "pm"
                    val newTime = String.format("%d:%02d%s", displayHour, selectedMinute, amPm)

                    // Update the time display in the timeline
                    updateDestinationTime(destinationIndex, newTime)

                    Toast.makeText(this, "Time updated for $destinationName to $newTime", Toast.LENGTH_SHORT).show()
                },
                hour,
                minute,
                false // 12-hour format
            )

            timePickerDialog.setTitle("Set time for $destinationName")
            timePickerDialog.show()

        } catch (e: Exception) {
            Toast.makeText(this, "Error parsing time: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun updateDestinationTime(destinationIndex: Int, newTime: String) {
        // Update the corresponding time input field
        when (destinationIndex) {
            1 -> timeInput1.setText(newTime)
            2 -> timeInput2.setText(newTime)
            3 -> timeInput3.setText(newTime)
            4 -> timeInput4.setText(newTime)
            5 -> timeInput5.setText(newTime)
            6 -> timeInput6.setText(newTime)
        }

        Toast.makeText(this, "Time updated to $newTime for destination $destinationIndex", Toast.LENGTH_SHORT).show()
    }
}
