package com.example.lampatask

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ContentRepository {


    private val logging = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG)
            setLevel(HttpLoggingInterceptor.Level.BODY)
        else
            setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    private val client = OkHttpClient.Builder()
    .addInterceptor(logging)
    .build()

    private val service =
        Retrofit.Builder()
            .baseUrl("http://188.40.167.45:3001")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(Api::class.java)


    suspend fun loadData(): List<Content> {
        val result = ArrayList<Content>()
        var page = 1
        while (true) {
            val list = service.loadData(page).execute().body()!!
            if (list.isEmpty()) {
                break
            } else {
                result.addAll(list)
                page++
            }
        }


        return result
    }

}