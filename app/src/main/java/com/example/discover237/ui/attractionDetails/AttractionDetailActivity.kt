package com.example.discover237.ui.attractionDetails

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.discover237.R
import com.example.discover237.data.DataLoader
import com.google.android.material.imageview.ShapeableImageView

class AttractionDetailActivity : AppCompatActivity() {

    private var isFavorited = false
    private lateinit var attractionId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attraction_details)

        attractionId = intent.getStringExtra("attractionId") ?: return
        isFavorited = intent.getBooleanExtra("isFavorited", false)

        val attraction = DataLoader.loadAttractions(this).firstOrNull { it.id == attractionId } ?: return

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
    }

    override fun finish() {
        val resultIntent = Intent()
        resultIntent.putExtra("attractionId", attractionId)
        resultIntent.putExtra("isFavorited", isFavorited)
        setResult(RESULT_OK, resultIntent)
        super.finish()
    }
}