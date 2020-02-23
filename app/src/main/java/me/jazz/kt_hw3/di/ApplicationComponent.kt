package me.jazz.kt_hw3.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import me.jazz.kt_hw3.MyApp
import javax.inject.Singleton


@Component(modules = [ApplicationModule::class])
@Singleton
interface ApplicationComponent : AndroidInjector<MyApp> {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance appContext: Context): ApplicationComponent
    }
}