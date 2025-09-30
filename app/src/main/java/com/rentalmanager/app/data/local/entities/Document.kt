package com.rentalmanager.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "documents")
data class Document(
    @PrimaryKey
    val id: String,
    val tenantId: String? = null,
    val propertyId: String? = null,
    val landlordId: String? = null,
    val title: String,
    val documentType: DocumentType,
    val fileUrl: String,
    val fileName: String,
    val fileSize: Long,
    val mimeType: String,
    val version: Int = 1,
    val isSigned: Boolean = false,
    val signedDate: Long? = null,
    val expiryDate: Long? = null,
    val description: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)

enum class DocumentType {
    LEASE_AGREEMENT,
    RENTAL_APPLICATION,
    PROOF_OF_INCOME,
    ID_VERIFICATION,
    INSURANCE_POLICY,
    INSPECTION_REPORT,
    RECEIPT,
    NOTICE,
    OTHER
}