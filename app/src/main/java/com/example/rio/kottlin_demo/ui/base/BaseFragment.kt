package com.example.rio.kottlin_demo.ui.base

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment<V : Any>:Fragment(){

    private lateinit var baseActivity: BaseActivity<V>

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var viewModel: V

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context)
        if (context is BaseActivity<*>) {
            baseActivity = context as BaseActivity<V>
        }
    }

    protected abstract fun getViewReferences()
    protected abstract fun initializeViews()
    protected abstract fun registerEvents()
    protected abstract fun observeDataChange()


    fun getBaseActivity():  BaseActivity<V> {
        return baseActivity
    }
}