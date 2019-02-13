package com.example.rio.kottlin_demo.ui.register

import android.util.Log
import com.example.rio.kottlin_demo.data.AppDataManager
import com.example.rio.kottlin_demo.ui.base.BaseViewModel
import com.example.rio.kottlin_demo.utils.SingleLiveEvent
import javax.inject.Inject

class RegisterViewModel @Inject constructor(private var appDataManager: AppDataManager) : BaseViewModel<RegisterViewData>(){

    var registerViewData:RegisterViewData
    private val onRegisterClick: SingleLiveEvent<Void>

    fun onRegisterClickEvent(): SingleLiveEvent<Void> {
        return onRegisterClick
    }

    init {
        registerViewData= RegisterViewData()
        setDataView()
        onRegisterClick= SingleLiveEvent()
    }

    fun setDataView(){
        viewData.setValue(registerViewData)
    }

    fun onRegisterClick(){
        onRegisterClickEvent().call()
        Log.e("Rio", "onRegisterClick  :"+registerViewData.isVisibilityBtnGetCode)
    }

    fun onGetVerifyCodeClick(){
        registerViewData.isEnabledEtPhone=false
        registerViewData.isVisibilityBtnGetCode=false
        registerViewData.isVisibilityEtCode=true
        registerViewData.isVisibilityBtnCheckCode=true
        updateViewData(registerViewData)
    }

    fun onCheckCodeClick(){
        registerViewData.isVisibilityEtCode=false
        registerViewData.isVisibilityBtnCheckCode=false
        registerViewData.isVisibilityEtNameCode=true
        registerViewData.isVisibilityEtPassCode=true
        registerViewData.isVisibilityBtnRegister=true
        updateViewData(registerViewData)
    }

}