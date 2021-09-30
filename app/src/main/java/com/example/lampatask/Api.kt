package com.example.lampatask

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("/")
    fun loadData(@Query("page") int: Int): Call<List<Content>>

}