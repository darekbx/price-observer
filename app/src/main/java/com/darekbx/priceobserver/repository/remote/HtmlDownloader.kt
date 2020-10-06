package com.darekbx.priceobserver.repository.remote

import okhttp3.OkHttpClient
import okhttp3.Request

object HtmlDownloader {

    fun download(url: String) : String? {
        try {
            val request = Request.Builder()
                .get()
                .url(url)
                .build()
            val response = OkHttpClient().newCall(request).execute()
            if (response.isSuccessful) {
                return response.body()?.string()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
