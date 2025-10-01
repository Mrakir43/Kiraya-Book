package com.rentalmanager.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String,
    val email: String,
    val name: String,
    val phoneNumber: String,
    val userType: UserType,
    val profileImageUrl: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val isActive: Boolean = true
)

enum class UserType {
    LANDLORD,
    TENANT
}