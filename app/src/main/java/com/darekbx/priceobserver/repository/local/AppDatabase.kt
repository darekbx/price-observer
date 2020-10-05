package com.darekbx.priceobserver.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.darekbx.priceobserver.repository.local.entities.ItemDto

@Database(entities = arrayOf(ItemDto::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        val DB_NAME = "app_database"
    }

    abstract fun itemsDao(): ItemsDao
}
