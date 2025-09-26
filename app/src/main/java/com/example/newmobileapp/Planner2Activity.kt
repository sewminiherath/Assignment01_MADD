package com.example.newmobileapp

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newmobileapp.utils.PlannerManager
import java.util.*

class Planner2Activity : AppCompatActivity() {

    private lateinit var backArrow: ImageView
    private lateinit var sharePlanButton: View
    private lateinit var timelineContainer: LinearLayout

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
        
        // Load and display planner items
        loadPlannerItems()
    }

    private fun setupClickListeners() {
        backArrow.setOnClickListener {
            // Navigate back to Planner1
            finish()
        }

        sharePlanButton.setOnClickListener {
            // Navigate to Planner1 for sharing
            val intent = Intent(this, PlannerActivity::class.java)
            startActivity(intent)
        }
    }
    
    private fun loadPlannerItems() {
        val plannerItems = PlannerManager.getPlannerItems()
        
        // Clear existing items
        timelineContainer.removeAllViews()
        
        // Add each planner item to the timeline
        plannerItems.forEachIndexed { index, item ->
            addPlannerItemToTimeline(item, index)
        }
    }
    
    private fun addPlannerItemToTimeline(item: com.example.newmobileapp.models.PlannerItem, index: Int) {
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
}
