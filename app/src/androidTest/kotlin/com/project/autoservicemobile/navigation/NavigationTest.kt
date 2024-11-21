package com.project.autoservicemobile.navigation

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
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class NavigationTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun homeNavTest(){
        onView(withId(R.id.navigation_home)).perform(click()).check(matches(isDisplayed()))
    }

    @Test
    fun servicesNavTest(){
        onView(withId(R.id.navigation_services)).perform(click()).check(matches(isDisplayed()))
    }

    @Test
    fun cartNavTest(){
        onView(withId(R.id.navigation_cart)).perform(click()).check(matches(isDisplayed()))
    }

    @Test
    fun loyaltyNavTest(){
        onView(withId(R.id.navigation_loyalty)).perform(click()).check(matches(isDisplayed()))
    }

    @Test
    fun profileNavTest(){
        onView(withId(R.id.profileFragment)).perform(click()).check(matches(isDisplayed()))
    }
}