package com.example.mypod.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mypod.BuildConfig
import com.example.mypod.model.POD.PODButtonData
import com.example.mypod.model.POD.PODButtonRetrofitImpl
import com.example.mypod.model.POD.PODServerResponseData
import com.example.mypod.model.POD.PictureOfTheDayData
import com.example.mypod.view.PODButtonFragment
import com.example.mypod.view.PODFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PODButtonViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PODButtonData> = MutableLiveData(),
    private val retrofitImpl: PODButtonRetrofitImpl = PODButtonRetrofitImpl()
) :
    ViewModel() {
    var date: String = PODButtonFragment().dateButton

    fun getData(): LiveData<PODButtonData> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

//    fun getDate(): LiveData<PODButtonData> {
//        sendServerRequest()
//        var datevm = PODFragment().datef
//        return datevm
//    }

    //    lateinit var datevm : String
    private fun sendServerRequest() {

        liveDataForViewToObserve.value = PODButtonData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PictureOfTheDayData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPODButton(apiKey, date).enqueue(object :
                Callback<PODServerResponseData> {
                override fun onResponse(
                    call: Call<PODServerResponseData>,
                    response: Response<PODServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                            PODButtonData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                PODButtonData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                PODButtonData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                    liveDataForViewToObserve.value = PODButtonData.Error(t)
                }
            })
        }
    }
}
