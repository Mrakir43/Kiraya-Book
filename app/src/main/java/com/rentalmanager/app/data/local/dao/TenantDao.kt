package com.rentalmanager.app.data.local.dao

import androidx.room.*
import com.rentalmanager.app.data.local.entities.LeaseStatus
import com.rentalmanager.app.data.local.entities.Tenant
import kotlinx.coroutines.flow.Flow

@Dao
interface TenantDao {
    
    @Query("SELECT * FROM tenants WHERE id = :tenantId")
    suspend fun getTenantById(tenantId: String): Tenant?
    
    @Query("SELECT * FROM tenants WHERE propertyId = :propertyId")
    fun getTenantsByProperty(propertyId: String): Flow<List<Tenant>>
    
    @Query("SELECT * FROM tenants WHERE userId = :userId")
    suspend fun getTenantByUserId(userId: String): Tenant?
    
    @Query("SELECT * FROM tenants WHERE leaseStatus = :status")
    fun getTenantsByStatus(status: LeaseStatus): Flow<List<Tenant>>
    
    @Query("SELECT * FROM tenants WHERE leaseEndDate < :currentTime AND leaseStatus = 'ACTIVE'")
    fun getExpiredLeases(currentTime: Long): Flow<List<Tenant>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTenant(tenant: Tenant)
    
    @Update
    suspend fun updateTenant(tenant: Tenant)
    
    @Delete
    suspend fun deleteTenant(tenant: Tenant)
    
    @Query("SELECT COUNT(*) FROM tenants WHERE propertyId = :propertyId AND leaseStatus = 'ACTIVE'")
    suspend fun getActiveTenantCount(propertyId: String): Int
}