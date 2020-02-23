package me.jazz.kt_hw3.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjectionModule;
import dagger.android.ContributesAndroidInjector;
import me.jazz.kt_hw3.MyApp;
import me.jazz.kt_hw3.component.ResourceProviderImpl;
import me.jazz.kt_hw3.presentation.MainActivity;

@Module(includes = {AndroidInjectionModule.class})
abstract public class ApplicationModule {

    @Binds
    @Singleton
    abstract Context context(MyApp app);

    @Binds
    @Singleton
    abstract ResourceProvider provideResourceProvider(ResourceProviderImpl impl);

    @ContributesAndroidInjector(modules = {MainActivityModule.class})
    abstract MainActivity mainActivity();

}
