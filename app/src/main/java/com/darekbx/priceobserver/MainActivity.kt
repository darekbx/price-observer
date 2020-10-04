package com.darekbx.priceobserver

import android.os.Bundle
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import com.darekbx.priceobserver.ui.TagSelectDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        web_view.loadUrl("https://bikecenter.pl/p5580,chris-king-inset-press-fit-1-1-8-stery-czarne-stery.html")
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
        }.show(supportFragmentManager, TagSelectDialog::class.simpleName)
    }

    fun savePriceObserver(name: String, partToObserve: String) {


    }

    fun processPageSource(source: String?) {

    }
}
