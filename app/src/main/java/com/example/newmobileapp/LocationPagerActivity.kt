package com.example.newmobileapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.newmobileapp.R

class LocationPagerActivity : AppCompatActivity() {

    private lateinit var locationViewPager: ViewPager2
    private lateinit var dotIndicator: ViewGroup
    private lateinit var timeText: TextView
    private lateinit var locationIcon: ImageView
    private lateinit var homeIcon: ImageView
    private lateinit var notificationsIcon: ImageView
    private lateinit var profileIcon: ImageView

    private val locations = listOf(
        LocationData(
            "Anuradhapura Sacred City",
            "Anuradhapura City",
            R.drawable.anuradhapura_sacred_city,
            "Golden Mango Restaurant",
            R.drawable.golden_mango_restaurant,
            "Casserole Restaurant",
            R.drawable.casserole_restaurant,
            "4.1K Reviews",
            "1.1K Reviews"
        ),
        LocationData(
            "Nallur Kovil",
            "Jaffna",
            R.drawable.nallur_kovil_temple,
            "Rio Ice-Cream",
            R.drawable.rio_ice_cream_placeholder,
            "Lavin's Caffe",
            R.drawable.lavins_caffe_placeholder,
            "7.5K Reviews",
            "1.1K Reviews"
        ),
        LocationData(
            "Sigiriya",
            "Matale",
            R.drawable.sigiriya_rock_fortress,
            "Kenoli Restaurant",
            R.drawable.kenoli_restaurant,
            "Flavor Haven",
            R.drawable.flavor_haven,
            "1K Reviews",
            "1.1K Reviews"
        ),
        LocationData(
            "Rawana Waterfall",
            "Wellawaya",
            R.drawable.rawana_waterfall,
            "River Face Restaurant",
            R.drawable.river_face_restaurant,
            "Caffe Dew Ella",
            R.drawable.caffe_dew_ella,
            "1K Reviews",
            "1.1K Reviews"
        ),
        LocationData(
            "Yapahuwa",
            "Kurunegala",
            R.drawable.yapahuwa_fortress,
            "Hotel Yapahuwa Paradise",
            R.drawable.hotel_yapahuwa_paradise,
            "Caffe Dew Ella",
            R.drawable.caffe_dew_ella,
            "1K Reviews",
            "1.1K Reviews"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_pager)

        initializeViews()
        setupViewPager()
        setupClickListeners()
        setupStatusBar()
    }

    private fun initializeViews() {
        locationViewPager = findViewById(R.id.locationViewPager)
        dotIndicator = findViewById(R.id.dotIndicator)
        timeText = findViewById(R.id.timeText)
        locationIcon = findViewById(R.id.locationIcon)
        homeIcon = findViewById(R.id.homeIcon)
        notificationsIcon = findViewById(R.id.notificationsIcon)
        profileIcon = findViewById(R.id.profileIcon)
    }

    private fun setupStatusBar() {
        timeText.text = "10:55"
    }

    private fun setupViewPager() {
        val adapter = LocationAdapter(locations)
        locationViewPager.adapter = adapter

        // Setup dot indicators
        setupDotIndicators()

        // Listen to page changes
        locationViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateDotIndicators(position)
            }
        })
    }

    private fun setupDotIndicators() {
        for (i in locations.indices) {
            val dot = ImageView(this)
            val layoutParams = ViewGroup.LayoutParams(30, 12)
            dot.layoutParams = layoutParams
            dot.setImageResource(if (i == 0) R.drawable.indicator_active else R.drawable.indicator_inactive)
            dot.setOnClickListener {
                locationViewPager.currentItem = i
            }
            dotIndicator.addView(dot)
        }
    }

    private fun updateDotIndicators(selectedPosition: Int) {
        for (i in 0 until dotIndicator.childCount) {
            val dot = dotIndicator.getChildAt(i) as ImageView
            dot.setImageResource(if (i == selectedPosition) R.drawable.indicator_active else R.drawable.indicator_inactive)
        }
    }

    private fun setupClickListeners() {
        locationIcon.setOnClickListener {
            Toast.makeText(this, "You are already on Location", Toast.LENGTH_SHORT).show()
        }

        homeIcon.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        notificationsIcon.setOnClickListener {
            val intent = Intent(this, NotificationsActivity::class.java)
            startActivity(intent)
            finish()
        }

        profileIcon.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    data class LocationData(
        val name: String,
        val address: String,
        val imageRes: Int,
        val restaurant1Name: String,
        val restaurant1ImageRes: Int,
        val restaurant2Name: String,
        val restaurant2ImageRes: Int,
        val restaurant1Reviews: String,
        val restaurant2Reviews: String
    )

    class LocationAdapter(private val locations: List<LocationData>) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

        class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val locationImage: ImageView = itemView.findViewById(R.id.locationImage)
            val locationName: TextView = itemView.findViewById(R.id.locationName)
            val locationAddress: TextView = itemView.findViewById(R.id.locationAddress)
            val restaurant1Image: ImageView = itemView.findViewById(R.id.restaurant1Image)
            val restaurant1Name: TextView = itemView.findViewById(R.id.restaurant1Name)
            val restaurant1Reviews: TextView = itemView.findViewById(R.id.restaurant1Reviews)
            val restaurant2Image: ImageView = itemView.findViewById(R.id.restaurant2Image)
            val restaurant2Name: TextView = itemView.findViewById(R.id.restaurant2Name)
            val restaurant2Reviews: TextView = itemView.findViewById(R.id.restaurant2Reviews)
            val addToPlannerButton: View = itemView.findViewById(R.id.addToPlannerButton)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_location_card, parent, false)
            return LocationViewHolder(view)
        }

        override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
            val location = locations[position]
            
            holder.locationImage.setImageResource(location.imageRes)
            holder.locationName.text = location.name
            holder.locationAddress.text = location.address
            
            holder.restaurant1Image.setImageResource(location.restaurant1ImageRes)
            holder.restaurant1Name.text = location.restaurant1Name
            holder.restaurant1Reviews.text = location.restaurant1Reviews
            
            holder.restaurant2Image.setImageResource(location.restaurant2ImageRes)
            holder.restaurant2Name.text = location.restaurant2Name
            holder.restaurant2Reviews.text = location.restaurant2Reviews

            holder.addToPlannerButton.setOnClickListener {
                Toast.makeText(holder.itemView.context, "Added ${location.name} to planner", Toast.LENGTH_SHORT).show()
            }
        }

        override fun getItemCount(): Int = locations.size
    }
}


