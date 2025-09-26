package com.example.newmobileapp.models

data class Restaurant(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val address: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val cuisine: String = "", // Sri Lankan, Indian, Chinese, etc.
    val rating: Double = 0.0,
    val priceRange: String = "", // $, $$, $$$, etc.
    val imageUrl: String = "",
    val phoneNumber: String = "",
    val isRecommended: Boolean = false,
    val nearbyDestinations: List<String> = emptyList(), // destination IDs
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)


