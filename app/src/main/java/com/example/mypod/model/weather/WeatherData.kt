package com.example.mypod.model.weather

sealed class WeatherData {
    data class Success(val serverResponseData: WeatherServerResponseData) : WeatherData()
    data class Error(val error: Throwable) : WeatherData()
    data class Loading(val progress: Int?) : WeatherData()
}