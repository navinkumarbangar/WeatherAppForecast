package com.example.navinbangar.sampleweatherapplication.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.navinbangar.sampleweatherapplication.CustomApplication
import com.example.navinbangar.sampleweatherapplication.R
import com.example.navinbangar.sampleweatherapplication.di.factory.ViewModelFactory
import com.example.navinbangar.sampleweatherapplication.helper.hideKeyboard
import com.example.navinbangar.sampleweatherapplication.helper.makeGone
import com.example.navinbangar.sampleweatherapplication.helper.viewVisibleAnimator
import com.example.navinbangar.sampleweatherapplication.model.WeatherCurrentDetail
import com.example.navinbangar.sampleweatherapplication.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * Created by Navin Bangar on 11/25/2019.
 * This is activity class responsible for showing  weather details to user
 */
class WeatherActivity : AppCompatActivity() {
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpDagger()
        setUpViewModel()

        //Setting up button click listeners
        setUpGetCurrentWeatherBtnClickListener()
        setUpHourlyForecastBtnListener()
        setUpSixteenDaysForecastBtnListener()
        setUpCloseBtnClickListener()

        //subscribe live data for ui update if any change occure in data
        subscribeCurrentWeatherLiveData()
        subscribeHourlyForeCastLiveData()
        subscribeSixteenDaysForeCastLiveData()
    }

    private fun setUpDagger() {
        (application as CustomApplication).getNetworkComponentFn().inject(this)
    }

    private fun setUpViewModel() {
        weatherViewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel::class.java)
    }


    private fun setUpGetCurrentWeatherBtnClickListener() {
        btnGetCurrentWeather.setOnClickListener {
            if (isValidInput()) {
                btnGetCurrentWeather.hideKeyboard(btnGetCurrentWeather.context)
                cvCurrentWeathreDetails.viewVisibleAnimator()
                barChartForecast.makeGone()

                weatherViewModel.cityName = etCityName.text.toString()
                weatherViewModel.getCurrentWeatherDetails()
            } else {
                displayAlertDialog()
            }
        }
    }

    private fun subscribeCurrentWeatherLiveData() {
        weatherViewModel.getCurrentWeatherLiveData().observe(this, Observer { weatherDetailObj ->
            updateCurrentWeatherDetail(weatherDetailObj)
        })
    }

    private fun updateCurrentWeatherDetail(weatherDetailObj: WeatherCurrentDetail?) {
        val currentWeatherDetails = weatherViewModel.getCurrentWeatherDetailText(weatherDetailObj)
        tvCurrentWeatherDetails.text = currentWeatherDetails
    }

    private fun setUpSixteenDaysForecastBtnListener() {
        btnShowSixteenDaysForcast.setOnClickListener {
            btnShowSixteenDaysForcast.hideKeyboard(btnShowSixteenDaysForcast.context)
            if (isValidInput()) {
                barChartForecast.viewVisibleAnimator()
                cvCurrentWeathreDetails.makeGone()

                weatherViewModel.cityName = etCityName.text.toString()
                weatherViewModel.getSixteenDaysForeCastWeatherDetails()
            } else {
                displayAlertDialog()
            }

        }
    }

    private fun subscribeSixteenDaysForeCastLiveData() {
        weatherViewModel.getSixteenDaysForeCastWeatherLiveData().observe(this, Observer { weatherDetailHourlyObj ->
            val weatherHoursList = weatherViewModel.getSixteenDaysForeCastHours(weatherDetailHourlyObj?.list)
            val temperatureList = weatherViewModel.getSixteenDaysForeCastTemprature(weatherDetailHourlyObj?.list)
            updateWeatherForeCastDetails(weatherHoursList, temperatureList)
        })
    }

    private fun setUpHourlyForecastBtnListener() {
        btnShowHourlyForcast.setOnClickListener {
            btnShowHourlyForcast.hideKeyboard(btnShowHourlyForcast.context)
            if (isValidInput()) {
                cvCurrentWeathreDetails.visibility = View.GONE
                barChartForecast.visibility = View.VISIBLE

                weatherViewModel.cityName = etCityName.text.toString()
                weatherViewModel.getHourlyWeatherForeCastDetail()
            } else {
                displayAlertDialog()
            }

        }
    }

    private fun subscribeHourlyForeCastLiveData() {
        weatherViewModel.getHourlyWeatherForeCastLiveData().observe(this, Observer { weatherDetailHourlyObj ->
            val weatherHoursList = weatherViewModel.getHourlyForeCastHours(weatherDetailHourlyObj?.list)
            val temperatureList = weatherViewModel.getHourlyForeCastTemprature(weatherDetailHourlyObj?.list)
            updateWeatherForeCastDetails(weatherHoursList, temperatureList)
        })
    }


    private fun updateWeatherForeCastDetails(weatherHoursList: List<String>, tempratureList: List<String>) {
        val barData = weatherViewModel.getBarGraphData(weatherHoursList, tempratureList)
        barChartForecast.data = barData
        barChartForecast.setDescription(getString(R.string.weather_forecast_text))
        barChartForecast.animateY(500)
    }

    private fun setUpCloseBtnClickListener() {
        btnCloseApp.setOnClickListener {
            finishAffinity()
        }
    }

    private fun displayAlertDialog() {
        val builder = AlertDialog.Builder(this@WeatherActivity)
        builder.setTitle(getString(R.string.alert_dialog_title))
        builder.setMessage(getString(R.string.alert_dialog_message))
        builder.setPositiveButton(getString(R.string.alert_dialog_positive_btn_text)) { dialog, _ ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun isValidInput(): Boolean {
        return etCityName.text.isNotEmpty()
    }
}
