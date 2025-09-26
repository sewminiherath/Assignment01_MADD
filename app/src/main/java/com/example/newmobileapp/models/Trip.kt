package com.example.newmobileapp.models

data class Trip(
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val startDate: Long = 0,
    val endDate: Long = 0,
    val duration: Int = 1, // in days
    val status: String = "planned", // planned, active, completed, cancelled
    val destinations: List<String> = emptyList(), // destination IDs
    val sharedWith: List<String> = emptyList(), // user IDs
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)


