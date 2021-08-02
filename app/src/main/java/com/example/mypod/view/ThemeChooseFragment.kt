package com.example.mypod.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat.animate
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

        ObjectAnimator.ofFloat(choose_theme_text, "translationY", -100f).apply {
            duration = 3000
            start()
        }

        change_visibility.animate().x(150f).y(300f)

        change_visibility.setOnClickListener {
            animate(change_visibility).x(-40f)

            if (group_visibility.visibility != GONE) {
                group_visibility.visibility = GONE
                Toast.makeText(context, "button is clicked", Toast.LENGTH_SHORT).show()
            } else {
                group_visibility.visibility = VISIBLE
                Toast.makeText(context, "button is clicked again", Toast.LENGTH_SHORT).show()
            }
        }

        circularButton.setOnClickListener {
            animate(it).rotation(180f)
            activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container, PODFragment())
                ?.addToBackStack(null)
                ?.commit()
        }

        show_barrier.setOnClickListener {
            show_barrier.insetBottom = 50
            show_barrier.text = getString(R.string.button_becomes_longer)
        }

        myFAB.setOnClickListener {
            ValueAnimator.ofFloat(0f, 180f).apply {
                duration = 1500
                addUpdateListener { animation ->
                    mars_theme_button.rotation = animation.animatedValue as Float
                    show_barrier.translationX = animation.animatedValue as Float
                }
                repeatCount = 3
            }.start()
            ObjectAnimator.ofFloat(myFAB, "rotation", 0f, 225f).start()
        }

        enlargeScreen.setOnClickListener {
            animate(it).rotation(270f).withEndAction {
                Thread.sleep(1500)
                startActivity(Intent(context, AnimationActivity::class.java))
            }
    }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getMainFragment(theme: Int) {
        activity?.setTheme(theme)
        activity?.startActivity(Intent(context,MainActivity::class.java).putExtra("1",theme))
//        activity
//            ?.supportFragmentManager
//            ?.beginTransaction()
//            ?.replace(R.id.container, ThemeChooseFragment())
//            ?.commit()
    }

    companion object {
        fun newInstance() = ThemeChooseFragment()
    }
}