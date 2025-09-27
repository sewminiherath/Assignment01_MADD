package com.example.newmobileapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
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

class Planner2Activity : AppCompatActivity() {

    private lateinit var backArrow: ImageView
    private lateinit var sharePlanButton: View
    private lateinit var timelineContainer: LinearLayout
    private lateinit var calendarIcon: ImageView
    private lateinit var clockIcon: ImageView
    private lateinit var dateText: TextView
    private lateinit var clockIcon1: ImageView
    private lateinit var clockIcon2: ImageView
    private lateinit var clockIcon3: ImageView
    private lateinit var clockIcon4: ImageView
    private lateinit var clockIcon5: ImageView
    private lateinit var timeInput1: EditText
    private lateinit var timeInput2: EditText
    private lateinit var timeInput3: EditText
    private lateinit var timeInput4: EditText
    private lateinit var timeInput5: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner2)

        initializeViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        backArrow = findViewById(R.id.back_arrow)
        sharePlanButton = findViewById(R.id.share_plan_button)
        timelineContainer = findViewById(R.id.timeline_container)
        calendarIcon = findViewById(R.id.calendar_icon)
        clockIcon = findViewById(R.id.clock_icon)
        dateText = findViewById(R.id.date_text)
        clockIcon1 = findViewById(R.id.clock_icon_1)
        clockIcon2 = findViewById(R.id.clock_icon_2)
        clockIcon3 = findViewById(R.id.clock_icon_3)
        clockIcon4 = findViewById(R.id.clock_icon_4)
        clockIcon5 = findViewById(R.id.clock_icon_5)
        timeInput1 = findViewById(R.id.time_input_1)
        timeInput2 = findViewById(R.id.time_input_2)
        timeInput3 = findViewById(R.id.time_input_3)
        timeInput4 = findViewById(R.id.time_input_4)
        timeInput5 = findViewById(R.id.time_input_5)
        
        // Debug timeline container
        Toast.makeText(this, "Timeline container found: ${timelineContainer != null}", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "Timeline container child count: ${timelineContainer.childCount}", Toast.LENGTH_SHORT).show()
        
        // Load and display planner items
        loadPlannerItems()
    }

    private fun setupClickListeners() {
        backArrow.setOnClickListener {
            Toast.makeText(this, "Back arrow clicked - going back", Toast.LENGTH_SHORT).show()
            // Navigate back to previous activity
            finish()
        }

        sharePlanButton.setOnClickListener {
            // Navigate to Planner1 for sharing
            val intent = Intent(this, PlannerActivity::class.java)
            startActivity(intent)
        }
        
        // Calendar icon click listener
        calendarIcon.setOnClickListener {
            showDatePickerDialog()
        }
        
        // Clock icon click listener
        clockIcon.setOnClickListener {
            showTimePickerDialog()
        }
        
        // Individual destination clock icon click listeners
        clockIcon1.setOnClickListener {
            showTimePickerForDestination("Ruwanweli Maha Seya", "05:30am", 1)
        }
        
        clockIcon2.setOnClickListener {
            showTimePickerForDestination("Jaya Sri Maha Bodhiya", "06:30am", 2)
        }
        
        clockIcon3.setOnClickListener {
            showTimePickerForDestination("Thuparamaya", "07:30pm", 3)
        }
        
        clockIcon4.setOnClickListener {
            showTimePickerForDestination("Abhayagiriya", "08:30pm", 4)
        }
        
        clockIcon5.setOnClickListener {
            showTimePickerForDestination("Isurumuniya", "10:30pm", 5)
        }
    }
    
    private fun loadPlannerItems() {
        val plannerItems = PlannerManager.getPlannerItems()
        
        Toast.makeText(this, "Loading planner items: ${plannerItems.size}", Toast.LENGTH_SHORT).show()
        
        // If no items in planner, add some default items for demo
        if (plannerItems.isEmpty()) {
            Toast.makeText(this, "Adding default planner items", Toast.LENGTH_SHORT).show()
            addDefaultPlannerItems()
        }
        
        // Don't clear existing items - let the static content show
        // The static timeline items in the XML should be visible
        Toast.makeText(this, "Timeline should be visible with static content", Toast.LENGTH_SHORT).show()
    }
    
    private fun addDefaultPlannerItems() {
        // Add some default planner items for demo
        val defaultItems = listOf(
            PlannerItem(
                id = "1",
                locationName = "Jaya Sri Maha Bodhiya",
                locationAddress = "Anuradhapura, Sri Lanka",
                imageResource = R.drawable.ar,
                time = "06:30"
            ),
            PlannerItem(
                id = "2", 
                locationName = "Thuparamaya",
                locationAddress = "Anuradhapura, Sri Lanka",
                imageResource = R.drawable.thuparamaya,
                time = "19:30"
            ),
            PlannerItem(
                id = "3",
                locationName = "Abhayagiriya", 
                locationAddress = "Anuradhapura, Sri Lanka",
                imageResource = R.drawable.abhayagiriya,
                time = "20:30"
            )
        )
        
        defaultItems.forEach { item ->
            PlannerManager.addToPlanner(item)
        }
    }
    
    private fun addPlannerItemToTimeline(item: com.example.newmobileapp.models.PlannerItem, index: Int) {
        Toast.makeText(this, "Creating timeline item for: ${item.locationName}", Toast.LENGTH_SHORT).show()
        
        // Create a timeline item layout
        val itemLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(0, 16, 0, 16)
        }
        
        // Add timeline dot
        val dot = View(this).apply {
            layoutParams = LinearLayout.LayoutParams(20, 20).apply {
                setMargins(0, 0, 16, 0)
            }
            setBackgroundResource(R.drawable.timeline_dot_white)
        }
        
        // Add location image
        val imageView = ImageView(this).apply {
            layoutParams = LinearLayout.LayoutParams(60, 60).apply {
                setMargins(0, 0, 16, 0)
            }
            setImageResource(item.imageResource)
            setBackgroundResource(R.drawable.image_border_white)
        }
        
        // Add location details (takes remaining space)
        val detailsLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }
        
        val nameText = TextView(this).apply {
            text = item.locationName
            setTextColor(resources.getColor(android.R.color.white))
            textSize = 14f
        }
        
        val addressText = TextView(this).apply {
            text = item.locationAddress
            setTextColor(resources.getColor(android.R.color.white))
            textSize = 12f
        }
        
        // Add time display
        val timeText = TextView(this).apply {
            text = "Time: ${item.time}"
            setTextColor(resources.getColor(android.R.color.white))
            textSize = 10f
        }
        
        // Add clock icon with click listener (positioned on the right)
        val clockIcon = ImageView(this).apply {
            layoutParams = LinearLayout.LayoutParams(32, 32).apply {
                setMargins(16, 0, 0, 0)
            }
            setImageResource(R.drawable.ic_alarm_clock)
            isClickable = true
            isFocusable = true
            setOnClickListener {
                showTimePicker(item, index)
            }
        }
        
        detailsLayout.addView(nameText)
        detailsLayout.addView(addressText)
        detailsLayout.addView(timeText)
        
        itemLayout.addView(dot)
        itemLayout.addView(imageView)
        itemLayout.addView(detailsLayout)
        itemLayout.addView(clockIcon) // Clock icon added to the right side
        
        timelineContainer.addView(itemLayout)
        Toast.makeText(this, "Added item to timeline container", Toast.LENGTH_SHORT).show()
    }
    
    private fun showTimePicker(item: com.example.newmobileapp.models.PlannerItem, index: Int) {
        val calendar = Calendar.getInstance()
        val currentTime = item.time.split(":")
        val currentHour = currentTime[0].toInt()
        val currentMinute = currentTime[1].toInt()
        
        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                
                // Update the item in the planner
                val updatedItem = item.copy(time = selectedTime)
                PlannerManager.removeFromPlanner(item.id)
                PlannerManager.addToPlanner(updatedItem)
                
                // Refresh the timeline
                loadPlannerItems()
                
                Toast.makeText(this, "Time set to $selectedTime", Toast.LENGTH_SHORT).show()
            },
            currentHour,
            currentMinute,
            true // 24-hour format
        )
        
        timePickerDialog.setTitle("Set time for ${item.locationName}")
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
    
    private fun showTimePickerForDestination(destinationName: String, currentTime: String, destinationIndex: Int) {
        try {
            // Parse current time to get hour and minute
            val timeParts = currentTime.replace("am", "").replace("pm", "").split(":")
            val isPM = currentTime.contains("pm")
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
        }
        
        Toast.makeText(this, "Time updated to $newTime for destination $destinationIndex", Toast.LENGTH_SHORT).show()
    }
}
