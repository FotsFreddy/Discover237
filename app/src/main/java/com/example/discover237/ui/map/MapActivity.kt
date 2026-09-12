package com.example.discover237.ui.map

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.discover237.R
import com.example.discover237.data.DataLoader
import com.example.discover237.ui.attractionList.AttractionListActivity

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val container: LinearLayout = findViewById(R.id.regionButtonContainer)
        val regions = DataLoader.loadRegions(this)

        for (region in regions) {
            val button = Button(this).apply {
                text = region.name
                setOnClickListener {
                    val intent = Intent(this@MapActivity, AttractionListActivity::class.java)
                    intent.putExtra("regionId", region.id)
                    intent.putExtra("regionName", region.name)
                    startActivity(intent)
                }
            }
            container.addView(button)
        }
    }
}