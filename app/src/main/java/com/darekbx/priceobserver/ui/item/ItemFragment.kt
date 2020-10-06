package com.darekbx.priceobserver.ui.item

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.darekbx.priceobserver.R
import com.darekbx.priceobserver.ui.viewmodels.ItemViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_item.*
import java.lang.IllegalStateException

@AndroidEntryPoint
class ItemFragment : Fragment(R.layout.fragment_item) {

    companion object {
        val URL_ARGUMENT = "url_argument"
    }

    private val itemViewModel: ItemViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString(URL_ARGUMENT)?.let { url ->
            loadUrl(url)
        }

        itemViewModel.itemAddResult.observe(viewLifecycleOwner, { result ->
            when (result){
                true -> showDialog(R.string.item_added, {
                    findNavController().popBackStack()
                })
                else -> showDialog(R.string.unable_to_add_item)
            }
        })
    }

    private fun loadUrl(url: String) {
        web_view.loadUrl(url)
        web_view.htmlPartCallback = { element ->
            element?.let {
                displayHtmlpart(it)
            }
        }
        web_view.pageSourceCallback = { processPageSource(it) }
        web_view.initializeObserver()

        val settings: WebSettings = web_view.getSettings()
        settings.javaScriptEnabled = true
    }

    fun displayHtmlpart(htmlPart: String) {
        TagSelectDialog().apply {
            arguments = Bundle(1).apply {
                putString(TagSelectDialog.HTML_PART, htmlPart)
            }
            onCompleted = { name, partToObserve ->
                savePriceObserver(name, partToObserve)
            }
        }.show(parentFragmentManager, TagSelectDialog::class.simpleName)
    }

    fun savePriceObserver(name: String, partToObserve: String) {

        val regexString = partToObserve.replace("%", "(.*?)")
        val regex = regexString.toRegex()
        val match = regex.find(pageSource)
        val url = arguments?.getString(URL_ARGUMENT) ?: throw IllegalStateException("Url is null")

        val regexStringChunks = partToObserve.split('%')
        val price = match?.value
            ?.removePrefix(regexStringChunks[0])
            ?.removeSuffix(regexStringChunks[1])

        when {
            match == null -> showDialog(R.string.no_match)
            price == null -> showDialog(R.string.wrong_price_format)
            else -> itemViewModel.add(name, url, regexString, price)
        }
    }

    private fun showDialog(messageId: Int, callback: () -> Unit = { }) {
        AlertDialog
            .Builder(requireContext())
            .setMessage(messageId)
            .setPositiveButton(R.string.dialog_ok, { _, _ -> callback() })
            .show()
    }

    var pageSource: String = ""

    fun processPageSource(source: String?) {
        source?.let {
            pageSource = it
        }
    }
}
