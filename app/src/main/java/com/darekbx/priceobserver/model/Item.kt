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

        val replacements = arrayListOf("&nbsp;")

        fun fromItemDto(itemDto: ItemDto) =
            with(itemDto) {
                var cleanInitialPrice = cleanPrice(initialPrice)
                var cleanActualPrice = cleanPrice(actualPrice)
                Item(
                    id ?: throw IllegalStateException("Id is null"),
                    url,
                    name,
                    regex,
                    cleanInitialPrice.trim(),
                    cleanActualPrice.trim()
                )
            }

        private fun cleanPrice(cleanActualPrice: String): String {
            var cleanActualPriceCopy = cleanActualPrice
            replacements.forEach {
                cleanActualPriceCopy = cleanActualPriceCopy.replace(it, " ")
            }
            return cleanActualPriceCopy
        }
    }
}
