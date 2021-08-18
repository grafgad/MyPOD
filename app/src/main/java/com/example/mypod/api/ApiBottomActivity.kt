package com.example.mypod.api

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.example.mypod.R
import kotlinx.android.synthetic.main.activity_api_bottom.*


class ApiBottomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api_bottom)
        bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_earth -> {
                    TransitionManager.beginDelayedTransition(activity_api_bottom_container, Slide(
                        Gravity.TOP))
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, EarthFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.bottom_view_mars -> {
                    TransitionManager.beginDelayedTransition(activity_api_bottom_container, Slide(
                        Gravity.TOP))
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, MarsFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.bottom_view_weather -> {
                    TransitionManager.beginDelayedTransition(activity_api_bottom_container, Slide(
                        Gravity.TOP))
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, WeatherFragment())
                        .commitAllowingStateLoss()
                    true
                }
                else -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, EarthFragment())
                        .commitAllowingStateLoss()
                    true
                }
            }
        }
        bottom_navigation_view.selectedItemId = R.id.bottom_view_earth
        bottom_navigation_view.getOrCreateBadge(R.id.bottom_view_earth)
        val badge = bottom_navigation_view.getBadge(R.id.bottom_view_earth)
        badge?.maxCharacterCount = 2
        badge?.number = 999

        bottom_navigation_view.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_earth -> {
                    //Item tapped
                }
                R.id.bottom_view_mars -> {
                    //Item tapped
                }
                R.id.bottom_view_weather -> {
                    //Item tapped
                }
            }
        }
    }
}

