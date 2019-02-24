package com.example.rio.kottlin_demo.ui.login

import android.text.Editable
import android.util.Log
import com.example.rio.kottlin_demo.data.AppDataManager
import com.example.rio.kottlin_demo.data.firebase.FirebaseFirestoreInstance
import com.example.rio.kottlin_demo.data.firebase.FirebaseReferenceInstance
import com.example.rio.kottlin_demo.data.model.User
import com.example.rio.kottlin_demo.ui.base.BaseViewModel
import com.example.rio.kottlin_demo.utils.AppConstants
import com.example.rio.kottlin_demo.utils.ConvertData
import com.example.rio.kottlin_demo.utils.SingleLiveEvent
import com.example.rio.kottlin_demo.utils.rx.AppSchedulerProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import javax.inject.Inject

class LoginViewModel @Inject constructor(private var appDataManager: AppDataManager) : BaseViewModel<LoginViewData>() {

    var loginViewData: LoginViewData
    private val toRegister: SingleLiveEvent<Void>
    private val toMain: SingleLiveEvent<Void>

    fun getToRegisterEvent(): SingleLiveEvent<Void> {
        return toRegister
    }

    fun getToMainEvent(): SingleLiveEvent<Void> {
        return toMain
    }

    init {
        loginViewData = LoginViewData()
        setDataView()

        toRegister = SingleLiveEvent()
        toMain = SingleLiveEvent()
    }

    fun setDataView() {
        viewData.setValue(loginViewData)
    }

    fun onServerLoginClick() {
        if (loginViewData.phone.length < 10) {
            loginViewData.message = "Name is incorrect!"
            onShowMessageEvent().call()
        } else if (loginViewData.pass.length < 6) {
            loginViewData.message = "Pass is incorrect!"
            onShowMessageEvent().call()
        } else {
//            onLoginRealTimeDbServer()
            onLoginFireStoreServer()
        }
    }

    fun onRegisterClick() {
        getToRegisterEvent().call()
    }

    fun updatePhone(phone: Editable) {
        loginViewData.phone = phone.toString()
    }

    fun updatePass(pass: Editable) {
        loginViewData.pass = pass.toString()
    }

    fun onLoginRealTimeDbServer() {
        FirebaseReferenceInstance.getUsersReference().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.getValue() != null) {

                    var phoneIsSuccess = false
                    var passIsSuccess = false
                    var u= User()
                    for (snapshot in dataSnapshot.getChildren()) {
                        if (loginViewData.phone.equals(snapshot.child("phone").value)) {
                            phoneIsSuccess = true
                            if (loginViewData.pass.equals(snapshot.child("pass").value)) {
                                passIsSuccess = true
//                                u = ConvertData.convertSnapshotToUser(snapshot)
                            }
                        }
                    }
                    if (phoneIsSuccess && passIsSuccess) {
                        val token = AppConstants.generateTokenString()
                        FirebaseReferenceInstance.getSessionsReference().child(token).setValue(loginViewData.phone)
                        appDataManager.setLoginToken(token)
                        appDataManager.setUserId(u.id)
                        disposable.add(appDataManager.insertUser(u)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ t: User? ->
                                Log.e("Rio", "login is ok")
                                Log.e("Rio", "save user success : " + t.toString())
                                getToMainEvent().call()
                            }, { throwable ->
                                Log.e("Rio", "save user fails")
                            })
                        )

                    } else {
                        if (!phoneIsSuccess) {
                            loginViewData.message = "Phone is incorrect!"
                            Log.e("Rio", "login phone is incorrect!")
                        } else {
                            loginViewData.message = "Pass is incorrect!"
                            Log.e("Rio", "login pass is incorrect!")
                        }
                        hideLoading()
                        onShowMessageEvent().call()
                    }
                } else {
                    Log.e("Rio", "dataSnapshot.getValue() null")
                }
                FirebaseReferenceInstance.getUsersReference().removeEventListener(this);
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.e("Rio", "Failed to read value." + error.message)
            }
        })

    }

    fun onLoginFireStoreServer(){
        FirebaseFirestoreInstance.getUserByPhoneAndPass(loginViewData.phone,loginViewData.pass)
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    loginViewData.message = "Phone or pass is incorrect!"
                    Log.e("Rio", "login phone or pass is incorrect!")
                    hideLoading()
                    onShowMessageEvent().call()
                } else {
                    for (document in documents) {
                        disposable.add(appDataManager.insertUser(document.toObject(User::class.java))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ u: User ->
                                Log.e("Rio", "login = phone ok  : " + u.toString())
                                loginViewData.user=u
                                createLoginToken()
                            }, { throwable ->
                                Log.e("Rio", "save user fails: "+throwable.message)
                            })
                        )
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Rio", "checkExitsPhoneOnFireStore  error: " + exception.message)

            }
    }

    fun createLoginToken(){

        loginViewData.user.loginToken = AppConstants.generateTokenString()
        FirebaseFirestoreInstance.createLoginToken(loginViewData.user)
            .addOnSuccessListener {
                appDataManager.setLoginToken(loginViewData.user.loginToken)
                appDataManager.setUserId(loginViewData.user.id)
                FirebaseFirestoreInstance.updateUserAccount(loginViewData.user)
                Log.e("Rio", "createLoginToken ok: ")
                getToMainEvent().call()
            }
            .addOnFailureListener { e ->
                loginViewData.message = e.message.toString()
                onShowMessageEvent().call()
                hideLoading()
                Log.e("Rio", "Error getting documents: ", e)
            }
    }
}