package com.newyork.times.app.baseTest

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.newyork.times.R
import com.newyork.times.app.baseTest.utils.MyViewAction
import com.newyork.times.ui.base.BaseActivity

import org.junit.Before
import org.junit.Rule
import org.junit.Test


@LargeTest
class AppJourney {

    /**
     * Set the rule to launch the activity.
     */
    @get:Rule
    val mActivityRule1 = ActivityTestRule(BaseActivity::class.java)

    @Before
    fun setup() {

    }


    @Test
    fun isInputCitiesDialogDisplays() {

        Thread.sleep(7000)

        onView(withId(R.id.article_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2,click()))

    }


    fun viewIsDisplayed(str: String): Boolean {
        val isDisplayed = booleanArrayOf(true)
        onView(withText(str)).withFailureHandler { error, viewMatcher -> isDisplayed[0] = false }
            .check(matches(isDisplayed()))
        return isDisplayed[0]
    }


}