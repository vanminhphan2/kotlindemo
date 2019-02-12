package com.example.rio.kottlin_demo.ui.splash

import com.example.rio.kottlin_demo.data.AppDataManager
import com.example.rio.kottlin_demo.ui.base.BaseViewModel
import com.example.rio.kottlin_demo.utils.SingleLiveEvent
import javax.inject.Inject

class SplashViewModel @Inject constructor(private var appDataManager: AppDataManager) :
    BaseViewModel<SplashDataView>() {

    private val toLogin: SingleLiveEvent<Void>
    private val toMain: SingleLiveEvent<Void>
    private var splashDataView:SplashDataView

    fun getToLoginEvent(): SingleLiveEvent<Void> {
        return toLogin
    }

    fun getToMainEvent(): SingleLiveEvent<Void> {
        return toMain
    }

    init {
        splashDataView = SplashDataView()
        setDataView()
        toLogin= SingleLiveEvent()
        toMain= SingleLiveEvent()
    }

    fun setDataView(){
        viewData.setValue(splashDataView)
    }

    fun checkLogin(){
        if(appDataManager.getLoginToken().equals("")||appDataManager.getLoginToken()==null){
            getToLoginEvent().call()
        }
        else{
            getToMainEvent().call()
        }
    }
}