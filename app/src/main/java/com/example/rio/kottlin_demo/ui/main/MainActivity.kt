package com.example.rio.kottlin_demo.ui.main

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import com.example.rio.kottlin_demo.R
import com.example.rio.kottlin_demo.ui.base.BaseActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity :BaseActivity<MainViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        initializeViews()
    }

    override fun getViewReferences() {

    }

    override fun initializeViews() {
    }

    override fun registerEvents() {
    }

    override fun observeDataChange() {
    }

}
