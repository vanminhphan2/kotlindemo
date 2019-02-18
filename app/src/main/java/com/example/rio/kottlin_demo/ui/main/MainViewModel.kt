package com.example.rio.kottlin_demo.ui.main

import com.example.rio.kottlin_demo.data.AppDataManager
import com.example.rio.kottlin_demo.ui.base.BaseViewData
import com.example.rio.kottlin_demo.ui.base.BaseViewModel
import com.example.rio.kottlin_demo.utils.SingleLiveEvent
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class MainViewModel @Inject constructor(private var appDataManager: AppDataManager) : BaseViewModel<MainViewData>() {

    val database = FirebaseDatabase.getInstance()
    val myRefSession = database.getReference("sessions")

    private val toLogin: SingleLiveEvent<Void>


    fun getToLoginEvent(): SingleLiveEvent<Void> {
        return toLogin
    }

    init {
        toLogin= SingleLiveEvent()
    }

    fun onClickLogout(){
        myRefSession.child(appDataManager.getLoginToken().toString()).removeValue();
        appDataManager.setLoginToken("")
        getToLoginEvent().call()
    }
}