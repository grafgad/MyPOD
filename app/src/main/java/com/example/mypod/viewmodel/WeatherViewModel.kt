package com.example.mypod.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mypod.BuildConfig
import com.example.mypod.model.*
import com.example.mypod.model.weather.WeatherData
import com.example.mypod.model.weather.WeatherRetrofitImpl
import com.example.mypod.model.weather.WeatherServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel(
    private val liveDataForViewToObserve: MutableLiveData<WeatherData> = MutableLiveData(),
    private val retrofitImpl: WeatherRetrofitImpl = WeatherRetrofitImpl()
) :
    ViewModel() {

    fun getWeatherData(): LiveData<WeatherData> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    private fun sendServerRequest() {
        liveDataForViewToObserve.value = WeatherData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            WeatherData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getWeatherRetrofitImpl().getWeather(apiKey).enqueue(object :
                Callback<WeatherServerResponseData> {
                override fun onResponse(
                    call: Call<WeatherServerResponseData>,
                    response: Response<WeatherServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                            WeatherData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                WeatherData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                WeatherData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<WeatherServerResponseData>, t: Throwable) {
                    liveDataForViewToObserve.value = WeatherData.Error(t)
                }
            })
        }
    }
}