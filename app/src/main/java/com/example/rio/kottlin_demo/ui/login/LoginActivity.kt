package com.example.rio.kottlin_demo.ui.login

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.example.rio.kottlin_demo.R
import com.example.rio.kottlin_demo.databinding.ActivityLoginBinding
import com.example.rio.kottlin_demo.ui.base.BaseActivity

class LoginActivity : BaseActivity<LoginViewModel>() {

    private lateinit var activityLoginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
        getViewReferences()
    }

    override fun getViewReferences() {
        activityLoginBinding= DataBindingUtil.setContentView(this,R.layout.activity_login)
    }

    override fun initializeViews() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun registerEvents() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun observeDataChange() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
