package com.example.rio.kottlin_demo.di.builder

import com.example.rio.kottlin_demo.di.anotation.ActivityScope
import com.example.rio.kottlin_demo.ui.login.LoginActivity
import com.example.rio.kottlin_demo.ui.main.MainActivity
import com.example.rio.kottlin_demo.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.Provides



@SuppressWarnings("unused")
@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    @ActivityScope
    abstract fun splashActivity():SplashActivity

    @ContributesAndroidInjector
    @ActivityScope
    abstract fun mainActivity():MainActivity

    @ContributesAndroidInjector
    @ActivityScope
    abstract fun loginActivity():LoginActivity

}