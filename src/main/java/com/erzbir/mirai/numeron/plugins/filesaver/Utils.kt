package com.erzbir.mirai.numeron.plugins.filesaver

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream

object NetUtils {
    private val client = OkHttpClient()
    fun downloadTo(url: String, location: File) {
        val request = Request.Builder().get().url(url).build()
        val resp = client.newCall(request).execute()
        val inputStream = resp.body!!.byteStream()
        val fos = FileOutputStream(location)
        fos.write(inputStream.readAllBytes())
        fos.flush()
        fos.close()
    }
}