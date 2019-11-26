package com.example.navinbangar.sampleweatherapplication.helper


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
}