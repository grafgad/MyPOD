package com.example.mypod.model.POD

sealed class PODButtonData {
    data class Success(val serverResponseData: PODServerResponseData) : PODButtonData()
    data class Error(val error: Throwable) : PODButtonData()
    data class Loading(val progress: Int?) : PODButtonData()
}