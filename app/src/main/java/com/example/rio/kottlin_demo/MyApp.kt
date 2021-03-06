package com.example.rio.kottlin_demo

import android.app.Activity
import android.app.Application
import android.support.multidex.MultiDexApplication
import com.example.rio.kottlin_demo.data.model.User
import com.example.rio.kottlin_demo.di.component.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MyApp : MultiDexApplication(),HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return activityDispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder().application(this).build().inject(this)
    }
}