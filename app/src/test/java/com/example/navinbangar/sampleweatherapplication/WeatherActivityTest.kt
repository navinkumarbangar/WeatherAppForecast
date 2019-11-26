package com.example.navinbangar.sampleweatherapplication

import com.example.navinbangar.sampleweatherapplication.R.id.barChartForecast
import com.example.navinbangar.sampleweatherapplication.R.id.cvCurrentWeathreDetails
import org.hamcrest.core.IsNull.notNullValue
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations


/**
 * Created by Navin Bangar on 11/26/2019.
 */

@RunWith(JUnit4::class)
class WeatherActivityTest {

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

}