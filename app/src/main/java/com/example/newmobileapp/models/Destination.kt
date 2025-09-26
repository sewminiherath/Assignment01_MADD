package com.example.newmobileapp.models

data class Destination(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val address: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val category: String = "", // temple, beach, historical, nature, etc.
    val rating: Double = 0.0,
    val imageUrl: String = "",
    val isRecommended: Boolean = false,
    val nearbyRestaurants: List<String> = emptyList(), // restaurant IDs
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)


