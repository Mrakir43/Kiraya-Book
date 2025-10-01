package com.rentalmanager.app.domain.usecase

import com.rentalmanager.app.data.local.entities.Tenant
import com.rentalmanager.app.data.local.entities.LeaseStatus
import com.rentalmanager.app.domain.repository.TenantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTenantsUseCase @Inject constructor(
    private val repository: TenantRepository
) {
    operator fun invoke(propertyId: String): Flow<List<Tenant>> {
        return repository.getTenantsByProperty(propertyId)
    }
}

class AddTenantUseCase @Inject constructor(
    private val repository: TenantRepository
) {
    suspend operator fun invoke(tenant: Tenant) {
        repository.insertTenant(tenant)
    }
}

class UpdateTenantUseCase @Inject constructor(
    private val repository: TenantRepository
) {
    suspend operator fun invoke(tenant: Tenant) {
        repository.updateTenant(tenant)
    }
}

class GetExpiredLeasesUseCase @Inject constructor(
    private val repository: TenantRepository
) {
    operator fun invoke(): Flow<List<Tenant>> {
        return repository.getExpiredLeases(System.currentTimeMillis())
    }
}

class GetTenantByIdUseCase @Inject constructor(
    private val repository: TenantRepository
) {
    suspend operator fun invoke(tenantId: String): Tenant? {
        return repository.getTenantById(tenantId)
    }
}