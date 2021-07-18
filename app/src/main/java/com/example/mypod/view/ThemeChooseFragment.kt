package com.example.mypod.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.mypod.R
import kotlinx.android.synthetic.main.fragment_theme_choose.*

class ThemeChooseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_theme_choose, container, false)

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mars_theme_button.setOnClickListener {
            getMainFragment(R.style.MarsTheme)
        }
        moon_theme_button.setOnClickListener {
            getMainFragment(R.style.MoonTHeme)
        }
        cosmos_theme_button.setOnClickListener {
            getMainFragment(R.style.CosmosTheme)
        }
        default_theme_button.setOnClickListener {
            getMainFragment(R.style.AppTheme)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getMainFragment (theme: Int){
        activity?.setTheme(theme)
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