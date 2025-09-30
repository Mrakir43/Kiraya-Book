package com.rentalmanager.app.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.rentalmanager.app.data.local.dao.*
import com.rentalmanager.app.data.local.entities.*

@Database(
    entities = [
        User::class,
        Property::class,
        Tenant::class,
        Payment::class,
        MaintenanceRequest::class,
        Document::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RentalDatabase : RoomDatabase() {
    
    abstract fun userDao(): UserDao
    abstract fun propertyDao(): PropertyDao
    abstract fun tenantDao(): TenantDao
    abstract fun paymentDao(): PaymentDao
    abstract fun maintenanceRequestDao(): MaintenanceRequestDao
    abstract fun documentDao(): DocumentDao
    
    companion object {
        @Volatile
        private var INSTANCE: RentalDatabase? = null
        
        fun getDatabase(context: Context): RentalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RentalDatabase::class.java,
                    "rental_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}