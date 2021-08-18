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
import com.example.mypod.viewmodel.MarsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_earth.*
import kotlinx.android.synthetic.main.fragment_mars.*

class MarsFragment : Fragment() {

    private val viewModel: MarsViewModel by lazy {
        ViewModelProviders.of(this).get(MarsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_earth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMarsData()
            .observe(viewLifecycleOwner, {renderData(it)})

    }
    private fun renderData(data: MarsData) {
        when (data) {

            is MarsData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.img_src
                if (url.isNullOrEmpty()) {
                    //showError("Сообщение, что ссылка пустая")
                    toast("Link is empty")
                } else {
                    //showSuccess()
                    image_view_mars.load(url) {
                        lifecycle(this@MarsFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                }
            }
            is MarsData.Loading -> {
                //showLoading()
            }
            is MarsData.Error -> {
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