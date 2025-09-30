package com.rentalmanager.app.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Gson().toJson(value)
    }
    
    @TypeConverter
    fun toStringList(value: String): List<String> {
        return try {
            Gson().fromJson<List<String>>(
                value,
                object : TypeToken<List<String>>() {}.type
            ) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}