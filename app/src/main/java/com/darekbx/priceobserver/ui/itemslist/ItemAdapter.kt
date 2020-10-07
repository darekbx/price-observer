package com.darekbx.priceobserver.ui.itemslist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darekbx.priceobserver.databinding.AdapterItemBinding
import com.darekbx.priceobserver.model.Item
import com.darekbx.priceobserver.repository.remote.HtmlDownloader
import kotlinx.coroutines.*

class ItemAdapter(val context: Context?)
    : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    var onLongPress: ((Item) -> Unit)? = null

    var items = listOf<Item>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val binding = AdapterItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, onLongPress)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val part = items.get(position)
        viewHolder.bind(part)
    }

    val inflater by lazy { LayoutInflater.from(context) }

    class ViewHolder(val binding: AdapterItemBinding, var onLongPress: ((Item) -> Unit)?) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {

            binding.item = item
            binding.executePendingBindings()

            binding.root.setOnLongClickListener {
                onLongPress?.invoke(item)
                true
            }

            binding.actualPrice.setText("Loading...")

            updatePrice(item, {
                when {
                    it == null -> binding.actualPrice.setText("Error!")
                    else -> binding.actualPrice.setText(it)
                }
            })
        }

        fun updatePrice(item: Item, callback: (String?) -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                val html = HtmlDownloader.download(item.url)
                withContext(Dispatchers.Main) {
                    when {
                        html == null -> callback(null)
                        else -> {
                            val price = extractPrice(item, html)
                            callback(price)
                        }
                    }
                }
            }
        }

        private fun extractPrice(item: Item, html: String): String? {
            val regex = item.regex.toRegex()
            val match = regex.find(html)
            val regexStringChunks = item.regex.split("(.*?)")
            return match?.value
                ?.removePrefix(regexStringChunks[0])
                ?.removeSuffix(regexStringChunks[1])
        }
    }
}
