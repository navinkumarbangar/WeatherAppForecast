package com.example.navinbangar.sampleweatherapplication

import org.junit.runner.RunWith
import com.example.navinbangar.sampleweatherapplication.R.id.cvCurrentWeathreDetails
import org.hamcrest.core.IsNull.notNullValue
import org.junit.Assert.assertThat
import org.junit.Test
import android.support.test.runner.AndroidJUnit4
import com.example.navinbangar.sampleweatherapplication.R.id.barChartForecast
import org.junit.Before
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config


/**
 * Created by Navin Bangar on 11/26/2019.
 */

@RunWith(AndroidJUnit4::class)
class WeatherActivityTest {

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    @Config(manifest= Config.NONE)
    @Throws(Exception::class)
    fun ensureCurrentWeatherDetailsCardViewIsPresent() {
        assertThat(cvCurrentWeathreDetails, notNullValue())
    }

    @Test
    @Config(manifest= Config.NONE)
    @Throws(Exception::class)
    fun ensureBarChartViewIsPresent() {
        assertThat(barChartForecast, notNullValue())
    }

}