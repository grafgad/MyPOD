package com.example.mypod.model.earth

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EarthAPI {

    @GET("EPIC/archive/natural/2019/05/30/png/epic_1b_20190530011359.png")
//    @GET("EPIC/api/natural/images")
    fun getEarth(@Query("api_key") apiKey: String): Call<EarthServerResponseData>
}
