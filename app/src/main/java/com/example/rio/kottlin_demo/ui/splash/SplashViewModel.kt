package com.example.rio.kottlin_demo.ui.splash

import android.util.Log
import com.example.rio.kottlin_demo.data.AppDataManager
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
        Log.e("Rio", "token local: "+appDataManager.getLoginToken())
        if(appDataManager.getLoginToken().equals("")||appDataManager.getLoginToken()==null){
            getToLoginEvent().call()
        }
        else{
            var phone:String

            FirebaseReferenceInstance.checkPhoneByToken(appDataManager.getLoginToken().toString()).addValueEventListener(object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                   Log.e("Rio ","loi get data m01: "+ p0.message)
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.getValue() != null) {
                       phone = p0.getValue().toString()
                        Log.e("Rio ","login = token va phone = "+ p0.getValue().toString())
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
                    splashViewData.user=ConvertData.convertDataSnapshotToUser(p0)
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
}