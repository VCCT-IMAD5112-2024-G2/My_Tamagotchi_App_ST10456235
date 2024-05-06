package com.example.mytalkingdog

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.core.app.ActivityScenario
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ActionPageInstrumentedTest {

    @Before
    fun setUp() {
        val scenario = ActivityScenario.launch(actionPage::class.java)
    }

    @Test
    fun testFeedButton() {
        // Click on the Feed button
        Espresso.onView(withId(R.id.feed_button)).perform(ViewActions.click())

        // Check if hunger level TextView is updated
        Espresso.onView(withId(R.id.hungerLevel)).check(matches(withText("Hunger: 10")))

        // Check if health level TextView is updated after feeding 3 times
        Espresso.onView(withId(R.id.feed_button)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.feed_button)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.healthLevel)).check(matches(withText("Health: 24")))
    }

    @Test
    fun testCleanButton() {
        // Click on the Clean button
        Espresso.onView(withId(R.id.clean_button)).perform(ViewActions.click())

        // Check if cleanliness level TextView is updated
        Espresso.onView(withId(R.id.cleanLevel)).check(matches(withText("Cleanliness: 10")))

        // Check if health level TextView is updated after cleaning 3 times
        Espresso.onView(withId(R.id.clean_button)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.clean_button)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.healthLevel)).check(matches(withText("Health: 1")))
    }

    @Test
    fun testPlayButton() {
        // Click on the Play button
        Espresso.onView(withId(R.id.play_button)).perform(ViewActions.click())

        // Check if happiness level TextView is updated
        Espresso.onView(withId(R.id.playLevel)).check(matches(withText("Happiness: 10")))

        // Check if health level TextView is updated after playing 3 times
        Espresso.onView(withId(R.id.play_button)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.play_button)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.healthLevel)).check(matches(withText("Health: 2")))
    }
}
