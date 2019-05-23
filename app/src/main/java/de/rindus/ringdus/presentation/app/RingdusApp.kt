package de.rindus.ringdus.presentation.app

import androidx.multidex.MultiDexApplication
import de.rindus.ringdus.presentation.di.ringdusModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RingdusApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            fileProperties()
            androidContext(this@RingdusApp)
            modules(ringdusModule)
        }
    }
}