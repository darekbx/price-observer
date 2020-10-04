package com.darekbx.priceobserver.js

import android.webkit.JavascriptInterface

class JsInterface {

    var onElementClick: ((String?) -> Unit)? = null
    var onPageSource: ((String?) -> Unit)? = null

    @JavascriptInterface
    fun elementParentId(idOrClass: String) {
        onElementClick?.invoke(idOrClass)
    }

    @JavascriptInterface
    fun pageSource(html: String?) {
        onPageSource?.invoke(html)
    }
}
