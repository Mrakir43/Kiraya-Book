package com.rentalmanager.app.di

import android.content.Context
import androidx.room.Room
import com.rentalmanager.app.data.local.RentalDatabase
import com.rentalmanager.app.data.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideRentalDatabase(@ApplicationContext context: Context): RentalDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            RentalDatabase::class.java,
            "rental_database"
        ).build()
    }
    
    @Provides
    fun provideUserDao(database: RentalDatabase): UserDao = database.userDao()
    
    @Provides
    fun providePropertyDao(database: RentalDatabase): PropertyDao = database.propertyDao()
    
    @Provides
    fun provideTenantDao(database: RentalDatabase): TenantDao = database.tenantDao()
    
    @Provides
    fun providePaymentDao(database: RentalDatabase): PaymentDao = database.paymentDao()
    
    @Provides
    fun provideMaintenanceRequestDao(database: RentalDatabase): MaintenanceRequestDao = database.maintenanceRequestDao()
    
    @Provides
    fun provideDocumentDao(database: RentalDatabase): DocumentDao = database.documentDao()
}