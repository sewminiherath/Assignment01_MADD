package com.example.newmobileapp.utils

import com.example.newmobileapp.models.Destination
import com.example.newmobileapp.models.Restaurant
import com.example.newmobileapp.services.FirebaseService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseInitializer {
    private val firebaseService = FirebaseService()
    
    fun initializeSampleData() {
        CoroutineScope(Dispatchers.IO).launch {
            initializeDestinations()
            initializeRestaurants()
        }
    }
    
    private suspend fun initializeDestinations() {
        val destinations = listOf(
            Destination(
                name = "Anuradhapura Sacred City",
                description = "Ancient capital of Sri Lanka with sacred Buddhist sites",
                address = "Anuradhapura, Sri Lanka",
                latitude = 8.3114,
                longitude = 80.4037,
                category = "historical",
                rating = 4.8,
                isRecommended = true
            ),
            Destination(
                name = "Nallur Kovil",
                description = "Famous Hindu temple in Jaffna",
                address = "Jaffna, Sri Lanka",
                latitude = 9.6615,
                longitude = 80.0255,
                category = "temple",
                rating = 4.6,
                isRecommended = true
            ),
            Destination(
                name = "Sigiriya Rock Fortress",
                description = "Ancient rock fortress and UNESCO World Heritage site",
                address = "Sigiriya, Sri Lanka",
                latitude = 7.9569,
                longitude = 80.7597,
                category = "historical",
                rating = 4.9,
                isRecommended = true
            ),
            Destination(
                name = "Rawana Waterfall",
                description = "Beautiful waterfall in Wellawaya",
                address = "Wellawaya, Sri Lanka",
                latitude = 6.8667,
                longitude = 81.1167,
                category = "nature",
                rating = 4.5,
                isRecommended = true
            ),
            Destination(
                name = "Yapahuwa Fortress",
                description = "Ancient fortress and archaeological site",
                address = "Yapahuwa, Sri Lanka",
                latitude = 7.8333,
                longitude = 80.1667,
                category = "historical",
                rating = 4.4,
                isRecommended = true
            )
        )
        
        destinations.forEach { destination ->
            firebaseService.createDestination(destination)
        }
    }
    
    private suspend fun initializeRestaurants() {
        val restaurants = listOf(
            Restaurant(
                name = "Golden Mango Restaurant",
                description = "Traditional Sri Lankan cuisine",
                address = "Anuradhapura, Sri Lanka",
                latitude = 8.3114,
                longitude = 80.4037,
                cuisine = "Sri Lankan",
                rating = 4.3,
                priceRange = "$$",
                isRecommended = true
            ),
            Restaurant(
                name = "Casserole Restaurant",
                description = "Modern Sri Lankan and international cuisine",
                address = "Anuradhapura, Sri Lanka",
                latitude = 8.3114,
                longitude = 80.4037,
                cuisine = "International",
                rating = 4.5,
                priceRange = "$$$",
                isRecommended = true
            ),
            Restaurant(
                name = "Rio Ice Cream",
                description = "Best ice cream in Jaffna",
                address = "Jaffna, Sri Lanka",
                latitude = 9.6615,
                longitude = 80.0255,
                cuisine = "Dessert",
                rating = 4.2,
                priceRange = "$",
                isRecommended = true
            ),
            Restaurant(
                name = "Lavins Caffe",
                description = "Cozy coffee shop with local treats",
                address = "Jaffna, Sri Lanka",
                latitude = 9.6615,
                longitude = 80.0255,
                cuisine = "Cafe",
                rating = 4.4,
                priceRange = "$$",
                isRecommended = true
            ),
            Restaurant(
                name = "Kenoli Restaurant",
                description = "Traditional Sri Lankan food near Sigiriya",
                address = "Sigiriya, Sri Lanka",
                latitude = 7.9569,
                longitude = 80.7597,
                cuisine = "Sri Lankan",
                rating = 4.6,
                priceRange = "$$",
                isRecommended = true
            ),
            Restaurant(
                name = "Flavor Haven",
                description = "Multi-cuisine restaurant",
                address = "Sigiriya, Sri Lanka",
                latitude = 7.9569,
                longitude = 80.7597,
                cuisine = "Multi-cuisine",
                rating = 4.4,
                priceRange = "$$$",
                isRecommended = true
            )
        )
        
        restaurants.forEach { restaurant ->
            firebaseService.createRestaurant(restaurant)
        }
    }
}


