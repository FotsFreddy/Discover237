package com.example.discover237.ui.attractionList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.discover237.R
import com.example.discover237.data.DataLoader
import com.example.discover237.ui.attractionDetails.AttractionDetailActivity

class AttractionListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attraction_list)

        val regionId = intent.getStringExtra("regionId") ?: return
        val regionName = intent.getStringExtra("regionName") ?: ""

        findViewById<TextView>(R.id.regionTitleText).text = regionName

        val allAttractions = DataLoader.loadAttractions(this)
        val filtered = allAttractions.filter { it.regionId == regionId }
        Log.d("filtered",filtered.toString())

        val recyclerView: RecyclerView = findViewById(R.id.attractionsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AttractionAdapter(filtered) { attraction ->
            val intent = Intent(this, AttractionDetailActivity::class.java)
            intent.putExtra("attractionId", attraction.id)
            startActivity(intent)
        }
    }
}