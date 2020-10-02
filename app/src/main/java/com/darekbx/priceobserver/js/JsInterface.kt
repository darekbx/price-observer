package com.darekbx.priceobserver.js

import android.webkit.JavascriptInterface

class JsInterface {

    var htmlPartCallback: ((String) -> Unit)? = null

    @JavascriptInterface
    fun elementParentId(idOrClass: String) {
        htmlPartCallback?.invoke(idOrClass)
    }
}
