package es.voghdev.hellokotlin

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule @JvmField
    val testRule: ActivityTestRule<MainActivity> = IntentsTestRule(MainActivity::class.java)

    @Test
    fun shouldDisplayInitialLabel() {
        onView(withId(R.id.textView1)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldClickEditText() {
        onView(withId(R.id.editText1)).perform(click())
    }
}
