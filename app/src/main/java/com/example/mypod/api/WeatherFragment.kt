package com.example.mypod.api

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import com.example.mypod.R
import com.example.mypod.model.mars.MarsData
import com.example.mypod.model.weather.WeatherData
import com.example.mypod.viewmodel.MarsViewModel
import com.example.mypod.viewmodel.WeatherViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_earth.*
import kotlinx.android.synthetic.main.fragment_weather.*

class WeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by lazy {
        ViewModelProviders.of(this).get(WeatherViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getWeatherData()
            .observe(viewLifecycleOwner, {renderData(it)})

    }
    private fun renderData(data: WeatherData) {
        when (data) {

            is WeatherData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.image
                if (url.isNullOrEmpty()) {
                    //showError("Сообщение, что ссылка пустая")
                    toast("Link is empty")
                } else {
                    //showSuccess()
                    image_view_weather.load(url) {
                        lifecycle(this@WeatherFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                }
            }
            is WeatherData.Loading -> {
                //showLoading()
            }
            is WeatherData.Error -> {
                //showError(data.error.message)
                toast(data.error.message)
            }
        }
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
            Snackbar.make(image_view_earth,"error", Snackbar.LENGTH_SHORT).show()
        }
    }




}