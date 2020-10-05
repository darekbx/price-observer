package com.darekbx.priceobserver.ui.item

import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import androidx.fragment.app.Fragment
import com.darekbx.priceobserver.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_item.*

@AndroidEntryPoint
class ItemFragment : Fragment(R.layout.fragment_item) {

    companion object {
        val URL_ARGUMENT = "url_argument"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString(URL_ARGUMENT)?.let { url ->
            loadUrl(url)
        }
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

        val r = partToObserve.replace("%", "(.*?)").toRegex()
        val match = r.find(s)?.value

    }

    var s: String = ""

    fun processPageSource(source: String?) {
        source?.let {
            s = it
        }
    }
}
