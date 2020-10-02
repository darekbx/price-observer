package com.darekbx.priceobserver.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import com.darekbx.priceobserver.js.JsInterface

class ObservableWebView(context: Context, attributeSet: AttributeSet?): WebView(context, attributeSet) {

    var htmlCallback: ((String) -> Unit)? = null

    fun initializeObserver() {
        addJavascriptInterface(jsInterface, "PriceObserver")
        setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                view.evaluateJavascript(injectPriceObserverJs(), null)
            }
        })
    }

    private fun injectPriceObserverJs(): String {
        return """
javascript:function elementClick(event) {
    if (event.target.className == null) { 
        PriceObserver.elementParentId(event.target.parentNode.outerHTML);
    } else { 
        PriceObserver.elementParentId(event.target.parentNode.outerHTML);
    }
}
document.addEventListener("click", elementClick, true);
    """
    }

    private val jsInterface by lazy {
        JsInterface().apply {
            htmlPartCallback = {
                htmlCallback?.invoke(it)
            }
        }
    }
}
