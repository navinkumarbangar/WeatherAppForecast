package com.example.navinbangar.sampleweatherapplication

import com.example.navinbangar.sampleweatherapplication.R.id.barChartForecast
import com.example.navinbangar.sampleweatherapplication.R.id.cvCurrentWeathreDetails
import com.example.navinbangar.sampleweatherapplication.di.factory.ViewModelFactory
import com.example.navinbangar.sampleweatherapplication.view.WeatherActivity
import org.hamcrest.core.IsNull.notNullValue
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


/**
 * Created by Navin Bangar on 11/26/2019.
 */

@RunWith(JUnit4::class)
class WeatherActivityTest {
    private val viewModelFactory = mock(ViewModelFactory::class.java)
    val weatherActivity = spy(WeatherActivity())

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun ensureCurrentWeatherDetailsCardViewIsPresent() {
        assertThat(cvCurrentWeathreDetails, notNullValue())
    }

    @Test
    fun ensureBarChartViewIsPresent() {
        assertThat(barChartForecast, notNullValue())
    }


    @Test
    @Throws(Exception::class)
    fun testOnCreate() {
        doNothing().`when`(weatherActivity).setContentView(R.layout.activity_main)
    }

    @Test
    @Throws(Exception::class)
    fun testButtonGetCurrentWeatherClickListeners() {
        doNothing().`when`(weatherActivity).setContentView(R.layout.activity_main)
    }
}