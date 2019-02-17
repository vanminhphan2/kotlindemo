package com.example.rio.kottlin_demo.ui.splash

import android.util.Log
import com.example.rio.kottlin_demo.data.AppDataManager
import com.example.rio.kottlin_demo.data.model.User
import com.example.rio.kottlin_demo.ui.base.BaseViewModel
import com.example.rio.kottlin_demo.utils.SingleLiveEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class SplashViewModel @Inject constructor(private var appDataManager: AppDataManager) :
    BaseViewModel<SplashViewData>() {

    var user: User? = null
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("users")
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
//        if(appDataManager.getLoginToken().equals("")||appDataManager.getLoginToken()==null){
//            getToLoginEvent().call()
//        }
//        else{
//            getToMainEvent().call()
//        }

        if (FirebaseAuth.getInstance().currentUser != null) {
            myRef.child(FirebaseAuth.getInstance().currentUser!!.uid)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            val map = dataSnapshot.getValue()

                            if (map is Map<*, *>) {
                                val id = map["idUser"].toString()
                                val userName = map["name"].toString()
                                val phone = map["phone"].toString()
                                val pass = map["pass"].toString()
                                user = User(id, userName, phone, pass)
//                                getToMainEvent().call()
                                getToLoginEvent().call()
                            } else {
                                Log.e("Rio", "loi map is Map<*, *>")
                                getToLoginEvent().call()
                            }

                        } else {
                            getToLoginEvent().call()
                            Log.e("Rio", "dataSnapshot.getValue() USER INFO IN SERVER null")

                        }
                        myRef.removeEventListener(this);
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                        Log.e("Rio", "Failed to read value.", error.toException())
                    }
                })
            Log.e("Rio", "currentUser da login  :")
        } else {
            Log.e("Rio", "currentUser chuaaa login  :")
            getToLoginEvent().call()
        }
    }
}