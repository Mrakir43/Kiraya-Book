package com.rentalmanager.app.di

import com.rentalmanager.app.data.repository.TenantRepositoryImpl
import com.rentalmanager.app.domain.repository.TenantRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindTenantRepository(
        tenantRepositoryImpl: TenantRepositoryImpl
    ): TenantRepository
}