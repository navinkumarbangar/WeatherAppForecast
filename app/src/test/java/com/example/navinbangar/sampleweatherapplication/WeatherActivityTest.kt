package com.example.navinbangar.sampleweatherapplication

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.widget.Button
import android.widget.EditText
import com.example.navinbangar.sampleweatherapplication.R.id.*
import com.example.navinbangar.sampleweatherapplication.api.Repository
import com.example.navinbangar.sampleweatherapplication.api.WeatherServiceApiInterface
import com.example.navinbangar.sampleweatherapplication.model.WeatherCurrentDetail
import com.example.navinbangar.sampleweatherapplication.model.WeatherForeCast
import com.example.navinbangar.sampleweatherapplication.view.WeatherActivity
import com.example.navinbangar.sampleweatherapplication.viewmodel.WeatherViewModel
import org.junit.Assert
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.MockitoAnnotations


/**
 * Created by Navin Bangar on 11/26/2019.
 */

@RunWith(JUnit4::class)
class WeatherActivityTest {
    val weatherActivity = spy(WeatherActivity())
    private val weatherService = mock(WeatherServiceApiInterface::class.java)
    private val repository = Repository(weatherService)
    private val viewModelFactoryTest = mock(ViewModelProvider.Factory::class.java)

    private var weatherViewModelTest = WeatherViewModel(repository)
    @Mock
    private val etCityName: EditText? = null
    @Mock
    private val btnGetCurrentWeather: Button? = null

    private lateinit var weatherForeCast: WeatherForeCast

    private lateinit var currentWeatherForeCast: WeatherCurrentDetail

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        weatherActivity.viewModelFactory = createViewModelFactory(weatherViewModelTest)
        weatherForeCast = mock(WeatherForeCast::class.java)
        currentWeatherForeCast = mock(WeatherCurrentDetail::class.java)
    }

    @Test
    fun testViewsAreNotNull() {
        assertNotNull(etCityName)
        assertNotNull(btnGetCurrentWeather)
        assertNotNull(btnShowSixteenDaysForcast)
        assertNotNull(barChartForecast)
        assertNotNull(btnCloseApp)
        assertNotNull(cvCurrentWeathreDetails)

    }

    @Test
    fun isLiveDataEmittingWeatherForecastDetail() {
        weatherViewModelTest.getCurrentWeatherLiveData().postValue(currentWeatherForeCast)
        weatherViewModelTest.getSixteenDaysForeCastWeatherLiveData().postValue(weatherForeCast)

        Assert.assertNotEquals(weatherViewModelTest.getCurrentWeatherLiveData().value, currentWeatherForeCast)
        Assert.assertNotEquals(weatherViewModelTest.getSixteenDaysForeCastWeatherLiveData().value, weatherForeCast)

    }

    @Test
    fun isLiveDataEmittingCorrectCurrentWeatherDetails() {
        val currentDetail = repository.getCurrentWeatherData(weatherViewModelTest.getCurrentWeatherLiveData(), "Rome")
        weatherViewModelTest.getCurrentWeatherLiveData().postValue(currentDetail.value)

        Assert.assertEquals(weatherViewModelTest.getCurrentWeatherLiveData().value, currentDetail.value)
    }

    @Test
    fun isLiveDataEmittingCorrectSixteenDaysForecastWeatherDetails() {
        val sixteeDaysForeCastDetails = repository.getSixteenDaysForecastData(weatherViewModelTest.getSixteenDaysForeCastWeatherLiveData(), "Rome")
        weatherViewModelTest.getSixteenDaysForeCastWeatherLiveData().postValue(sixteeDaysForeCastDetails.value)

        Assert.assertEquals(weatherViewModelTest.getSixteenDaysForeCastWeatherLiveData().value, sixteeDaysForeCastDetails.value)
    }

    @Test
    fun testweatherViewModelTestNotNull() {
        assertNotNull(viewModelFactoryTest)
    }

    @Test
    @Throws(Exception::class)
    fun testGetViewModelFactory() {
        val result = weatherActivity.viewModelFactory
        Assert.assertNotEquals(createViewModelFactory(weatherViewModelTest), result)
    }

    @Test
    @Throws(Exception::class)
    fun testSetViewModelFactory() {
        weatherActivity.viewModelFactory = createViewModelFactory(weatherViewModelTest)
        Assert.assertNotNull(createViewModelFactory(weatherViewModelTest))
    }

    private fun <T : ViewModel> createViewModelFactory(viewModel: T): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(viewModelClass: Class<T>): T {
                if (viewModelClass.isAssignableFrom(viewModel.javaClass)) {
                    @Suppress("UNCHECKED_CAST")
                    return viewModel as T
                }
                throw IllegalArgumentException("Unknown view model class $viewModelClass")
            }
        }
    }
}