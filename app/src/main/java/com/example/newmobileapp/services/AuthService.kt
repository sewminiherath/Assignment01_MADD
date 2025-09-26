package com.example.newmobileapp.services

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await

class AuthService {
    private val auth = FirebaseAuth.getInstance()
    
    // Check if user is currently signed in
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
    
    // Sign in with email and password
    suspend fun signIn(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("Sign in failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Create new user account
    suspend fun createUser(email: String, password: String, firstName: String, lastName: String): Result<FirebaseUser> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null) {
                // Update user profile with name
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName("$firstName $lastName")
                    .build()
                user.updateProfile(profileUpdates).await()
                Result.success(user)
            } else {
                Result.failure(Exception("Account creation failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Sign out user
    fun signOut() {
        auth.signOut()
    }
    
    // Check if user exists (for validation)
    suspend fun checkUserExists(email: String): Boolean {
        return try {
            // Try to sign in with a dummy password to check if user exists
            // This is a workaround since Firebase doesn't have a direct "user exists" method
            auth.fetchSignInMethodsForEmail(email).await()
            true
        } catch (e: Exception) {
            false
        }
    }
}


