package com.example.navinbangar.sampleweatherapplication

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.navinbangar.sampleweatherapplication.view.WeatherActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Navin Bangar on 11/27/2019.
 */

@RunWith(AndroidJUnit4::class)
class WeatherActivityInstrumentTest {
    @Rule
    @JvmField
    var activityActivityTestRule = ActivityTestRule(WeatherActivity::class.java)

    @Test
    fun getCurrentWeatherDetails() {
        onView(withId(R.id.etCityName)).perform(typeText("Rome"))
        onView(withId(R.id.btnGetCurrentWeather)).perform(click())
        onView(withText("Rome")).check(matches(isDisplayed()))
    }


    @Test
    fun testHintVisibility() {
        // enter name
        onView(withId(R.id.etCityName)).perform(typeText("Rome"), closeSoftKeyboard())
        onView(withId(R.id.etCityName)).check(matches(withText("Rome")))
    }
}