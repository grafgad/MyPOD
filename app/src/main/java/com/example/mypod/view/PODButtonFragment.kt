package com.example.mypod.view

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import com.example.mypod.R
import com.example.mypod.model.POD.PODButtonData
import com.example.mypod.viewmodel.PODButtonViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.image_view
import kotlinx.android.synthetic.main.fragment_podbutton.*
import java.time.LocalDate

class PODButtonFragment : Fragment(R.layout.fragment_podbutton){

    private var isExpanded = false

    var dateButton = LocalDate.now().toString()
//        request_date.text.toString()

    private val viewModel: PODButtonViewModel by lazy {
        ViewModelProviders.of(this).get(PODButtonViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        request_date.text = dateButton
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData()
            .observe(viewLifecycleOwner, {renderData(it)})




        image_view.setOnClickListener {
            isExpanded = !isExpanded
            androidx.transition.TransitionManager.beginDelayedTransition(
                fragment_podbutton, androidx.transition.TransitionSet()
                    .addTransition(androidx.transition.ChangeBounds())
                    .addTransition(androidx.transition.ChangeImageTransform())
            )

            val params: ViewGroup.LayoutParams = image_view.layoutParams
            params.height =
                if (isExpanded) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
            image_view.layoutParams = params
            image_view.scaleType =
                if (isExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
        }
    }



    private fun renderData(data: PODButtonData) {

        when (data) {
            is PODButtonData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                date_of_PODControl.text = serverResponseData.date
                if (url.isNullOrEmpty()) {
                    //showError("Сообщение, что ссылка пустая")
                    toast("Link is empty")
                } else {
                    //showSuccess()
                    image_view.load(url) {
                        lifecycle(this@PODButtonFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                }
            }
            is PODButtonData.Loading -> {
                //showLoading()
            }
            is PODButtonData.Error -> {
                //showError(data.error.message)
                toast(data.error.message)
            }
        }
    }


        private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
            Snackbar.make(image_view,"error", Snackbar.LENGTH_SHORT).show()
        }
    }



}