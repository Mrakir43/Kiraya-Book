package com.rentalmanager.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "maintenance_requests")
data class MaintenanceRequest(
    @PrimaryKey
    val id: String,
    val tenantId: String,
    val propertyId: String,
    val title: String,
    val description: String,
    val priority: Priority,
    val category: MaintenanceCategory,
    val status: MaintenanceStatus,
    val imageUrls: List<String> = emptyList(),
    val assignedTo: String? = null, // Contractor ID
    val estimatedCost: Double? = null,
    val actualCost: Double? = null,
    val scheduledDate: Long? = null,
    val completedDate: Long? = null,
    val tenantRating: Int? = null, // 1-5 stars
    val tenantFeedback: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)

enum class Priority {
    LOW,
    MEDIUM,
    HIGH,
    EMERGENCY
}

enum class MaintenanceCategory {
    PLUMBING,
    ELECTRICAL,
    HVAC,
    APPLIANCES,
    FLOORING,
    PAINTING,
    PEST_CONTROL,
    SECURITY,
    LANDSCAPING,
    OTHER
}

enum class MaintenanceStatus {
    SUBMITTED,
    ACKNOWLEDGED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}