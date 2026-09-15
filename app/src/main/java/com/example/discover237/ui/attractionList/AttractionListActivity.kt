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
import com.example.discover237.data.model.Attraction
import com.example.discover237.ui.attractionDetails.AttractionDetailActivity

class AttractionListActivity : AppCompatActivity() {

    private val favoritedIds = mutableSetOf<String>()
    private lateinit var filteredAttractions: List<Attraction>
    private lateinit var adapter: AttractionAdapter
    private val detailLauncher = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && ::filteredAttractions.isInitialized) {
            val attractionId = result.data?.getStringExtra("attractionId")
            val isFavorited = result.data?.getBooleanExtra("isFavorited", false) ?: false

            if (attractionId != null) {
                if (isFavorited) favoritedIds.add(attractionId) else favoritedIds.remove(attractionId)

                val index = filteredAttractions.indexOfFirst { it.id == attractionId }
                if (index != -1) adapter.notifyItemChanged(index)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attraction_list)

        val regionId = intent.getStringExtra("regionId") ?: return
        val regionName = intent.getStringExtra("regionName") ?: ""

        findViewById<TextView>(R.id.regionTitleText).text = regionName

        filteredAttractions = DataLoader.loadAttractions(this).filter { it.regionId == regionId }

        val recyclerView: RecyclerView = findViewById(R.id.attractionsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = AttractionAdapter(filteredAttractions, favoritedIds) { attraction ->
            val intent = Intent(this, AttractionDetailActivity::class.java)
            intent.putExtra("attractionId", attraction.id)
            intent.putExtra("isFavorited", attraction.id in favoritedIds)
            detailLauncher.launch(intent)
        }
        recyclerView.adapter = adapter
    }
}