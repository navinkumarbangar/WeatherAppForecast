package com.example.navinbangar.sampleweatherapplication.helper

import android.view.View
import android.view.animation.AnimationUtils


/**
 * Created by Navin Bangar on 11/25/2019.
 */

object Utils {

    // Converts kelvin to celcius
    fun convertKelvinToCelcius(kelvin: Double): Double {
        return (kelvin - 273.15).round()
    }
    //Round double  value to 2 desimal place
    private fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()

    fun viewVisibleAnimator(view: View) {
        val inAnimation = AnimationUtils.loadAnimation(view.context, android.R.anim.fade_in)
        view.startAnimation(inAnimation)
        view.visibility = View.VISIBLE
    }
}