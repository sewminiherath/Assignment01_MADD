package com.example.newmobileapp.services

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.example.newmobileapp.models.User
import com.example.newmobileapp.models.Trip
import com.example.newmobileapp.models.Destination
import com.example.newmobileapp.models.Restaurant
import kotlinx.coroutines.tasks.await

class FirebaseService {
    private val db = FirebaseFirestore.getInstance()
    
    // User operations
    suspend fun createUser(user: User): Result<String> {
        return try {
            val docRef = db.collection("users").add(user).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getUser(userId: String): Result<User?> {
        return try {
            val document = db.collection("users").document(userId).get().await()
            val user = document.toObject(User::class.java)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updateUser(userId: String, user: User): Result<Unit> {
        return try {
            db.collection("users").document(userId).set(user).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Trip operations
    suspend fun createTrip(trip: Trip): Result<String> {
        return try {
            val docRef = db.collection("trips").add(trip).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getUserTrips(userId: String): Result<List<Trip>> {
        return try {
            val query = db.collection("trips")
                .whereEqualTo("userId", userId)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()
            
            val trips = query.documents.mapNotNull { it.toObject(Trip::class.java) }
            Result.success(trips)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updateTrip(tripId: String, trip: Trip): Result<Unit> {
        return try {
            db.collection("trips").document(tripId).set(trip).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun deleteTrip(tripId: String): Result<Unit> {
        return try {
            db.collection("trips").document(tripId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Destination operations
    suspend fun getRecommendedDestinations(): Result<List<Destination>> {
        return try {
            val query = db.collection("destinations")
                .whereEqualTo("isRecommended", true)
                .orderBy("rating", Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .await()
            
            val destinations = query.documents.mapNotNull { it.toObject(Destination::class.java) }
            Result.success(destinations)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getDestinationById(destinationId: String): Result<Destination?> {
        return try {
            val document = db.collection("destinations").document(destinationId).get().await()
            val destination = document.toObject(Destination::class.java)
            Result.success(destination)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun searchDestinations(query: String): Result<List<Destination>> {
        return try {
            val firestoreQuery = db.collection("destinations")
                .whereGreaterThanOrEqualTo("name", query)
                .whereLessThan("name", query + "\uf8ff")
                .limit(20)
                .get()
                .await()
            
            val destinations = firestoreQuery.documents.mapNotNull { it.toObject(Destination::class.java) }
            Result.success(destinations)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Restaurant operations
    suspend fun getRestaurantsByDestination(destinationId: String): Result<List<Restaurant>> {
        return try {
            val query = db.collection("restaurants")
                .whereArrayContains("nearbyDestinations", destinationId)
                .orderBy("rating", Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .await()
            
            val restaurants = query.documents.mapNotNull { it.toObject(Restaurant::class.java) }
            Result.success(restaurants)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getRecommendedRestaurants(): Result<List<Restaurant>> {
        return try {
            val query = db.collection("restaurants")
                .whereEqualTo("isRecommended", true)
                .orderBy("rating", Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .await()
            
            val restaurants = query.documents.mapNotNull { it.toObject(Restaurant::class.java) }
            Result.success(restaurants)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Additional methods for data initialization
    suspend fun createDestination(destination: Destination): Result<String> {
        return try {
            val docRef = db.collection("destinations").add(destination).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun createRestaurant(restaurant: Restaurant): Result<String> {
        return try {
            val docRef = db.collection("restaurants").add(restaurant).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
