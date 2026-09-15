package com.example.discover237.ui.map

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.discover237.R
import com.example.discover237.data.DataLoader
import com.example.discover237.ui.attractionList.AttractionListActivity

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val regions = DataLoader.loadRegions(this)

        val mapView: RegionMapView = findViewById(R.id.regionMapView)
        mapView.regions = regions
        mapView.onRegionTapped = { region ->
            Toast.makeText(this, "You've chosen the ${region.name} region", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, AttractionListActivity::class.java)
            intent.putExtra("regionId", region.id)
            intent.putExtra("regionName", region.name)
            startActivity(intent)
        }

        val legendContainer: LinearLayout = findViewById(R.id.legendContainer)
        regions.forEachIndexed { index, region ->
            val letter = ('A' + index).toString()
            val row = TextView(this).apply {
                text = "$letter — ${region.name}"
                textSize = 14f
                setTextColor(resources.getColor(R.color.forest_green, theme))
                setPadding(0, 8, 0, 8)
            }
            legendContainer.addView(row)
        }
    }
}