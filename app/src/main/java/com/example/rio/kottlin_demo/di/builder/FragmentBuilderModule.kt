package com.example.rio.kottlin_demo.di.builder

import com.example.rio.kottlin_demo.di.anotation.PerFragment
import com.example.rio.kottlin_demo.ui.main.account.AccountFragment
import com.example.rio.kottlin_demo.ui.main.boxs.BoxsFragment
import com.example.rio.kottlin_demo.ui.main.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@SuppressWarnings("unused")
@Module
abstract class FragmentBuilderModule{

    @ContributesAndroidInjector
    @PerFragment
    abstract fun boxsFragment(): BoxsFragment


    @ContributesAndroidInjector
    @PerFragment
    abstract fun accountFragment(): AccountFragment

    @ContributesAndroidInjector
    @PerFragment
    abstract fun searchFragment(): SearchFragment

}