package com.example.rio.kottlin_demo.ui.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.rio.kottlin_demo.R
import com.example.rio.kottlin_demo.ui.base.BaseActivity
import com.example.rio.kottlin_demo.ui.login.LoginActivity
import com.example.rio.kottlin_demo.ui.main.MainActivity

class SplashActivity : BaseActivity<SplashViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel::class.java)
        registerEvents()
        initializeViews()
    }

    override fun getViewReferences() {
    }

    override fun initializeViews() {
        Handler().postDelayed({
            viewModel.checkLogin()
        }, 2000)

    }

    override fun registerEvents() {

        viewModel.getToLoginEvent().observe(this,Observer { isLogged ->
            val starter = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(starter)
            finish()
        })

        viewModel.getToMainEvent().observe(this,Observer { isLogged ->
            val starter = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(starter)
            finish()
        })
    }

    override fun observeDataChange() {

    }

}
