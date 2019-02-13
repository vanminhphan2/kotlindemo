package com.example.rio.kottlin_demo.ui.login

import android.util.Log
import com.example.rio.kottlin_demo.data.AppDataManager
import com.example.rio.kottlin_demo.ui.base.BaseViewModel
import com.example.rio.kottlin_demo.utils.SingleLiveEvent
import javax.inject.Inject

class LoginViewModel @Inject constructor(private var appDataManager: AppDataManager):BaseViewModel<LoginViewData>(){

    private val toRegister: SingleLiveEvent<Void>
    private var loginViewData:LoginViewData

    fun getToRegisterEvent(): SingleLiveEvent<Void> {
        return toRegister
    }

    init {
        loginViewData= LoginViewData()
        setDataView()

        toRegister= SingleLiveEvent()
    }


    fun setDataView(){
        viewData.setValue(loginViewData)
    }

    fun onServerLoginClick(){

    }

    fun onRegisterClick(){
        getToRegisterEvent().call()
    }

}