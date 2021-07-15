package com.example.mypod.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mypod.R
import kotlinx.android.synthetic.main.theme_choose_fragment.*

class ThemeChooseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.theme_choose_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cosmos_theme_button.setOnClickListener {
            activity?.setTheme(R.style.CosmosTheme)
            getMainFragment()
        }
        moon_theme_button.setOnClickListener {
            activity?.setTheme(R.style.MoonTHeme)
            getMainFragment()
        }
        mars_theme_button.setOnClickListener {
            activity?.setTheme(R.style.MarsTheme)
            getMainFragment()
        }
    }

    private fun getMainFragment (){
        activity
            ?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.container, PictureOfTheDayFragment())
            ?.addToBackStack(null)
            ?.commit()
    }

    companion object {
        fun newInstance() = ThemeChooseFragment()
    }
}