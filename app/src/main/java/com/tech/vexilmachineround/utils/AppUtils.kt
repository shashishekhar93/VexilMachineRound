package com.tech.vexilmachineround.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.tech.vexilmachineround.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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


    object ImageUtils {

        fun loadIbbCoImage(
            context: Context,
            imageView: ImageView,
            galleryUrl: String
        ) {
            Log.d("ImageUtils", "Starting load for: $galleryUrl")

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // Test connectivity first
                    if (!isNetworkAvailable(context)) {
                        Log.e("ImageUtils", "No internet connection")
                        withContext(Dispatchers.Main) {
                            imageView.setImageResource(android.R.drawable.ic_dialog_alert)
                        }
                        return@launch
                    }

                    Log.d("ImageUtils", "Fetching HTML...")
                    val doc = Jsoup.connect(galleryUrl)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                        .timeout(30000)  // Increased timeout
                        .followRedirects(true)
                        .get()

                    Log.d("ImageUtils", "Page loaded successfully. Title: ${doc.title()}")

                    // Multiple selectors for ibb.co
                    val img1 = doc.select("img[src*='i.ibb.co']").firstOrNull()?.attr("src")
                    val img2 = doc.select("meta[property=og:image]").attr("content")
                    val img3 = doc.select("img").attr("src").takeIf { it.contains("i.ibb.co") }

                    Log.d("ImageUtils", "Img1: $img1 | Img2: $img2 | Img3: $img3")

                    val directUrl = img1 ?: img2

                    withContext(Dispatchers.Main) {
                        if (!directUrl.isNullOrEmpty()) {
                            Log.d("ImageUtils", "Loading: $directUrl")
                            Glide.with(context)
                                .load(directUrl)
                                .placeholder(android.R.drawable.ic_menu_gallery)
                                .error(android.R.drawable.ic_delete)
                                .timeout(30000)
                                .into(imageView)
                        } else {
                            Log.e("ImageUtils", "No image URL found")
                            imageView.setImageResource(android.R.drawable.ic_menu_report_image)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("ImageUtils", "Full error: ${e.message}", e)
                    withContext(Dispatchers.Main) {
                        imageView.setImageResource(android.R.drawable.ic_dialog_alert)
                    }
                }
            }
        }

        private fun isNetworkAvailable(context: Context): Boolean {
            return try {
                val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val network = connectivityManager.activeNetwork ?: return false
                val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            } catch (e: Exception) {
                false
            }
        }
    }


    /*   object ImageUtils {

           fun loadIbbCoImage(
               context: Context,
               imageView: ImageView,
               galleryUrl: String
           ) {
               Log.d("ImageUtils", "Starting load for: $galleryUrl")

               CoroutineScope(Dispatchers.IO).launch {
                   try {
                       Log.d("ImageUtils", "Fetching HTML...")
                       val doc = Jsoup.connect(galleryUrl)
                           .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                           .timeout(20000)
                           .get()

                       Log.d("ImageUtils", "HTML title: ${doc.title()}")

                       // Try multiple selectors
                       val img1 = doc.select("img[src*='i.ibb.co']").firstOrNull()?.attr("src")
                       val img2 = doc.select("meta[property=og:image]").attr("content")
                       val img3 = doc.select("img.main-image, img.image").firstOrNull()?.attr("src")

                       Log.d("ImageUtils", "Img1 (i.ibb.co): $img1")
                       Log.d("ImageUtils", "Img2 (og:image): $img2")
                       Log.d("ImageUtils", "Img3 (main): $img3")

                       val directUrl = img1 ?: img2

                       withContext(Dispatchers.Main) {
                           if (directUrl.isNotEmpty()) {
                               Log.d("ImageUtils", "Loading direct URL: $directUrl")
                               Glide.with(context)
                                   .load(directUrl)
                                   .placeholder(android.R.drawable.ic_menu_gallery)
                                   .error(android.R.drawable.ic_delete)
                                   .into(imageView)
                           } else {
                               Log.e("ImageUtils", "NO IMAGE URL FOUND!")
                               imageView.setImageResource(android.R.drawable.ic_menu_report_image)
                           }
                       }
                   } catch (e: Exception) {
                       Log.e("ImageUtils", "ERROR: ${e.message}", e)
                       Log.e("ImageUtils", "Stacktrace: ${e.stackTraceToString()}")
                       withContext(Dispatchers.Main) {
                           imageView.setImageResource(android.R.drawable.ic_dialog_alert)
                       }
                   }
               }
           }
       }*/



    /* object ImageUtils {
         fun loadIbbCoImage(
             context: Context,
             imageView: ImageView,
             galleryUrl: String,
             shimmerLayoutId: Int? = null,
             placeholderRes: Int? = null,
             errorRes: Int? = null
         ) {
             (context as? FragmentActivity)?.lifecycleScope?.launch(Dispatchers.IO){}
                 ?: CoroutineScope(Dispatchers.IO).launch {
                     try {
                         // Parse ibb.co page to extract direct image
                         val doc = Jsoup.connect(galleryUrl)
                             .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                             .timeout(15000)
                             .get()

                         val directUrl =
                             doc.select("img[src*='i.ibb.co']").firstOrNull()?.attr("src")
                                 ?: doc.select("meta[property=og:image]").attr("content")

                         Log.d("ImageUtils", "Direct URL found: $directUrl")

                         withContext(Dispatchers.Main) {
                             if (directUrl.isNotEmpty()) {
                                 Glide.with(context)
                                     .load(directUrl)
                                     .placeholder(placeholderRes ?: R.drawable.ic_placeholder)
                                     .error(errorRes ?: R.drawable.ic_error)
                                     .listener(object : RequestListener<Drawable> {

                                         override fun onLoadFailed(
                                             e: GlideException?,
                                             model: Any?,
                                             target: com.bumptech.glide.request.target.Target<Drawable?>?,
                                             isFirstResource: Boolean
                                         ): Boolean {
                                             return false
                                         }

                                         override fun onResourceReady(
                                             resource: Drawable?,
                                             model: Any?,
                                             target: com.bumptech.glide.request.target.Target<Drawable?>?,
                                             dataSource: DataSource?,
                                             isFirstResource: Boolean
                                         ): Boolean {
                                             return false
                                         }
                                     })
                                     .into(imageView)
                             } else {
                                 imageView.setImageResource(errorRes ?: R.drawable.ic_error)
                             }
                         }
                     } catch (e: Exception) {
                         Log.e("ImageUtils", "Load failed: ${e.message}", e)
                         withContext(Dispatchers.Main) {
                             imageView.setImageResource(errorRes ?: R.drawable.ic_error)
                         }
                     }
                 }
         }
     }*/

}