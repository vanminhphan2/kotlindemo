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
import com.example.rio.kottlin_demo.utils.AppConstants
import android.widget.Toast
import android.app.Activity
import com.example.rio.kottlin_demo.MyApp
import com.example.rio.kottlin_demo.ui.main.MainActivity


class LoginActivity : BaseActivity<LoginViewModel>() {

    private lateinit var activityLoginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getViewReferences()
        registerEvents()
        initializeViews()
    }

    override fun getViewReferences() {
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
        activityLoginBinding.viewModel = viewModel
    }

    override fun initializeViews() {

    }

    override fun registerEvents() {

        viewModel.getViewData().observe(this, Observer {
            activityLoginBinding.viewModel = viewModel
        })

        viewModel.getToRegisterEvent().observe(this, Observer {
            val starter = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivityForResult(starter,AppConstants.REQUEST_CODE_TO_REGISTER_ACTIVITY)
        })

        viewModel.getToMainEvent().observe(this, Observer {
            val starter = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(starter)
        })

        viewModel.onLoadingEvent().observe(this, Observer {t->
            if (t!!)
                showLoading()
            else hideLoading()
        })

        viewModel.onShowMessageEvent().observe(this, Observer {
            Toast.makeText(this, viewModel.loginViewData.message, Toast.LENGTH_SHORT).show()
        })


    }

    override fun initData() {

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AppConstants.REQUEST_CODE_TO_REGISTER_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                val intent= Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
