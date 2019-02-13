package com.example.rio.kottlin_demo.ui.register

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.rio.kottlin_demo.R
import com.example.rio.kottlin_demo.databinding.ActivityRegisterBinding
import com.example.rio.kottlin_demo.ui.base.BaseActivity

class RegisterActivity : BaseActivity<RegisterViewModel>() {

    private lateinit var activityRegisterBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewReferences()
        registerEvents()
    }

    override fun getViewReferences() {
        activityRegisterBinding= DataBindingUtil.setContentView(this,R.layout.activity_register)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RegisterViewModel::class.java)
        activityRegisterBinding.viewModel=viewModel
    }

    override fun initializeViews() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun registerEvents() {

        viewModel.getViewData().observe(this, Observer {
            activityRegisterBinding.viewModel=viewModel
        })

      viewModel.onRegisterClickEvent().observe(this, Observer {  })
    }

    override fun observeDataChange() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
