package com.example.mypod.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mypod.BuildConfig
import com.example.mypod.model.*
import com.example.mypod.model.mars.MarsData
import com.example.mypod.model.mars.MarsRetrofitImpl
import com.example.mypod.model.mars.MarsServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarsViewModel(
    private val liveDataForViewToObserve: MutableLiveData<MarsData> = MutableLiveData(),
    private val retrofitImpl: MarsRetrofitImpl = MarsRetrofitImpl()
) :
    ViewModel() {

    fun getMarsData(): LiveData<MarsData> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    private fun sendServerRequest() {
        liveDataForViewToObserve.value = MarsData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            MarsData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getMarsRetrofitImpl().getMars(apiKey).enqueue(object :
                Callback<MarsServerResponseData> {
                override fun onResponse(
                    call: Call<MarsServerResponseData>,
                    response: Response<MarsServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                            MarsData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                MarsData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                MarsData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<MarsServerResponseData>, t: Throwable) {
                    liveDataForViewToObserve.value = MarsData.Error(t)
                }
            })
        }
    }
}