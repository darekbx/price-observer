package com.darekbx.priceobserver.model

import com.darekbx.priceobserver.repository.local.entities.ItemDto
import java.lang.IllegalStateException

class Item(
    val id: Int,
    val url: String,
    val name: String,
    val regex: String,
    val initialPrice: String,
    val actualPrice: String?
) {

    fun isDifferent() = initialPrice != actualPrice

    companion object {

        fun fromItemDto(itemDto: ItemDto) =
            with(itemDto) {
                Item(
                    id ?: throw IllegalStateException("Id is null"),
                    url,
                    name,
                    regex,
                    initialPrice,
                    actualPrice
                )
            }
    }
}
