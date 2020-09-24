package com.darekbx.priceobserver

import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    internal class MyJsToAndroid : Any() {

        @JavascriptInterface
        fun elementId(idOrClass: String) {
            Log.v("------------", "Id-> $idOrClass")
        }

        @JavascriptInterface
        fun elementParentId(idOrClass: String) {
            Log.v("------------", "parentId-> $idOrClass")
        }
    }

    fun injectPriceObserverJs(): String {
        return """
javascript:function elementClick(event) {
    if (event.target.className == null) { 
        PriceObserver.elementId(event.target.id);
        PriceObserver.elementParentId(event.target.outerHTML);
        PriceObserver.elementParentId(event.target.parentNode.outerHTML);
    } else { 
        PriceObserver.elementId(event.target.className);
        PriceObserver.elementParentId(event.target.outerHTML);
        PriceObserver.elementParentId(event.target.parentNode.outerHTML);
    }
}
document.addEventListener("click", elementClick, true);
    """
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        web_view.loadUrl("https://sportprofit.pl/pl/p/Stery-Kris-King-InSet-czerwone-1-18-GripLock-cze/12392")
        web_view.addJavascriptInterface(MyJsToAndroid(), "PriceObserver")
        web_view.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                view.evaluateJavascript(injectPriceObserverJs(), null);
            }
        })

        val settings: WebSettings = web_view.getSettings()
        settings.javaScriptEnabled = true
    }
}
