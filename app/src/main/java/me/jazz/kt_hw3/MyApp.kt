package me.jazz.kt_hw3

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class MyApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerApplicationComponent
            .factory()
            .create(this)

    override fun onCreate() {
        super.onCreate()

        setupLogging()
    }

    private fun setupLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}