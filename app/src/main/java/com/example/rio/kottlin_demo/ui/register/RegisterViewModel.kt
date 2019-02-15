package com.example.rio.kottlin_demo.ui.register

import android.text.Editable
import android.util.Log
import com.example.rio.kottlin_demo.data.AppDataManager
import com.example.rio.kottlin_demo.ui.base.BaseViewModel
import com.example.rio.kottlin_demo.utils.SingleLiveEvent
import com.google.firebase.FirebaseException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.auth.*


class RegisterViewModel @Inject constructor(private var appDataManager: AppDataManager) : BaseViewModel<RegisterViewData>(){

    var registerViewData:RegisterViewData
    private val onRegisterClick: SingleLiveEvent<Void>
    private val onGetCodeClick: SingleLiveEvent<Void>
    private val onShowMessage: SingleLiveEvent<Void>
    private val onVerifyCode: SingleLiveEvent<Void>
    private val onReceiveCode: SingleLiveEvent<Void>

    fun onRegisterClickEvent(): SingleLiveEvent<Void> {
        return onRegisterClick
    }

    fun onGetCodeClickEvent(): SingleLiveEvent<Void> {
        return onGetCodeClick
    }

    fun onShowMessageEvent(): SingleLiveEvent<Void> {
        return onShowMessage
    }

    fun onVerifyCodeEvent(): SingleLiveEvent<Void> {
        return onVerifyCode
    }

    fun onReceiveCodeEvent(): SingleLiveEvent<Void> {
        return onReceiveCode
    }

    init {
        registerViewData= RegisterViewData()
        setDataView()
        onRegisterClick= SingleLiveEvent()
        onGetCodeClick= SingleLiveEvent()
        onShowMessage= SingleLiveEvent()
        onVerifyCode= SingleLiveEvent()
        onReceiveCode= SingleLiveEvent()
    }

    fun setDataView(){
        viewData.setValue(registerViewData)
    }

    fun onRegisterClick(){
        onRegisterClickEvent().call()
        Log.e("Rio", "onRegisterClick  :"+registerViewData.isVisibilityBtnGetCode)
    }

    fun onGetVerifyCodeClick(){

        if(registerViewData.phone.isEmpty() || registerViewData.phone.length < 10){
            registerViewData.message="phone is incorrect"
            updateViewData(registerViewData)
            onShowMessageEvent().call()
        }else{
            Log.e("Rio", "onGetVerifyCodeClick  :"+registerViewData.code)
            registerViewData.isEnabledEtPhone=false
            registerViewData.isVisibilityBtnGetCode=false
            registerViewData.isVisibilityEtCode=true
            registerViewData.isVisibilityBtnCheckCode=true
            updateViewData(registerViewData)
//        onGetCodeClickEvent().call()
            sendVerificationCode()
        }

    }

    fun onCheckCodeClick(){

        Log.e("Rio", "onCheckCodeClick  :"+registerViewData.code)
        registerViewData.isVisibilityEtCode=false
        registerViewData.isVisibilityBtnCheckCode=false
        registerViewData.isVisibilityEtNameCode=true
        registerViewData.isVisibilityEtPassCode=true
        registerViewData.isVisibilityBtnRegister=true
        updateViewData(registerViewData)

        onVerifyCodeEvent().call()

        //verifying the code
//        verifyVerificationCode(registerViewData.code)

    }

    private fun sendVerificationCode() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+84${registerViewData.phone}]",
            60,
            TimeUnit.SECONDS,
            TaskExecutors.MAIN_THREAD,
            mCallbacks
        )
    }

    private val mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
            //Getting the code sent by SMS
            val code = phoneAuthCredential.smsCode

            Log.e("Rio", "mCallbacks  :"+code)
            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                registerViewData.code=code
                updateViewData(registerViewData)
                Log.e("Rio", "code != null  :"+code)
                onReceiveCodeEvent().call()
            }
        }

        override fun onVerificationFailed(e: FirebaseException) {

            Log.e("Rio", "onVerificationFailed :"+e.message)
            onShowMessageEvent().call()
        }

        override fun onCodeSent(s: String?, forceResendingToken: PhoneAuthProvider.ForceResendingToken?) {
            super.onCodeSent(s, forceResendingToken)

            registerViewData.verificationId = s!!
            registerViewData.forceResendingToken = forceResendingToken.toString()

            Log.e("Rio", "onCodeSent  registerViewData.verificationId :"+ registerViewData.verificationId )
            Log.e("Rio", "onCodeSent  registerViewData.forceResendingToken :"+ registerViewData.forceResendingToken )
        }
    }

    fun updatePhone(phone: Editable) {
        Log.e("Rio", "updatePhone "+phone.toString())
        registerViewData.phone=phone.toString()
    }


}