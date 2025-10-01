package com.rentalmanager.app.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.rentalmanager.app.R
import com.rentalmanager.app.presentation.MainActivity

class NotificationHelper(private val context: Context) {
    
    companion object {
        const val CHANNEL_ID_PAYMENTS = "payments_channel"
        const val CHANNEL_ID_MAINTENANCE = "maintenance_channel"
        const val CHANNEL_ID_LEASE = "lease_channel"
        
        const val NOTIFICATION_ID_PAYMENT_DUE = 1001
        const val NOTIFICATION_ID_MAINTENANCE = 1002
        const val NOTIFICATION_ID_LEASE_EXPIRY = 1003
    }
    
    init {
        createNotificationChannels()
    }
    
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channels = listOf(
                NotificationChannel(
                    CHANNEL_ID_PAYMENTS,
                    "Payment Reminders",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Notifications for rent and utility payment reminders"
                },
                NotificationChannel(
                    CHANNEL_ID_MAINTENANCE,
                    "Maintenance Requests",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Notifications for maintenance request updates"
                },
                NotificationChannel(
                    CHANNEL_ID_LEASE,
                    "Lease Management",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Notifications for lease renewals and expirations"
                }
            )
            
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            channels.forEach { channel ->
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
    
    fun showPaymentDueNotification(tenantName: String, amount: Double, daysOverdue: Int = 0) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val title = if (daysOverdue > 0) "Overdue Payment" else "Payment Due"
        val message = if (daysOverdue > 0) {
            "$tenantName has an overdue payment of $$amount ($daysOverdue days overdue)"
        } else {
            "$tenantName has a payment of $$amount due soon"
        }
        
        val notification = NotificationCompat.Builder(context, CHANNEL_ID_PAYMENTS)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        
        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID_PAYMENT_DUE, notification)
        }
    }
    
    fun showMaintenanceNotification(title: String, description: String, priority: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val notificationPriority = when (priority.uppercase()) {
            "EMERGENCY" -> NotificationCompat.PRIORITY_MAX
            "HIGH" -> NotificationCompat.PRIORITY_HIGH
            else -> NotificationCompat.PRIORITY_DEFAULT
        }
        
        val notification = NotificationCompat.Builder(context, CHANNEL_ID_MAINTENANCE)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("New Maintenance Request - $priority Priority")
            .setContentText("$title: $description")
            .setPriority(notificationPriority)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText("$title: $description"))
            .build()
        
        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID_MAINTENANCE, notification)
        }
    }
    
    fun showLeaseExpiryNotification(tenantName: String, daysUntilExpiry: Int) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val message = when {
            daysUntilExpiry <= 0 -> "$tenantName's lease has expired"
            daysUntilExpiry <= 30 -> "$tenantName's lease expires in $daysUntilExpiry days"
            else -> "$tenantName's lease expires soon"
        }
        
        val notification = NotificationCompat.Builder(context, CHANNEL_ID_LEASE)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Lease Expiry Alert")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        
        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID_LEASE_EXPIRY, notification)
        }
    }
}