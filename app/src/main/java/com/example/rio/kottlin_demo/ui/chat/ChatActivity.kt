package com.example.rio.kottlin_demo.ui.chat

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.example.rio.kottlin_demo.R
import com.example.rio.kottlin_demo.databinding.ActivityChatBinding
import com.example.rio.kottlin_demo.ui.base.BaseActivity
import com.example.rio.kottlin_demo.utils.AppConstants

class ChatActivity : BaseActivity<ChatViewModel>() {

    private lateinit var activityChatBinding:ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewReferences()
        if(intent.getStringExtra(AppConstants.ID_USER_CHOOSE) != ""){

            initializeViews()
            registerEvents()
            observeDataChange()
        }
    }

    override fun getViewReferences() {
        activityChatBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChatViewModel::class.java)
        activityChatBinding.viewModel = viewModel
    }

    override fun initializeViews() {
        viewModel.chatViewData.idUserSend=intent.getStringExtra(AppConstants.ID_USER_CHOOSE)
    }

    override fun registerEvents() {

        viewModel.getViewData().observe(this, Observer {
            activityChatBinding.viewModel = viewModel
        })



        viewModel.onLoadingEvent().observe(this, Observer {t->
            if (t!!)
                showLoading()
            else hideLoading()
        })

        viewModel.onShowMessageEvent().observe(this, Observer {
//            Toast.makeText(this, viewModel..message, Toast.LENGTH_SHORT).show()
        })


    }

    override fun observeDataChange() {

    }

}
