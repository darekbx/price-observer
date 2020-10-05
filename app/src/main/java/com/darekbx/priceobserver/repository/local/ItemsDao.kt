package com.darekbx.priceobserver.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.darekbx.priceobserver.repository.local.entities.ItemDto

@Dao
interface ItemsDao {

    @Query("SELECT * FROM item")
    fun getAllItems(): List<ItemDto>

    @Query("SELECT * FROM item WHERE id = :id")
    fun getItem(id: Int): ItemDto

    @Query("DELETE FROM item WHERE id = :id")
    fun deleteItem(id: Int)

    @Insert
    fun addItem(itemDto: ItemDto)
}
