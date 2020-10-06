package com.darekbx.priceobserver.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darekbx.priceobserver.model.Item
import com.darekbx.priceobserver.repository.local.ItemsDao
import com.darekbx.priceobserver.repository.local.entities.ItemDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel  @ViewModelInject constructor(
    private val itemsDao: ItemsDao
) : ViewModel() {

    val items = MutableLiveData<List<Item>>()
    val itemAddResult = MutableLiveData<Boolean>()

    fun add(name: String, url: String, regex: String, price: String) {
        viewModelScope.launch (Dispatchers.IO) {
            val itemDto = ItemDto(null, url, name, regex, price, price)
            val newId = itemsDao.addItem(itemDto)
            itemAddResult.postValue(newId > 0L)
        }
    }

    fun fetchItems() {
        viewModelScope.launch (Dispatchers.IO) {
            val itemsList = itemsDao.getAllItems().map { itemDto ->
                Item.fromItemDto(itemDto)
            }
            items.postValue(itemsList)
        }
    }
}
