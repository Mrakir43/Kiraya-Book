package com.rentalmanager.app.data.repository

import com.rentalmanager.app.data.local.dao.TenantDao
import com.rentalmanager.app.data.local.entities.Tenant
import com.rentalmanager.app.data.local.entities.LeaseStatus
import com.rentalmanager.app.domain.repository.TenantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TenantRepositoryImpl @Inject constructor(
    private val tenantDao: TenantDao
) : TenantRepository {
    
    override suspend fun getTenantById(tenantId: String): Tenant? {
        return tenantDao.getTenantById(tenantId)
    }
    
    override fun getTenantsByProperty(propertyId: String): Flow<List<Tenant>> {
        return tenantDao.getTenantsByProperty(propertyId)
    }
    
    override suspend fun getTenantByUserId(userId: String): Tenant? {
        return tenantDao.getTenantByUserId(userId)
    }
    
    override fun getTenantsByStatus(status: LeaseStatus): Flow<List<Tenant>> {
        return tenantDao.getTenantsByStatus(status)
    }
    
    override fun getExpiredLeases(currentTime: Long): Flow<List<Tenant>> {
        return tenantDao.getExpiredLeases(currentTime)
    }
    
    override suspend fun insertTenant(tenant: Tenant) {
        tenantDao.insertTenant(tenant)
    }
    
    override suspend fun updateTenant(tenant: Tenant) {
        tenantDao.updateTenant(tenant)
    }
    
    override suspend fun deleteTenant(tenant: Tenant) {
        tenantDao.deleteTenant(tenant)
    }
    
    override suspend fun getActiveTenantCount(propertyId: String): Int {
        return tenantDao.getActiveTenantCount(propertyId)
    }
}