package com.rentalmanager.app.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtils {
    
    private const val DATE_FORMAT_DISPLAY = "MMM dd, yyyy"
    private const val DATE_FORMAT_SHORT = "MM/dd/yyyy"
    private const val DATE_TIME_FORMAT = "MMM dd, yyyy HH:mm"
    
    fun formatDateForDisplay(timestamp: Long): String {
        return SimpleDateFormat(DATE_FORMAT_DISPLAY, Locale.getDefault()).format(Date(timestamp))
    }
    
    fun formatDateShort(timestamp: Long): String {
        return SimpleDateFormat(DATE_FORMAT_SHORT, Locale.getDefault()).format(Date(timestamp))
    }
    
    fun formatDateTime(timestamp: Long): String {
        return SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(Date(timestamp))
    }
    
    fun getDaysUntil(futureTimestamp: Long): Int {
        val currentTime = System.currentTimeMillis()
        val diffInMillis = futureTimestamp - currentTime
        return TimeUnit.MILLISECONDS.toDays(diffInMillis).toInt()
    }
    
    fun getDaysSince(pastTimestamp: Long): Int {
        val currentTime = System.currentTimeMillis()
        val diffInMillis = currentTime - pastTimestamp
        return TimeUnit.MILLISECONDS.toDays(diffInMillis).toInt()
    }
    
    fun isOverdue(dueTimestamp: Long): Boolean {
        return dueTimestamp < System.currentTimeMillis()
    }
    
    fun getRelativeTimeString(timestamp: Long): String {
        val currentTime = System.currentTimeMillis()
        val diffInMillis = currentTime - timestamp
        
        return when {
            diffInMillis < TimeUnit.MINUTES.toMillis(1) -> "Just now"
            diffInMillis < TimeUnit.HOURS.toMillis(1) -> {
                val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)
                "$minutes minute${if (minutes != 1L) "s" else ""} ago"
            }
            diffInMillis < TimeUnit.DAYS.toMillis(1) -> {
                val hours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
                "$hours hour${if (hours != 1L) "s" else ""} ago"
            }
            diffInMillis < TimeUnit.DAYS.toMillis(7) -> {
                val days = TimeUnit.MILLISECONDS.toDays(diffInMillis)
                "$days day${if (days != 1L) "s" else ""} ago"
            }
            else -> formatDateForDisplay(timestamp)
        }
    }
    
    fun getStartOfMonth(timestamp: Long = System.currentTimeMillis()): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
    
    fun getEndOfMonth(timestamp: Long = System.currentTimeMillis()): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.timeInMillis
    }
}