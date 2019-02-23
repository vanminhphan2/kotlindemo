package com.example.rio.kottlin_demo.ui.register

import android.text.Editable
import android.util.Log
import com.example.rio.kottlin_demo.data.AppDataManager
import com.example.rio.kottlin_demo.data.firebase.FirebaseFirestoreInstance
import com.example.rio.kottlin_demo.data.firebase.FirebaseReferenceInstance
import com.example.rio.kottlin_demo.data.model.User
import com.example.rio.kottlin_demo.ui.base.BaseViewModel
import com.example.rio.kottlin_demo.utils.AppConstants
import com.example.rio.kottlin_demo.utils.SingleLiveEvent
import com.google.firebase.FirebaseException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class RegisterViewModel @Inject constructor(private var appDataManager: AppDataManager) :
    BaseViewModel<RegisterViewData>() {

    var registerViewData: RegisterViewData
    private val onRegisterClick: SingleLiveEvent<Void>
    private val onVerifyCode: SingleLiveEvent<Void>
    private val onReceiveCode: SingleLiveEvent<Void>

    fun onRegisterSuccessEvent(): SingleLiveEvent<Void> {
        return onRegisterClick
    }

    fun onVerifyCodeEvent(): SingleLiveEvent<Void> {
        return onVerifyCode
    }

    fun onReceiveCodeEvent(): SingleLiveEvent<Void> {
        return onReceiveCode
    }

    init {
        registerViewData = RegisterViewData()
        setDataView()
        onRegisterClick = SingleLiveEvent()
        onShowMessage = SingleLiveEvent()
        onVerifyCode = SingleLiveEvent()
        onReceiveCode = SingleLiveEvent()
    }

    fun setDataView() {
        viewData.setValue(registerViewData)
    }

    fun onGetVerifyCodeClick() {

        if (registerViewData.user.phone.isEmpty() || registerViewData.user.phone.length < 10) {
            registerViewData.message = "phone is incorrect"
            updateViewData(registerViewData)
            onShowMessageEvent().call()
        } else {
            showLoading()
            Log.e("Rio", "onGetVerifyCodeClick  :" + registerViewData.code)
//           checkExitsPhoneOnServer()
            checkExitsPhoneOnFireStore()
        }

    }

    fun onCheckCodeClick() {
        Log.e("Rio", "onCheckCodeClick  :" + registerViewData.code)
        showLoading()
        onVerifyCodeEvent().call()

    }

    private fun sendVerificationCode() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+84${registerViewData.user.phone}]",
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
            Log.e("Rio", "mCallbacks  :" + code)
            if (code != null) {
                registerViewData.code = code
                updateViewData(registerViewData)
                Log.e("Rio", "code != null  :" + code)
                onReceiveCodeEvent().call()
                hideLoading()
            }
        }

        override fun onVerificationFailed(e: FirebaseException) {

            Log.e("Rio", "onVerificationFailed :" + e.message)
            //the the phone number format is not valid
            hideLoading()
            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                registerViewData.message = "the the phone number format is not valid"
                updateViewData(registerViewData)
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                registerViewData.message = "The SMS quota for the project has been exceeded"
                updateViewData(registerViewData)
            }

            onShowMessageEvent().call()
        }

        override fun onCodeSent(s: String?, forceResendingToken: PhoneAuthProvider.ForceResendingToken?) {
            super.onCodeSent(s, forceResendingToken)


            registerViewData.isEnabledEtPhone = false
            registerViewData.isVisibilityBtnGetCode = false
            registerViewData.isVisibilityEtCode = true
            registerViewData.isVisibilityBtnCheckCode = true
            registerViewData.verificationId = s!!
            registerViewData.forceResendingToken = forceResendingToken.toString()

            registerViewData.message = "The SMS verification code has been sent to the provided phone number"
            updateViewData(registerViewData)
            hideLoading()
            Log.e("Rio", "onCodeSent  registerViewData.verificationId :" + registerViewData.verificationId)
            Log.e("Rio", "onCodeSent  registerViewData.forceResendingToken :" + registerViewData.forceResendingToken)
        }
    }

    fun checkExitsPhoneOnServer() {

        FirebaseReferenceInstance.getUsersReference().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.getValue() != null) {

                    var isExits = false
                    for (postSnapshot in dataSnapshot.getChildren()) {
                        if (registerViewData.user.phone.equals(postSnapshot.child("phone").value)) {
                            isExits = true
                        }
                    }
                    if (isExits) {
                        registerViewData.message = "Phone is exits!"
                        hideLoading()
                        onShowMessageEvent().call()
                        Log.e("Rio", "Phone is exits")
                    } else {
                        sendVerificationCode()

                        Log.e("Rio", "Phone is ok")
                    }
                } else {
                    Log.e("Rio", "dataSnapshot.getValue() null")

                }
                FirebaseReferenceInstance.getUsersReference().removeEventListener(this);
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.e("Rio", "Failed to read value.", error.toException())
            }
        })

    }

    fun checkExitsPhoneOnFireStore() {


        FirebaseFirestoreInstance.getUserByPhone(registerViewData.user.phone)
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {

                    Log.e("Rio", "Phone chua dk, co the dung")
//                    sendVerificationCode()
                    verifySuccess()

                } else {
                    registerViewData.message = "Phone is exits!"
                    hideLoading()
                    onShowMessageEvent().call()
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Rio", "checkExitsPhoneOnFireStore  error: " + exception.message)

            }

    }

    fun verifySuccess() {
        registerViewData.isVisibilityBtnGetCode = false
        registerViewData.isVisibilityEtCode = false
        registerViewData.isVisibilityBtnCheckCode = false
        registerViewData.isVisibilityEtNameCode = true
        registerViewData.isVisibilityEtPassCode = true
        registerViewData.isVisibilityBtnRegister = true

        updateViewData(registerViewData)
//        createAccToRealTimeDb()
        createAccToFireStore()
    }

    fun createAccToRealTimeDb() {
        FirebaseReferenceInstance.createUserAccount(registerViewData.user.id, registerViewData.user.phone)
    }

    fun createAccToFireStore() {

        registerViewData.user.id = FirebaseFirestoreInstance.getUserCollection().document().id  //id temp
        FirebaseFirestoreInstance.createUserAccount(registerViewData.user.id, registerViewData.user.phone)
            .addOnSuccessListener {
                appDataManager.setUserId(registerViewData.user.id)
                Log.e("Rio", "createAccToFireStore : ok ")
                hideLoading()
            }
            .addOnFailureListener { e ->
                registerViewData.message = e.message.toString()
                onShowMessageEvent().call()
                hideLoading()
                Log.e("Rio", "Error getting documents: ", e)
            }
    }

    fun onRegisterClick() {
        if (registerViewData.user.name.length < 3) {
            registerViewData.message = "Name is incorrect!"
            onShowMessageEvent().call()
        } else if (registerViewData.user.pass.length < 6) {
            registerViewData.message = "Pass is incorrect!"
            onShowMessageEvent().call()
        } else {
//            registerUserToRealTimeDb()
            registerUserToFireStore()
        }
    }

    fun registerUserToRealTimeDb() {
        registerViewData.user.loginToken = AppConstants.generateTokenString()
        FirebaseReferenceInstance.updateUserAccount(registerViewData.user)
        FirebaseReferenceInstance.createLoginToken(registerViewData.user.loginToken, registerViewData.user.phone)
        appDataManager.setLoginToken(registerViewData.user.loginToken)
        onRegisterSuccessEvent().call()
    }

    fun registerUserToFireStore() {
        registerViewData.user.loginToken = AppConstants.generateTokenString()
        registerViewData.user.isUpdateInfo = true
        registerViewData.user.isOnline = true
        registerViewData.user.maritalStatus = "doc than"
        registerViewData.user.avatar = "chua co ava"
        registerViewData.user.timeCreate = AppConstants.getTimeNow()
        registerViewData.user.timeUpdate = registerViewData.user.timeCreate
        FirebaseFirestoreInstance.updateUserAccount(registerViewData.user)
        FirebaseFirestoreInstance.createLoginToken(registerViewData.user.loginToken)
            .addOnSuccessListener {
                appDataManager.setLoginToken(registerViewData.user.loginToken)
                appDataManager.setUserId(registerViewData.user.id)
                onRegisterSuccessEvent().call()
            }
            .addOnFailureListener { e ->
                registerViewData.message = e.message.toString()
                onShowMessageEvent().call()
                hideLoading()
                Log.e("Rio", "Error getting documents: ", e)
            }
    }

    fun updatePhone(phone: Editable) {
        registerViewData.user.phone = phone.toString()
    }

    fun updateName(name: Editable) {
        registerViewData.user.name = name.toString()
    }

    fun updatePass(pass: Editable) {
        registerViewData.user.pass = pass.toString()
    }

    fun updateCode(code: Editable) {
        registerViewData.code = code.toString()
    }
}