package com.example.urbandic.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.urbandic.test.base.DaggerTest
import org.junit.Rule

abstract class ActivityTest<T : AppCompatActivity>(activityClass: Class<T>) : DaggerTest() {

    @get:Rule
    val activityRule: ActivityTestRule<T> = ActivityTestRule(activityClass, true, true)

    val activity: T
        get() {
            if (activityRule.activity == null) {
                startActivity()
            }

            return activityRule.activity
        }

    fun startActivity() {
        startActivityWithIntent(Intent())
    }

    fun startActivityWithIntent(intent: Intent) {
        activityRule.launchActivity(intent)
    }

    @Throws(Throwable::class)
    fun runOnUiThread(action: () -> Unit) {
        activityRule.runOnUiThread(action)
    }

    fun waitForUiThread() {
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
    }
}