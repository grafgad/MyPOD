package com.example.mypod.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import com.example.mypod.R
import kotlinx.android.synthetic.main.theme_choose_fragment.*

class ThemeChooseFragment : Fragment() {

    private val themedContext = ContextThemeWrapper(
        context,//контекст вашей Активити
        R.style.IndigoTheme //ваш стиль из ресурсов
    )


    //любой вью, который вы хотите создать через конструктор или надуть через Inflator
 //   val view = MyCustomView(themedContext)
//    val otherView = LayoutInflater.from(themedContext).inflate(R.layout.main_fragment, container, false)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.theme_choose_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cosmos_theme_button.setOnClickListener(){
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container, PictureOfTheDayFragment())?.addToBackStack(null)?.commit()
        }
        moon_theme_button.setOnClickListener(){
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container, PictureOfTheDayFragment())?.addToBackStack(null)?.commit()
        }
        mars_theme_button.setOnClickListener(){
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container, PictureOfTheDayFragment())?.addToBackStack(null)?.commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



//    private fun somefun(){
//        R.id.moon_theme_button
//        cosmos_theme_button.setOnClickListener(){
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, ThemeChooseFragment.newInstance())
////                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
//                .commitNow()
//        }
//    }

    companion object {
        fun newInstance() = ThemeChooseFragment()
        private var isMain = true
    }
}