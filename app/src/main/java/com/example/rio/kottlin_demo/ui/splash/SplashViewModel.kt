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
    val myRefSession = database.getReference("sessions")
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
        Log.e("Rio", "token: "+appDataManager.getLoginToken())
        if(appDataManager.getLoginToken().equals("")||appDataManager.getLoginToken()==null){
            getToLoginEvent().call()
        }
        else{
            var phone=""
            myRefSession.child(appDataManager.getLoginToken().toString()).addValueEventListener(object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                   Log.e("Rio ","loi get data Session")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.getValue() != null) {
                       phone = p0.getValue().toString()
                        Log.e("Rio ","phone : "+ p0.getValue().toString())
                        getInfoUser(phone)
                    }
                    else{
                        getToLoginEvent().call()
                        Log.e("Rio ","ko co token!")
                    }
                    myRefSession.removeEventListener(this);
                }

            })
//            if(!phone.equals("")){
//
//                Log.e("Rio", "currentUser da login  :")
//            }
//            else{
//                Log.e("Rio", "currentUser chuaurrentUser chuaaa logi 1111223232  :")
//                getToLoginEvent().call()
//            }
        }

//        if (FirebaseAuth.getInstance().currentUser != null) {
//            myRef.child(FirebaseAuth.getInstance().currentUser!!.uid)
//                .addValueEventListener(object : ValueEventListener {
//                    override fun onDataChange(dataSnapshot: DataSnapshot) {
//                        if (dataSnapshot.getValue() != null) {
//                            val map = dataSnapshot.getValue()
//
//                            if (map is Map<*, *>) {
//                                val id = map["idUser"].toString()
//                                val userName = map["name"].toString()
//                                val phone = map["phone"].toString()
//                                val pass = map["pass"].toString()
//                                user = User(id, userName, phone, pass)
////                                getToMainEvent().call()
//                                getToLoginEvent().call()
//                            } else {
//                                Log.e("Rio", "loi map is Map<*, *>")
//                                getToLoginEvent().call()
//                            }
//
//                        } else {
//                            getToLoginEvent().call()
//                            Log.e("Rio", "dataSnapshot.getValue() USER INFO IN SERVER null")
//
//                        }
//                        myRef.removeEventListener(this);
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        // Failed to read value
//                        Log.e("Rio", "Failed to read value.", error.toException())
//                    }
//                })
//            Log.e("Rio", "currentUser da login  :")
//        } else {
//            Log.e("Rio", "currentUser chuaaa login  :")
//            getToLoginEvent().call()
//        }
    }

    fun getInfoUser(phone:String){
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.getValue() != null) {

                    var isHaveAccount=false
                    for (snapshot in dataSnapshot.getChildren()) {
                        if( snapshot.child("phone").value!!.equals(phone)){
                            isHaveAccount=true
                            val map = snapshot.getValue()

                            if (map is Map<*, *>) {
                                val id = map["idUser"].toString()
                                val userName = map["name"].toString()
                                val pass = map["pass"].toString()
                                user = User(id, userName, phone, pass)
//                                getToMainEvent().call()
//                                            getToLoginEvent().call()
                            }

                        }
                    }
                    if(isHaveAccount){
                        getToMainEvent().call()
                        Log.e("Rio", "User da login  :")
                        myRef.removeEventListener(this);
                    }else {
                        Log.e("Rio", "loi map is Map<*, *>")
                        getToLoginEvent().call()
                        myRef.removeEventListener(this);
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
    }
}