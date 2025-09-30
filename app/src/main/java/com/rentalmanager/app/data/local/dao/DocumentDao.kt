package com.rentalmanager.app.data.local.dao

import androidx.room.*
import com.rentalmanager.app.data.local.entities.Document
import com.rentalmanager.app.data.local.entities.DocumentType
import kotlinx.coroutines.flow.Flow

@Dao
interface DocumentDao {
    
    @Query("SELECT * FROM documents WHERE id = :documentId")
    suspend fun getDocumentById(documentId: String): Document?
    
    @Query("SELECT * FROM documents WHERE tenantId = :tenantId")
    fun getDocumentsByTenant(tenantId: String): Flow<List<Document>>
    
    @Query("SELECT * FROM documents WHERE propertyId = :propertyId")
    fun getDocumentsByProperty(propertyId: String): Flow<List<Document>>
    
    @Query("SELECT * FROM documents WHERE landlordId = :landlordId")
    fun getDocumentsByLandlord(landlordId: String): Flow<List<Document>>
    
    @Query("SELECT * FROM documents WHERE documentType = :type")
    fun getDocumentsByType(type: DocumentType): Flow<List<Document>>
    
    @Query("SELECT * FROM documents WHERE expiryDate < :currentTime AND expiryDate IS NOT NULL")
    fun getExpiredDocuments(currentTime: Long): Flow<List<Document>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocument(document: Document)
    
    @Update
    suspend fun updateDocument(document: Document)
    
    @Delete
    suspend fun deleteDocument(document: Document)
}