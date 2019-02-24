package com.example.rio.kottlin_demo.ui.splash

import android.util.Log
import com.example.rio.kottlin_demo.data.AppDataManager
import com.example.rio.kottlin_demo.data.firebase.FirebaseFirestoreInstance
import com.example.rio.kottlin_demo.data.firebase.FirebaseReferenceInstance
import com.example.rio.kottlin_demo.data.model.User
import com.example.rio.kottlin_demo.ui.base.BaseViewModel
import com.example.rio.kottlin_demo.utils.ConvertData
import com.example.rio.kottlin_demo.utils.SingleLiveEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SplashViewModel @Inject constructor(private var appDataManager: AppDataManager) :
    BaseViewModel<SplashViewData>() {

    private val toLogin: SingleLiveEvent<Void>
    private val toMain: SingleLiveEvent<Void>
    private var splashViewData: SplashViewData

    fun getToLoginEvent(): SingleLiveEvent<Void> {
        return toLogin
    }

    fun getToMainEvent(): SingleLiveEvent<Void> {
        return toMain
    }

    init {
        splashViewData = SplashViewData()
        setDataView()
        toLogin = SingleLiveEvent()
        toMain = SingleLiveEvent()
    }

    fun setDataView() {
        viewData.setValue(splashViewData)
    }

    fun checkLogin() {


        splashViewData.loginToken=appDataManager.getLoginToken().toString()
        Log.e("Rio", "  splashViewData.loginToken: "+ splashViewData.loginToken)
        if(splashViewData.loginToken.equals("")){
            getToLoginEvent().call()
        }
        else{
            checkLoginOnFireStore()
        }
    }

    fun checkLoginOnFireStore(){

        FirebaseFirestoreInstance.getLoginToken(splashViewData.loginToken)
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Log.e("Rio ","to login because have not token!")
                    getToLoginEvent().call()
                } else {
                    getInfoUser()
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Rio", "checkExitsPhoneOnFireStore  error: " + exception.message)

            }
    }

    fun checkLoginOnRealTimeDb(){

        var phone:String
        FirebaseReferenceInstance.checkPhoneByToken(splashViewData.loginToken).addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.e("Rio ","loi get data m01: "+ p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.getValue() != null) {
                    phone = p0.getValue().toString()
                    Log.e("Rio ","login = token = "+ p0.getValue().toString())
                    getInfoUser(phone)
                }
                else{
                    Log.e("Rio ","to login because have not token!")
                    getToLoginEvent().call()
                }
                FirebaseReferenceInstance.removeListener(FirebaseReferenceInstance.getSessionsReference(),this);
            }

        })
    }

    fun getInfoUser(phone:String){

        FirebaseReferenceInstance.getUserByPhone(phone).addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.e("Rio ","loi get data m02: "+ p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {

                Log.e("Rio ","login "+ p0.toString())
                if (p0.getValue() != null) {
                    Log.e("Rio ","login success have a info user:  "+ p0.getValue().toString())
//                    splashViewData.user=ConvertData.convertDataSnapshotToUser(p0)
                    getToMainEvent().call()
                }
                else{
                    Log.e("Rio ","user null! , go to login!")
                    getToLoginEvent().call()
                }

                FirebaseReferenceInstance.removeListener(FirebaseReferenceInstance.getUsersReference(),this);
            }

        })
    }


    fun getInfoUser(){

        FirebaseFirestoreInstance.getUserByLoginToken(splashViewData.loginToken)
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Log.e("Rio ","get user null!")
                    getToLoginEvent().call()
                } else {
                    for (document in documents) {

                        disposable.add(appDataManager.insertUser(document.toObject(User::class.java))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ u: User ->
                                Log.e("Rio", "login = token ok  : " + u.toString())
                                getToMainEvent().call()
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
}