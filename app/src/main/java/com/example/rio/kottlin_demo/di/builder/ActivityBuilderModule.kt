package com.example.rio.kottlin_demo.di.builder

import com.example.rio.kottlin_demo.di.anotation.ActivityScope
import com.example.rio.kottlin_demo.ui.chat.ChatActivity
import com.example.rio.kottlin_demo.ui.login.LoginActivity
import com.example.rio.kottlin_demo.ui.main.MainActivity
import com.example.rio.kottlin_demo.ui.register.RegisterActivity
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

    @ContributesAndroidInjector(modules = [FragmentBuilderModule::class])
    @ActivityScope
    abstract fun mainActivity():MainActivity

    @ContributesAndroidInjector
    @ActivityScope
    abstract fun loginActivity():LoginActivity

    @ContributesAndroidInjector
    @ActivityScope
    abstract fun registerActivity():RegisterActivity

    @ContributesAndroidInjector
    @ActivityScope
    abstract fun chatActivity():ChatActivity

}