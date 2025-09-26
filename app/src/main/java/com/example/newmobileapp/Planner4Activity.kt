package com.example.newmobileapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Planner4Activity : AppCompatActivity() {

    private lateinit var backArrow: ImageView
    private lateinit var checkOutButton: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner4)

        initializeViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        backArrow = findViewById(R.id.back_arrow)
        checkOutButton = findViewById(R.id.check_out_button)
    }

    private fun setupClickListeners() {
        backArrow.setOnClickListener {
            // Navigate back to Planner3
            finish()
        }

        checkOutButton.setOnClickListener {
            // Navigate to Home after successful checkout
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}
