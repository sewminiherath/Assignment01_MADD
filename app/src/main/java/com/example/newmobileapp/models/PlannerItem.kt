package com.example.newmobileapp.models

import java.io.Serializable

data class PlannerItem(
    val id: String,
    val locationName: String,
    val locationAddress: String,
    val imageResource: Int,
    val addedDate: Long = System.currentTimeMillis(),
    var time: String = "09:00" // Default time
) : Serializable
