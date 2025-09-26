package com.example.newmobileapp.utils

import com.example.newmobileapp.models.PlannerItem
import java.util.*

object PlannerManager {
    private val plannerItems = mutableListOf<PlannerItem>()
    
    fun addToPlanner(item: PlannerItem) {
        // Check if item already exists
        if (!plannerItems.any { it.id == item.id }) {
            plannerItems.add(item)
        }
    }
    
    fun removeFromPlanner(itemId: String) {
        plannerItems.removeAll { it.id == itemId }
    }
    
    fun getPlannerItems(): List<PlannerItem> {
        return plannerItems.toList()
    }
    
    fun clearPlanner() {
        plannerItems.clear()
    }
    
    fun isInPlanner(itemId: String): Boolean {
        return plannerItems.any { it.id == itemId }
    }
}


