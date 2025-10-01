package com.rentalmanager.app.data.local.dao

import androidx.room.*
import com.rentalmanager.app.data.local.entities.Payment
import com.rentalmanager.app.data.local.entities.PaymentStatus
import com.rentalmanager.app.data.local.entities.PaymentType
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentDao {
    
    @Query("SELECT * FROM payments WHERE id = :paymentId")
    suspend fun getPaymentById(paymentId: String): Payment?
    
    @Query("SELECT * FROM payments WHERE tenantId = :tenantId ORDER BY dueDate DESC")
    fun getPaymentsByTenant(tenantId: String): Flow<List<Payment>>
    
    @Query("SELECT * FROM payments WHERE propertyId = :propertyId ORDER BY dueDate DESC")
    fun getPaymentsByProperty(propertyId: String): Flow<List<Payment>>
    
    @Query("SELECT * FROM payments WHERE status = :status ORDER BY dueDate ASC")
    fun getPaymentsByStatus(status: PaymentStatus): Flow<List<Payment>>
    
    @Query("SELECT * FROM payments WHERE dueDate < :currentTime AND status = 'PENDING'")
    fun getOverduePayments(currentTime: Long): Flow<List<Payment>>
    
    @Query("SELECT * FROM payments WHERE tenantId = :tenantId AND paymentType = :type ORDER BY dueDate DESC")
    fun getPaymentsByTenantAndType(tenantId: String, type: PaymentType): Flow<List<Payment>>
    
    @Query("SELECT SUM(amount) FROM payments WHERE tenantId = :tenantId AND status = 'PAID' AND paidDate BETWEEN :startDate AND :endDate")
    suspend fun getTotalPaidAmount(tenantId: String, startDate: Long, endDate: Long): Double?
    
    @Query("SELECT SUM(amount) FROM payments WHERE propertyId = :propertyId AND status = 'PAID' AND paidDate BETWEEN :startDate AND :endDate")
    suspend fun getTotalPropertyIncome(propertyId: String, startDate: Long, endDate: Long): Double?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(payment: Payment)
    
    @Update
    suspend fun updatePayment(payment: Payment)
    
    @Delete
    suspend fun deletePayment(payment: Payment)
}