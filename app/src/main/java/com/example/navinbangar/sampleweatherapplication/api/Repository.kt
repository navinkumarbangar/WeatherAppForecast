package com.example.navinbangar.sampleweatherapplication.api

import android.arch.lifecycle.MutableLiveData
import com.example.navinbangar.sampleweatherapplication.helper.Constants.Companion.ForecastAppId
import com.example.navinbangar.sampleweatherapplication.helper.Constants.Companion.success_code
import com.example.navinbangar.sampleweatherapplication.model.WeatherCurrentDetail
import com.example.navinbangar.sampleweatherapplication.model.WeatherDetailHourly
import com.example.navinbangar.sampleweatherapplication.model.WeatherForeCast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Navin Bangar on 11/19/2019.
 * This class takes care network related implementations and provide separation of network operations
 * from other modules
 */

class Repository(private val webservice: WeatherServiceApiInterface) {
    ///Get current weather details
    fun getCurrentWeatherData(currentWeatherLiveData: MutableLiveData<WeatherCurrentDetail?>, cityName: String, countryName: String = "countryName"): MutableLiveData<WeatherCurrentDetail?> {
        val call = webservice.getCurrentWeatherData("$cityName,$countryName", ForecastAppId)
        call?.enqueue(object : Callback<WeatherCurrentDetail?> {
            override fun onResponse(call: Call<WeatherCurrentDetail?>, response: Response<WeatherCurrentDetail?>) {
                if (response.code() == success_code) {
                    val weatherForecastObj = response.body()
                    currentWeatherLiveData.postValue(weatherForecastObj)
                }
            }

            override fun onFailure(call: Call<WeatherCurrentDetail?>, t: Throwable) {
                currentWeatherLiveData.postValue(null)
            }
        })

        return currentWeatherLiveData
    }


    ///Get weather forecast hourly
    fun getHourlyForecastData(hourlyrForeCastLiveData: MutableLiveData<WeatherDetailHourly?>, cityName: String, countryName: String = "countryName"): MutableLiveData<WeatherDetailHourly?> {
        val call = webservice.getHourlyWeatherData("$cityName,$countryName", ForecastAppId)
        call.enqueue(object : Callback<WeatherDetailHourly> {
            override fun onResponse(call: Call<WeatherDetailHourly>, response: Response<WeatherDetailHourly>) {
                if (response.code() == success_code) {
                    val weatherForecastObj = response.body()
                    hourlyrForeCastLiveData.value = weatherForecastObj
                }
            }

            override fun onFailure(call: Call<WeatherDetailHourly>, t: Throwable) {
                hourlyrForeCastLiveData.value = null
            }
        })

        return hourlyrForeCastLiveData
    }

    //Get weather forecast for 16 days
    fun getSixteenDaysForecastData(sixteenDaysForeCastLiveData: MutableLiveData<WeatherForeCast?>, cityName: String, countryName: String = "countryName"): MutableLiveData<WeatherForeCast?> {
        val call = webservice.getSixteenDaysForecastData("$cityName,$countryName", ForecastAppId, mode, unit, cnt)
        call?.enqueue(object : Callback<WeatherForeCast?> {
            override fun onResponse(call: Call<WeatherForeCast?>, response: Response<WeatherForeCast?>) {
                if (response.code() == success_code) {
                    val weatherForecastObj = response.body()
                    sixteenDaysForeCastLiveData.postValue(weatherForecastObj)
                }
            }

            override fun onFailure(call: Call<WeatherForeCast?>, t: Throwable) {
                sixteenDaysForeCastLiveData.postValue(null)
            }
        })
        return sixteenDaysForeCastLiveData
    }

    companion object {
        const val unit = "metric"
        const val mode = "json"
        const val cnt = "16"
    }
}