package com.rentalmanager.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payments")
data class Payment(
    @PrimaryKey
    val id: String,
    val tenantId: String,
    val propertyId: String,
    val amount: Double,
    val paymentType: PaymentType,
    val paymentMethod: PaymentMethod,
    val dueDate: Long,
    val paidDate: Long? = null,
    val status: PaymentStatus,
    val description: String? = null,
    val receiptUrl: String? = null,
    val lateFee: Double = 0.0,
    val createdAt: Long = System.currentTimeMillis()
)

enum class PaymentType {
    RENT,
    ELECTRICITY,
    WATER,
    GAS,
    INTERNET,
    MAINTENANCE,
    SECURITY_DEPOSIT,
    LATE_FEE,
    OTHER
}

enum class PaymentMethod {
    CASH,
    CHECK,
    BANK_TRANSFER,
    CREDIT_CARD,
    DIGITAL_WALLET,
    OTHER
}

enum class PaymentStatus {
    PENDING,
    PAID,
    OVERDUE,
    PARTIAL,
    CANCELLED
}