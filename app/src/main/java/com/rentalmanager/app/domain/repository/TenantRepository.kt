package com.rentalmanager.app.domain.repository

import com.rentalmanager.app.data.local.entities.Tenant
import com.rentalmanager.app.data.local.entities.LeaseStatus
import kotlinx.coroutines.flow.Flow

interface TenantRepository {
    suspend fun getTenantById(tenantId: String): Tenant?
    fun getTenantsByProperty(propertyId: String): Flow<List<Tenant>>
    suspend fun getTenantByUserId(userId: String): Tenant?
    fun getTenantsByStatus(status: LeaseStatus): Flow<List<Tenant>>
    fun getExpiredLeases(currentTime: Long): Flow<List<Tenant>>
    suspend fun insertTenant(tenant: Tenant)
    suspend fun updateTenant(tenant: Tenant)
    suspend fun deleteTenant(tenant: Tenant)
    suspend fun getActiveTenantCount(propertyId: String): Int
}