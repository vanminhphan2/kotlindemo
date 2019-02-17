package com.example.rio.kottlin_demo.ui.base

import android.app.Dialog
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import com.example.rio.kottlin_demo.R
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseActivity<T : Any> : AppCompatActivity(){

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var loadingDialog: Dialog


    protected lateinit var viewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        loadingDialog= Dialog(this)
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingDialog.setContentView(R.layout.dialog_loading);
        loadingDialog.getWindow().setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
    }

    protected abstract fun getViewReferences()

    protected abstract fun initializeViews()

    protected abstract fun registerEvents()

    protected abstract fun observeDataChange()

    fun showLoading() {
        if (loadingDialog != null)
            loadingDialog.show()
    }

    fun hideLoading() {
        if (loadingDialog != null)
            loadingDialog.hide()
    }

    public override fun onDestroy() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.cancel()
        }
        super.onDestroy()
    }
}