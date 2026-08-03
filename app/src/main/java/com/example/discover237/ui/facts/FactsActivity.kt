package com.example.discover237.ui.facts

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.discover237.R
import com.example.discover237.ui.map.MapActivity

class FactsActivity: AppCompatActivity() {

    lateinit var counterText: TextView
    lateinit var titleText: TextView
    lateinit var bodyText: TextView

    private data class Fact(val title: String, val body: String)
    private val facts = listOf(
        Fact(
            "Where is Cameroon?",
            "Cameroon sits in Central Africa, often called 'Africa in Miniature' because it contains nearly every African landscape and culture within one country."
        ),
        Fact(
            "Population & Regions",
            "Cameroon is home to roughly 28 million people, organized into 10 distinct regions, each with its own capital, culture, and traditions."
        ),
        Fact(
            "Land & Geography",
            "Covering about 475,000 km², Cameroon spans Atlantic coastline, volcanic mountains, dense rainforest, and dry northern savanna."
        ),
        Fact(
            "Languages & Diversity",
            "With over 250 ethnic groups and languages, Cameroon uses French and English as its two official languages."
        )
    )
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facts)

        counterText = findViewById(R.id.factCounterTxt)
        titleText = findViewById(R.id.factTitleTxt)
        bodyText = findViewById(R.id.factBodyTxt)

        val prevButn: Button = findViewById(R.id.prevBtn)
        val nextButn: Button = findViewById(R.id.nextBtn)

        prevButn.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                updateFactDisplay()
            }
        }

        nextButn.setOnClickListener {
            if (currentIndex == facts.size - 1) {
                startActivity(Intent(this, MapActivity::class.java))
                finish()
            } else {
                currentIndex++
                updateFactDisplay()
            }
        }

        updateFactDisplay()
    }

    private fun updateFactDisplay() {
        val fact = facts[currentIndex]
        counterText.text = "${currentIndex + 1}/${facts.size}"
        titleText.text = fact.title
        bodyText.text = fact.body
    }
}
