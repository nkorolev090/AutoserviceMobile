package com.project.autoservicemobile.profile

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
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
class ProfileTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        onView(withId(R.id.profileFragment)).perform(click())
    }
    @Test
    fun profileToUserDataClickTest(){
        onView(withId(R.id.rightImage)).perform(click())
        onView(withId(R.id.personal_infoText)).check(matches(isDisplayed()))
    }

    @Test
    fun profileToCarsClickTest(){
        onView(withId(R.id.carsContainer)).perform(click())
        onView(withId(R.id.defaultCarContainer)).check(matches(isDisplayed()))
    }
}