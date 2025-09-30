package com.rentalmanager.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "properties")
data class Property(
    @PrimaryKey
    val id: String,
    val landlordId: String,
    val name: String,
    val address: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val propertyType: PropertyType,
    val totalUnits: Int,
    val description: String? = null,
    val imageUrls: List<String> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val isActive: Boolean = true
)

enum class PropertyType {
    APARTMENT,
    HOUSE,
    CONDO,
    TOWNHOUSE,
    STUDIO,
    OTHER
}