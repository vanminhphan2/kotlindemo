package com.example.rio.kottlin_demo.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.rio.kottlin_demo.di.anotation.ViewModelFactory
import com.example.rio.kottlin_demo.di.anotation.ViewModelFactory1
import com.example.rio.kottlin_demo.di.anotation.ViewModelKey
import com.example.rio.kottlin_demo.ui.login.LoginViewModel
import com.example.rio.kottlin_demo.ui.main.MainViewModel
import com.example.rio.kottlin_demo.ui.register.RegisterViewModel
import com.example.rio.kottlin_demo.ui.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@SuppressWarnings("unused")
@Module
abstract class ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindsMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindsSplashViewModel(splashViewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindsLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindsRegisterViewModel(registerViewModel: RegisterViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}