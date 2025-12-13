package com.tech.vexilmachineround.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class AppUtils {

    suspend fun getDirectImgBbUrl(viewerUrl: String): String? =
        withContext(Dispatchers.IO) {
            try {
                val doc = Jsoup.connect(viewerUrl).get()
                // imgbb puts main image in <meta property="og:image" content="https://i.ibb.co/.../file.jpg">
                doc.select("meta[property=og:image]")
                    .firstOrNull()
                    ?.attr("content")
            } catch (e: Exception) {
                null
            }
        }
}