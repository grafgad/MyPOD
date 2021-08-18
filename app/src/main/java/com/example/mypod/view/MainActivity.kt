package com.example.mypod.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mypod.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val a = intent.extras?.getInt("1")?: 0
        if (a > 0) {
            setTheme(a)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ThemeChooseFragment.newInstance())
//                .replace(R.id.container, PODFragment.newInstance())
                .commitNow()
        }
    }
}