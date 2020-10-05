package com.darekbx.priceobserver.ui.itemslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.darekbx.priceobserver.R
import com.darekbx.priceobserver.ui.item.ItemFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_items_list.*

@AndroidEntryPoint
class ItemsListFragment : Fragment(R.layout.fragment_items_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add_item_button.setOnClickListener {
            UrlSelectDialog().apply {
                onSelected = { url -> openNewUrl(url) }
            }.show(parentFragmentManager, UrlSelectDialog::class.simpleName)
        }
    }

    private fun openNewUrl(url: String) {
        findNavController().navigate(
            R.id.action_itemsListFragment_to_itemFragment,
            Bundle(1).apply {
                putString(ItemFragment.URL_ARGUMENT, url)
            })
    }
}
