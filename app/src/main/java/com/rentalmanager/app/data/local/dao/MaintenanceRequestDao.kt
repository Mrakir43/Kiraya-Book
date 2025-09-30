package com.rentalmanager.app.data.local.dao

import androidx.room.*
import com.rentalmanager.app.data.local.entities.MaintenanceRequest
import com.rentalmanager.app.data.local.entities.MaintenanceStatus
import com.rentalmanager.app.data.local.entities.Priority
import kotlinx.coroutines.flow.Flow

@Dao
interface MaintenanceRequestDao {
    
    @Query("SELECT * FROM maintenance_requests WHERE id = :requestId")
    suspend fun getMaintenanceRequestById(requestId: String): MaintenanceRequest?
    
    @Query("SELECT * FROM maintenance_requests WHERE tenantId = :tenantId ORDER BY createdAt DESC")
    fun getMaintenanceRequestsByTenant(tenantId: String): Flow<List<MaintenanceRequest>>
    
    @Query("SELECT * FROM maintenance_requests WHERE propertyId = :propertyId ORDER BY createdAt DESC")
    fun getMaintenanceRequestsByProperty(propertyId: String): Flow<List<MaintenanceRequest>>
    
    @Query("SELECT * FROM maintenance_requests WHERE status = :status ORDER BY priority DESC, createdAt ASC")
    fun getMaintenanceRequestsByStatus(status: MaintenanceStatus): Flow<List<MaintenanceRequest>>
    
    @Query("SELECT * FROM maintenance_requests WHERE priority = :priority ORDER BY createdAt ASC")
    fun getMaintenanceRequestsByPriority(priority: Priority): Flow<List<MaintenanceRequest>>
    
    @Query("SELECT * FROM maintenance_requests WHERE assignedTo = :contractorId ORDER BY scheduledDate ASC")
    fun getMaintenanceRequestsByContractor(contractorId: String): Flow<List<MaintenanceRequest>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaintenanceRequest(request: MaintenanceRequest)
    
    @Update
    suspend fun updateMaintenanceRequest(request: MaintenanceRequest)
    
    @Delete
    suspend fun deleteMaintenanceRequest(request: MaintenanceRequest)
}