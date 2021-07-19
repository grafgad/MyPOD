package com.example.mypod.api

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import com.example.mypod.R
import com.example.mypod.model.EarthData
import com.example.mypod.model.PictureOfTheDayData
import com.example.mypod.viewmodel.EarthViewModel
import com.example.mypod.viewmodel.PictureOfTheDayViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_earth.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.image_view

class EarthFragment : Fragment() {

    private val viewModel: EarthViewModel by lazy {
        ViewModelProviders.of(this).get(EarthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_earth, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getEarthData()
            .observe(viewLifecycleOwner, {renderData(it)})

    }
    private fun renderData(data: EarthData) {
        when (data) {

            is EarthData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.image
                if (url.isNullOrEmpty()) {
                    //showError("Сообщение, что ссылка пустая")
                    toast("Link is empty")
                } else {
                    //showSuccess()
                    image_view_earth.load(url) {
                        lifecycle(this@EarthFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                }
            }
            is EarthData.Loading -> {
                //showLoading()
            }
            is EarthData.Error -> {
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
         //срабатывает на любое нажатие
    }

}