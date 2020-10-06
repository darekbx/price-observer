package com.darekbx.priceobserver.repository.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
class ItemDto(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "regex") val regex: String,
    @ColumnInfo(name = "initial_price") val initialPrice: String,
    @ColumnInfo(name = "actual_price") val actualPrice: String
)
