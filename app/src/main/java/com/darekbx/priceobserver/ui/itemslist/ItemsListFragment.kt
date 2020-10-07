package com.darekbx.priceobserver.ui.itemslist

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.darekbx.priceobserver.R
import com.darekbx.priceobserver.model.Item
import com.darekbx.priceobserver.ui.item.ItemFragment
import com.darekbx.priceobserver.ui.viewmodels.ItemViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_items_list.*

@AndroidEntryPoint
class ItemsListFragment : Fragment(R.layout.fragment_items_list) {

    private val itemViewModel: ItemViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add_item_button.setOnClickListener {
            UrlSelectDialog().apply {
                onSelected = { url -> openNewUrl(url) }
            }.show(parentFragmentManager, UrlSelectDialog::class.simpleName)
        }

        initializeList()
        initializeViewModel()

        itemViewModel.fetchItems()
    }

    private fun initializeViewModel() {
        itemViewModel.items.observe(viewLifecycleOwner, { items ->
            itemAdapter.items = items
        })
    }

    private fun initializeList() {
        items_list.adapter = itemAdapter
        items_list.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun openNewUrl(url: String) {
        findNavController().navigate(
            R.id.action_itemsListFragment_to_itemFragment,
            Bundle(1).apply {
                putString(ItemFragment.URL_ARGUMENT, url)
            })
    }

    private fun deleteItem(item: Item) {
        AlertDialog
            .Builder(requireContext())
            .setMessage(getString(R.string.delete_item, item.name))
            .setPositiveButton(R.string.delete_yes, { _, _ -> itemViewModel.delete(item) })
            .setNegativeButton(R.string.delete_no, null)
            .show()
    }

    private val itemAdapter by lazy {
        ItemAdapter(requireContext()).apply {
            onLongPress = { deleteItem(it) }
        }
    }
}
