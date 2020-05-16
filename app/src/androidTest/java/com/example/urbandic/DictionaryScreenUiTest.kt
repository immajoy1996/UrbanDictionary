package com.example.urbandic

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.urbandic.test.ActivityTest
import com.example.urbandic.ui.DictionaryActivity
import io.mockk.clearAllMocks
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class DictionaryScreenUiTest : ActivityTest<DictionaryActivity>(DictionaryActivity::class.java) {

    @Before
    override fun setUp() {
        super.setUp()
        clearAllMocks()
        Intents.init()
        testComponent.inject(this)
    }

    @After
    fun teardown() {
        Intents.release()
    }

    @Test
    fun testScreenContainsToggleButtons() {
        startActivity()
        Espresso.onView(ViewMatchers.withId(R.id.most))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.least))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.favorite))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
