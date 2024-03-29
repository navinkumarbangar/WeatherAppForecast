package com.example.navinbangar.sampleweatherapplication.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.navinbangar.sampleweatherapplication.api.Repository
import com.example.navinbangar.sampleweatherapplication.helper.Utils.convertKelvinToCelcius
import com.example.navinbangar.sampleweatherapplication.model.*
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


/**
 * Created by Navin Bangar on 11/19/2019.
 * This is view model class responsible for communicating with repository
 * and updates UI
 */

class WeatherViewModel @Inject constructor(val weatherRepo: Repository) : ViewModel() {
    private val hourlyWeatherForecastDetailLiveData = MutableLiveData<WeatherDetailHourly?>()
    private val currentWeatherForecastDetailLiveData = MutableLiveData<WeatherCurrentDetail?>()
    private val sixteenDaysWeatherForecastDetailLiveData = MutableLiveData<WeatherForeCast?>()
    var cityName: String = ""

    fun getCurrentWeatherDetails(): MutableLiveData<WeatherCurrentDetail?> {
        return weatherRepo.getCurrentWeatherData(currentWeatherForecastDetailLiveData, cityName)
    }

    fun getCurrentWeatherLiveData(): MutableLiveData<WeatherCurrentDetail?> {
        return currentWeatherForecastDetailLiveData
    }
    fun getHourlyWeatherForeCastDetail(): MutableLiveData<WeatherDetailHourly?> {
        return weatherRepo.getHourlyForecastData(hourlyWeatherForecastDetailLiveData, cityName)
    }

    fun getHourlyWeatherForeCastLiveData(): MutableLiveData<WeatherDetailHourly?> {
        return hourlyWeatherForecastDetailLiveData
    }

    fun getSixteenDaysForeCastWeatherDetails(): MutableLiveData<WeatherForeCast?> {
        return weatherRepo.getSixteenDaysForecastData(sixteenDaysWeatherForecastDetailLiveData, cityName)
    }

    fun getSixteenDaysForeCastWeatherLiveData(): MutableLiveData<WeatherForeCast?> {
        return sixteenDaysWeatherForecastDetailLiveData
    }


    fun getHourlyForeCastHours(weatherDetailList: List<ListItem>?): List<String> {
        val hoursList = ArrayList<String>()
        weatherDetailList?.forEach {
            val hours = getHoursFromDateString(it.dtTxt)
            hoursList.add(hours)
        }
        return hoursList.take(16)
    }

    fun getSixteenDaysForeCastHours(weatherDetailList: List<WeatherDetailsObj>?): List<String> {
        val hoursList = ArrayList<String>()
        weatherDetailList?.forEach {
            val hours = getHoursFromDateString(it.dtTxt)
            hoursList.add(hours)
        }
        return hoursList.take(16)
    }

    private fun getHoursFromDateString(dateString: String): String {
        var hour = ""
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        try {
            val date = format.parse(dateString)
            val time = SimpleDateFormat("hh", Locale.US)
            hour = time.format(date)

        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return hour
    }

    fun getHourlyForeCastTemprature(weatherDetailList: List<ListItem>?): List<String> {
        val tempratureList = ArrayList<String>()
        weatherDetailList?.forEach {
            val weatherObjListObj = it.tempratureObj
            tempratureList.add(convertKelvinToCelcius(weatherObjListObj.temp).toString())
        }
        return tempratureList.take(16)
    }


    fun getSixteenDaysForeCastTemprature(weatherDetailList: List<WeatherDetailsObj>?): List<String> {
        val tempratureList = ArrayList<String>()
        weatherDetailList?.forEach {
            val weatherObjListObj = it.main
            tempratureList.add(convertKelvinToCelcius(weatherObjListObj.temp).toString())
        }
        return tempratureList.take(16)
    }

    //create bar data object
    fun getBarGraphData(weatherHoursList: List<String>, tempratureList: List<String>): BarData {
        val entries = ArrayList<BarEntry>()
        tempratureList.forEachIndexed { index, values ->
            entries.add(BarEntry(values.toFloat(), index))
        }
        val barDataSet = BarDataSet(entries, " Weather Forecast")
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS)
        return BarData(weatherHoursList, barDataSet)
    }

    fun getCurrentWeatherDetailText(weatherDetailObj: WeatherCurrentDetail?): String {
        return "Country: " +
                weatherDetailObj?.sys?.country +
                "\n" +
                "Temperature: " +
                weatherDetailObj?.main?.temp?.let { convertKelvinToCelcius(it) } + "°C" +
                "\n" +
                "Temperature(Min): " +
                weatherDetailObj?.main?.tempMin?.let { convertKelvinToCelcius(it) } + "°C" +
                "\n" +
                "Temperature(Max): " +
                weatherDetailObj?.main?.tempMax?.let { convertKelvinToCelcius(it) } + "°C" +
                "\n" +
                "Humidity: " +
                weatherDetailObj?.main?.humidity +
                "\n" +
                "Pressure: " +
                weatherDetailObj?.main?.pressure
    }


}