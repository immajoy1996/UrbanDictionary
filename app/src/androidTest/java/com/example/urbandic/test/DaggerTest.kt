package com.example.urbandic.test.base

import android.os.RemoteException
import androidx.annotation.CallSuper
import androidx.test.platform.app.InstrumentationRegistry
import com.example.urbandic.di.AppComponentHolder
import com.example.urbandic.test.di.TestComponent
import androidx.test.uiautomator.UiDevice
import com.example.urbandic.test.di.DaggerTestComponent
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before

abstract class DaggerTest {
    protected val testComponent: TestComponent = buildTestComponent()

    @Before
    @CallSuper
    open fun setUp() {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        try {
            if (!device.isScreenOn) {
                device.wakeUp()
            }
        } catch (e: RemoteException) {
            e.printStackTrace()
        }

        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setInitIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
        AppComponentHolder.component = testComponent
    }

    private fun buildTestComponent(): TestComponent {
        return DaggerTestComponent.builder()
            .build()
    }
}