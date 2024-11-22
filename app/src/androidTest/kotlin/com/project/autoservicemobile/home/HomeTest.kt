package com.project.autoservicemobile.home

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
class HomeTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        onView(withId(R.id.navigation_home)).perform(ViewActions.click())
    }

    @Test
    fun homeOnStart() {
        onView(withId(R.id.registrationShimmer)).check(matches(isDisplayed()))
        //onView(withId(R.id.shimmerContainer)).check(matches(isDisplayed()))
    }
}