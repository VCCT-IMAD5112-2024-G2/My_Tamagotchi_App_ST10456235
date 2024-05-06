package com.example.mytalkingdog

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class actionPage : AppCompatActivity() {

    // Define initial status levels
    var healthLevel = 20
    var hungerLevel = 0
    var happinessLevel = 0
    var cleanlinessLevel = 0

    // Define TextViews for status levels
    private lateinit var hungerStatus: TextView
    private lateinit var playStatus: TextView
    private lateinit var cleanStatus: TextView
    private lateinit var healthStatus: TextView

    // Define a handler and runnable for updating status levels periodically
    private val handler = Handler(Looper.getMainLooper())
    private val statusUpdateRunnable = object : Runnable {
        override fun run() {
            // Decrease status levels by 5
            if (hungerLevel > 0) {
                hungerLevel -= 5
            }
            if (happinessLevel > 0) {
                happinessLevel -= 5
            }
            if (cleanlinessLevel > 0) {
                cleanlinessLevel -= 5
            }

            // If hunger level is depleted, decrease health level by 5
            if (hungerLevel == 0 && healthLevel > 0) {
                healthLevel -= 5
            }

            // Cap health level at 120
            healthLevel = healthLevel.coerceIn(0, 120)

            // Update TextViews with new status levels
            runOnUiThread {
                hungerStatus.text = "Hunger: $hungerLevel"
                playStatus.text = "Happiness: $happinessLevel"
                cleanStatus.text = "Cleanliness: $cleanlinessLevel"
                healthStatus.text = "Health: $healthLevel"

                // Check if health level is 0 to change the image to dead dog image
                if (healthLevel == 0) {
                    findViewById<ImageView>(R.id.pet_image).setImageResource(R.drawable.dead_dog)
                } else {
                    // Restore original pet image if health level is greater than 0
                    findViewById<ImageView>(R.id.pet_image).setImageResource(R.drawable.img_3)
                }
            }

            // Schedule the next update after 10 seconds
            handler.postDelayed(this, 10000)
        }
    }

    // Define counters for button clicks
    private var feedClicks = 0
    private var cleanClicks = 0
    private var playClicks = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_action_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find views
        val petImageView = findViewById<ImageView>(R.id.pet_image)
        val feedButton = findViewById<Button>(R.id.feed_button)
        val cleanButton = findViewById<Button>(R.id.clean_button)
        val playButton = findViewById<Button>(R.id.play_button)
        hungerStatus = findViewById(R.id.hungerLevel)
        playStatus = findViewById(R.id.playLevel)
        cleanStatus = findViewById(R.id.cleanLevel)
        healthStatus = findViewById(R.id.healthLevel)

        // Initialize TextViews with initial status levels
        cleanStatus.text = "Cleanliness: $cleanlinessLevel"
        hungerStatus.text = "Hunger: $hungerLevel"
        playStatus.text = "Happiness: $happinessLevel"
        healthStatus.text = "Health: $healthLevel"

        // Set initial image of the pet
        petImageView.setImageResource(R.drawable.img_3)

        // Start updating status levels periodically
        handler.postDelayed(statusUpdateRunnable, 10000) // Start after 10 seconds

        // Set onClickListeners for buttons
        feedButton.setOnClickListener {
            // Change the pet's image to match the feeding action icon
            petImageView.setImageResource(R.drawable.img_2)

            // Increase hunger level, capped at 100
            hungerLevel = (hungerLevel + 10).coerceAtMost(100)

            // Update the hunger status TextView
            hungerStatus.text = "Hunger: $hungerLevel"

            // Increment feedClicks counter and check if it reaches 3
            feedClicks++
            if (feedClicks == 3) {
                // Increase health level by 10
                healthLevel = (healthLevel + 5).coerceAtMost(120)
                healthStatus.text = "Health: $healthLevel"
                // Reset feedClicks counter
                feedClicks = 0
            }

            // Restore original pet image after a delay
            restoreOriginalPetImage(petImageView, 10000) // Change delay time as needed
        }

        cleanButton.setOnClickListener {
            // Change the pet's image to match the cleaning action icon
            petImageView.setImageResource(R.drawable.img)

            // Increase cleanliness level, capped at 100
            cleanlinessLevel = (cleanlinessLevel + 10).coerceAtMost(100)

            // Update the cleanliness status TextView
            cleanStatus.text = "Cleanliness: $cleanlinessLevel"

            // Increment cleanClicks counter and check if it reaches 3
            cleanClicks++
            if (cleanClicks == 3) {
                // Increase health level by 10
                healthLevel = (healthLevel + 3).coerceAtMost(120)
                healthStatus.text = "Health: $healthLevel"
                // Reset cleanClicks counter
                cleanClicks = 0
            }

            // Restore original pet image after a delay
            restoreOriginalPetImage(petImageView, 10000) // Change delay time as needed
        }

        playButton.setOnClickListener {
            // Change the pet's image to match the playing action icon
            petImageView.setImageResource(R.drawable.img_1)

            // Increase happiness level, capped at 100
            happinessLevel = (happinessLevel + 10).coerceAtMost(100)

            // Update the happiness status TextView
            playStatus.text = "Happiness: $happinessLevel"

            // Increment playClicks counter and check if it reaches 3
            playClicks++
            if (playClicks == 3) {
                // Increase health level by 10
                healthLevel = (healthLevel + 3).coerceAtMost(120)
                healthStatus.text = "Health: $healthLevel"
                // Reset playClicks counter
                playClicks = 0
            }

            // Restore original pet image after a delay
            restoreOriginalPetImage(petImageView, 10000) // Change delay time as needed
        }
    }

    // Function to restore original pet image after a delay
    private fun restoreOriginalPetImage(imageView: ImageView, delayMillis: Long) {
        handler.postDelayed({
            imageView.setImageResource(R.drawable.img_3)
        }, delayMillis)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove callbacks to prevent memory leaks
        handler.removeCallbacks(statusUpdateRunnable)
    }
}
