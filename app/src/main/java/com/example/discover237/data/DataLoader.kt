package com.example.discover237.data

import android.content.Context
import com.example.discover237.data.model.Region
import com.example.discover237.data.model.Attraction
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

object DataLoader {

    private val json = Json { ignoreUnknownKeys = true }

    fun loadRegions(context: Context): List<Region> {
        val jsonText = context.assets.open("regions.json")
            .bufferedReader()
            .use { it.readText() }

        return json.decodeFromString(jsonText)
    }

    fun loadAttractions(context: Context): List<Attraction> {
        val jsonText = context.assets.open("attractions.json")
            .bufferedReader()
            .use { it.readText() }

        return json.decodeFromString(jsonText)
    }
}