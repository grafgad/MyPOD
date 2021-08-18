package com.example.mypod.model.POD

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PODButtonAPI {

    @GET("planetary/apod")// YYYY-MM-DD
    fun getPODButton(@Query("api_key") apiKey: String, @Query("date") date:String ): Call<PODServerResponseData>
}