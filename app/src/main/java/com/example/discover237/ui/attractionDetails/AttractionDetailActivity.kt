package com.example.discover237.ui.attractionDetails

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.discover237.R
import com.example.discover237.data.DataLoader
import com.google.android.material.imageview.ShapeableImageView
import android.Manifest
import android.content.pm.PackageManager
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlin.math.*

class AttractionDetailActivity : AppCompatActivity() {

    private var isFavorited = false
    private lateinit var attractionId: String
    private var targetLat = 0.0
    private var targetLng = 0.0

    private val locationPermissionLauncher = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            fetchAndShowDistance()
        } else {
            findViewById<TextView>(R.id.detailDistanceText).text = "Location permission denied"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attraction_details)

        attractionId = intent.getStringExtra("attractionId") ?: return
        isFavorited = intent.getBooleanExtra("isFavorited", false)

        val attraction = DataLoader.loadAttractions(this).firstOrNull { it.id == attractionId } ?: return

        targetLat = attraction.latitude
        targetLng = attraction.longitude

        findViewById<TextView>(R.id.detailNameText).text = attraction.name
        findViewById<TextView>(R.id.detailOpeningHoursText).text = attraction.openingHours
        findViewById<TextView>(R.id.detailFullDescText).text = attraction.fullDescription
        findViewById<TextView>(R.id.detailFunFactText).text = attraction.funFact

        val imageView: ShapeableImageView = findViewById(R.id.attractionImage)
        val imageName = attraction.imageFileName.substringBeforeLast(".")
        val resId = resources.getIdentifier(imageName, "drawable", packageName)
        imageView.setImageResource(if (resId != 0) resId else R.drawable.image_not_available)

        val favoriteButton: ImageButton = findViewById(R.id.favoriteButton)

        favoriteButton.setImageResource(
            if (isFavorited) android.R.drawable.btn_star_big_on
            else android.R.drawable.btn_star_big_off
        )
        favoriteButton.setOnClickListener {
            isFavorited = !isFavorited
            favoriteButton.setImageResource(
                if (isFavorited) android.R.drawable.btn_star_big_on
                else android.R.drawable.btn_star_big_off
            )
        }
        checkLocationPermissionAndFetch()
    }

    private fun checkLocationPermissionAndFetch() {
        val hasPermission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            fetchAndShowDistance()
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun fetchAndShowDistance() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val cancellationTokenSource = CancellationTokenSource()

        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        ).addOnSuccessListener { userLocation ->
            val distanceText = findViewById<TextView>(R.id.detailDistanceText)
            if (userLocation != null) {
                val distanceKm = haversineDistance(userLocation.latitude, userLocation.longitude, targetLat, targetLng)
                distanceText.text = "%.1f km away".format(distanceKm)
            } else {
                distanceText.text = "Location unavailable"
            }
        }
    }

    private fun haversineDistance(userLat: Double, userLong: Double, targetLat: Double, targetLong: Double): Double {
        val earthRadiusKm = 6371.0
        val dLat = Math.toRadians(targetLat - userLat)
        val dLon = Math.toRadians(targetLong - userLong)
        val a = sin(dLat / 2).pow(2) + cos(Math.toRadians(userLat)) * cos(Math.toRadians(targetLat)) * sin(dLon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return earthRadiusKm * c
    }

    override fun finish() {
        val resultIntent = Intent()
        resultIntent.putExtra("attractionId", attractionId)
        resultIntent.putExtra("isFavorited", isFavorited)
        setResult(RESULT_OK, resultIntent)
        super.finish()
    }
}