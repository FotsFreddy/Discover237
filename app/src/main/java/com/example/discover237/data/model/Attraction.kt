package com.example.discover237.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Attraction(
    val id: String,
    val regionId: String,
    val name: String,
    val category: String,
    val shortDescription: String,
    val fullDescription: String,
    val funFact: String,
    val openingHours: String,
    val imageFileName: String,
    val latitude: Double,
    val longitude: Double
)