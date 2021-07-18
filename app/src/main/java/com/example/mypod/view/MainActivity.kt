package com.example.mypod.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mypod.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        //setTheme(R.style.IndigoTheme)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ThemeChooseFragment.newInstance())
//                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }
}