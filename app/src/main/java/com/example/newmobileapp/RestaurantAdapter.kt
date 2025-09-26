package com.example.newmobileapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newmobileapp.R

class RestaurantAdapter(
    private val restaurants: List<Restaurant>,
    private val onItemClick: (Restaurant) -> Unit
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.restaurantImage)
        val nameText: TextView = itemView.findViewById(R.id.restaurantName)
        val ratingText: TextView = itemView.findViewById(R.id.restaurantRating)
        val reviewsText: TextView = itemView.findViewById(R.id.restaurantReviews)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_restaurant, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.imageView.setImageResource(restaurant.imageRes)
        holder.nameText.text = restaurant.name
        holder.ratingText.text = getStarRating(restaurant.rating)
        holder.reviewsText.text = restaurant.reviews

        holder.itemView.setOnClickListener {
            onItemClick(restaurant)
        }
    }

    override fun getItemCount(): Int = restaurants.size

    private fun getStarRating(rating: Float): String {
        val fullStars = rating.toInt()
        val hasHalfStar = rating % 1 >= 0.5f
        val emptyStars = 5 - fullStars - if (hasHalfStar) 1 else 0

        return "★".repeat(fullStars) +
                if (hasHalfStar) "☆" else "" +
                        "☆".repeat(emptyStars)
    }
}
