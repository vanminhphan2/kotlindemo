package com.example.rio.kottlin_demo.ui.splash

import com.example.rio.kottlin_demo.data.AppDataManager
import com.example.rio.kottlin_demo.ui.base.BaseViewModel
import com.example.rio.kottlin_demo.utils.SingleLiveEvent
import javax.inject.Inject

class SplashViewModel @Inject constructor(private var appDataManager: AppDataManager) :
    BaseViewModel<SplashViewData>() {

    private val toLogin: SingleLiveEvent<Void>
    private val toMain: SingleLiveEvent<Void>
    private var splashViewData:SplashViewData

    fun getToLoginEvent(): SingleLiveEvent<Void> {
        return toLogin
    }

    fun getToMainEvent(): SingleLiveEvent<Void> {
        return toMain
    }

    init {
        splashViewData = SplashViewData()
        setDataView()
        toLogin= SingleLiveEvent()
        toMain= SingleLiveEvent()
    }

    fun setDataView(){
        viewData.setValue(splashViewData)
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