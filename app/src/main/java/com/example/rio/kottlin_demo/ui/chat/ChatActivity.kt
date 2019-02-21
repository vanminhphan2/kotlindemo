package com.example.rio.kottlin_demo.ui.chat

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.rio.kottlin_demo.R
import com.example.rio.kottlin_demo.data.model.User
import com.example.rio.kottlin_demo.databinding.ActivityChatBinding
import com.example.rio.kottlin_demo.ui.adapter.ListChatAdapter
import com.example.rio.kottlin_demo.ui.base.BaseActivity
import com.example.rio.kottlin_demo.utils.AppConstants

class ChatActivity : BaseActivity<ChatViewModel>() {

    private lateinit var activityChatBinding: ActivityChatBinding
    private lateinit var listChatAdapter: ListChatAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewReferences()
        if (intent.getStringExtra(AppConstants.USER_CHOOSE) != "") {
            registerEvents()
            initializeViews()
            initData()
        }
    }

    override fun getViewReferences() {
        activityChatBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChatViewModel::class.java)
        activityChatBinding.viewModel = viewModel
    }

    override fun initializeViews() {

        viewModel.getIdUserLogin()
        val layoutManager = LinearLayoutManager(applicationContext)
        listChatAdapter = ListChatAdapter(viewModel.chatViewData.listChat, applicationContext)
        activityChatBinding.rvListContentChat.layoutManager = layoutManager
        activityChatBinding.rvListContentChat.adapter = listChatAdapter


    }

    override fun registerEvents() {

        viewModel.getViewData().observe(this, Observer {
            activityChatBinding.viewModel = viewModel
        })



        viewModel.onLoadingEvent().observe(this, Observer { t ->
            if (t!!)
                showLoading()
            else hideLoading()
        })

        viewModel.onShowMessageEvent().observe(this, Observer {
            //            Toast.makeText(this, viewModel..message, Toast.LENGTH_SHORT).show()
        })

        viewModel.onClickBackEvent().observe(this, Observer {
            finish()
        })

        viewModel.onClickSendEvent().observe(this, Observer {
            activityChatBinding.etContent.setText("")
        })

        viewModel.onGetListChatSuccessEvent().observe(this, Observer {
            listChatAdapter.setListDataMess(viewModel.chatViewData.listChat, viewModel.chatViewData.user.id)
        })

        viewModel.onAddMessEvent().observe(this, Observer {
            listChatAdapter.notifyItemChanged(viewModel.chatViewData.listChat.size - 1)
        })
    }

    override fun initData() {
        viewModel.chatViewData.userReceive = intent.getSerializableExtra(AppConstants.USER_CHOOSE) as User
//        viewModel.bindInfoUserReceive()
        activityChatBinding.tvName.setText(viewModel.chatViewData.userReceive.name)
        viewModel.getBoxDataFromServer()
    }

}
