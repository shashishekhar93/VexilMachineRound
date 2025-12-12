package com.tech.vexilmachineround.repository

import android.content.Context
import com.google.gson.Gson
import com.tech.vexilmachineround.model.ResponseObject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JsonRepository @Inject constructor(@ApplicationContext private val context: Context) {

    @Volatile
    private var cacheResponse: ResponseObject? = null
    private val gson= Gson()

    suspend fun getSampleData(): ResponseObject{
        return cacheResponse ?: loadJsonFromAssets().also {
            cacheResponse = it
        }
    }


    private suspend fun loadJsonFromAssets(): ResponseObject{
        return withContext(Dispatchers.IO){
            try {
                context.assets.open("sampledata.json").use {inputStream ->
                    val jsonString = inputStream.bufferedReader().use {
                        it.readText()
                    }
                    gson.fromJson(jsonString, ResponseObject::class.java)
                }
            }catch (e: Exception){
                throw RuntimeException("Runtime exception :- "+e.message)
            }
        }
    }
}