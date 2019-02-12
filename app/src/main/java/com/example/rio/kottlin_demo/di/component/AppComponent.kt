package com.example.rio.kottlin_demo.di.component

import android.app.Application
import com.example.rio.kottlin_demo.MyApp
import com.example.rio.kottlin_demo.di.builder.ActivityBuilderModule
import com.example.rio.kottlin_demo.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityBuilderModule::class])
interface AppComponent{

    fun inject(app: MyApp)
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
