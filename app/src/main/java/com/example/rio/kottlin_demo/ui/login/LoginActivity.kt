package com.example.rio.kottlin_demo.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import com.example.rio.kottlin_demo.R
import com.example.rio.kottlin_demo.databinding.ActivityLoginBinding
import com.example.rio.kottlin_demo.ui.base.BaseActivity
import com.example.rio.kottlin_demo.ui.register.RegisterActivity

class LoginActivity : BaseActivity<LoginViewModel>() {

    private lateinit var activityLoginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getViewReferences()
        registerEvents()
    }

    override fun getViewReferences() {
        activityLoginBinding= DataBindingUtil.setContentView(this,R.layout.activity_login)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
        activityLoginBinding.viewModel = viewModel
    }

    override fun initializeViews() {

    }

    override fun registerEvents() {
        viewModel.getToRegisterEvent().observe(this, Observer {

            val starter = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(starter)
        })
    }

    override fun observeDataChange() {

    }
}
