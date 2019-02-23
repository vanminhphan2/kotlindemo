package com.example.rio.kottlin_demo.ui.base

import android.app.Dialog
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Window
import com.example.rio.kottlin_demo.R
import dagger.android.AndroidInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject
import dagger.android.DispatchingAndroidInjector
import dagger.android.AndroidInjector





abstract class BaseActivity<V : Any> : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var loadingDialog: Dialog

    @Inject
    lateinit var fragmentAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentAndroidInjector
    }

    protected lateinit var viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        loadingDialog= Dialog(this)
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loadingDialog.setContentView(R.layout.dialog_loading)
        loadingDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    protected abstract fun getViewReferences()

    protected abstract fun initializeViews()

    protected abstract fun registerEvents()

    protected abstract fun initData()

    fun showLoading() {
        loadingDialog.show()
    }

    fun hideLoading() {
        loadingDialog.hide()
    }

    public override fun onDestroy() {
        if (loadingDialog.isShowing) {
            loadingDialog.cancel()
        }
        super.onDestroy()
    }
}