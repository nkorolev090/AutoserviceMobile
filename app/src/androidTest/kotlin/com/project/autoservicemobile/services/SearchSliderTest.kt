package com.project.autoservicemobile.services

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.project.autoservicemobile.MainActivity
import com.project.autoservicemobile.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class SearchSliderTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        onView(withId(R.id.navigation_services)).perform(ViewActions.click())
    }
    @Test
    fun sliderOnStartTest() {
        onView(withId(R.id.productsContainer)).check(matches(isDisplayed()))
    }
    @Test
    fun sliderClickTest() {
        onView(withId(R.id.searchSlider)).perform(ViewActions.click())
        onView(withId(R.id.servicesContainer)).check(matches(isDisplayed()))
    }

    @Test
    fun sliderDoubleClickTest() {
        onView(withId(R.id.searchSlider)).perform(ViewActions.click())
        onView(withId(R.id.searchSlider)).perform(ViewActions.click())

        onView(withId(R.id.productsContainer)).check(matches(isDisplayed()))
    }
}