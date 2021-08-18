package com.example.mypod.model.mars

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarsAPI {

    @GET("sol=1000&camera=fhaz")
//    @GET("EPIC/api/natural/images")
    fun getMars(@Query("api_key") apiKey: String): Call<MarsServerResponseData>
}