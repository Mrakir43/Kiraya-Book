package com.rentalmanager.app.data.local.dao

import androidx.room.*
import com.rentalmanager.app.data.local.entities.Property
import com.rentalmanager.app.data.local.entities.PropertyType
import kotlinx.coroutines.flow.Flow

@Dao
interface PropertyDao {
    
    @Query("SELECT * FROM properties WHERE id = :propertyId")
    suspend fun getPropertyById(propertyId: String): Property?
    
    @Query("SELECT * FROM properties WHERE landlordId = :landlordId")
    fun getPropertiesByLandlord(landlordId: String): Flow<List<Property>>
    
    @Query("SELECT * FROM properties WHERE propertyType = :type")
    fun getPropertiesByType(type: PropertyType): Flow<List<Property>>
    
    @Query("SELECT * FROM properties WHERE city = :city")
    fun getPropertiesByCity(city: String): Flow<List<Property>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProperty(property: Property)
    
    @Update
    suspend fun updateProperty(property: Property)
    
    @Delete
    suspend fun deleteProperty(property: Property)
}