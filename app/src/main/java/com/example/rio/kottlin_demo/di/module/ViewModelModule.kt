package com.example.rio.kottlin_demo.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.rio.kottlin_demo.di.anotation.ViewModelFactory
import com.example.rio.kottlin_demo.di.anotation.ViewModelKey
import com.example.rio.kottlin_demo.ui.chat.ChatViewModel
import com.example.rio.kottlin_demo.ui.login.LoginViewModel
import com.example.rio.kottlin_demo.ui.main.MainViewModel
import com.example.rio.kottlin_demo.ui.main.account.AccountViewModel
import com.example.rio.kottlin_demo.ui.main.boxs.BoxsViewModel
import com.example.rio.kottlin_demo.ui.main.search.SearchViewModel
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
    @IntoMap
    @ViewModelKey(BoxsViewModel::class)
    abstract fun bindsBoxsViewModel(boxsViewModel: BoxsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    abstract fun bindsAccountViewModel(accountViewModel: AccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindsSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    abstract fun bindsChatViewModel(chatViewModel: ChatViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}