package com.rentalmanager.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tenants")
data class Tenant(
    @PrimaryKey
    val id: String,
    val userId: String, // Reference to User entity
    val propertyId: String,
    val unitNumber: String? = null,
    val leaseStartDate: Long,
    val leaseEndDate: Long,
    val monthlyRent: Double,
    val securityDeposit: Double,
    val emergencyContactName: String? = null,
    val emergencyContactPhone: String? = null,
    val moveInDate: Long? = null,
    val moveOutDate: Long? = null,
    val leaseStatus: LeaseStatus = LeaseStatus.ACTIVE,
    val notes: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)

enum class LeaseStatus {
    ACTIVE,
    EXPIRED,
    TERMINATED,
    PENDING
}