package com.example.rio.kottlin_demo.ui.login

import android.text.Editable
import android.util.Log
import com.example.rio.kottlin_demo.data.AppDataManager
import com.example.rio.kottlin_demo.data.model.User
import com.example.rio.kottlin_demo.ui.base.BaseViewModel
import com.example.rio.kottlin_demo.utils.SingleLiveEvent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class LoginViewModel @Inject constructor(private var appDataManager: AppDataManager):BaseViewModel<LoginViewData>(){

    var user: User? = null
    var loginViewData:LoginViewData
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("users")
    private val toRegister: SingleLiveEvent<Void>
    private val toMain: SingleLiveEvent<Void>

    fun getToRegisterEvent(): SingleLiveEvent<Void> {
        return toRegister
    }

    fun getToMainEvent(): SingleLiveEvent<Void> {
        return toMain
    }

    init {
        loginViewData= LoginViewData()
        setDataView()

        toRegister= SingleLiveEvent()
        toMain= SingleLiveEvent()
    }

    fun setDataView(){
        viewData.setValue(loginViewData)
    }

    fun onServerLoginClick(){
        if(loginViewData.phone.length<10)
        {
            loginViewData.message="Name is incorrect!"
            onShowMessageEvent().call()
        }else if(loginViewData.pass.length<6){
            loginViewData.message="Pass is incorrect!"
            onShowMessageEvent().call()
        }else{
            onLoginServer()
        }
    }

    fun onRegisterClick(){
        getToRegisterEvent().call()
    }

    fun updatePhone(phone: Editable) {
        loginViewData.phone=phone.toString()
    }

    fun updatePass(pass: Editable) {
        loginViewData.pass=pass.toString()
    }

    fun onLoginServer(){
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.getValue() != null){

                    var phoneIsSuccess=false
                    var passIsSuccess=false
                    for ( postSnapshot in dataSnapshot.getChildren()) {
                        if(loginViewData.phone.equals(postSnapshot.child("phone").value)){
                            phoneIsSuccess=true
                            if(loginViewData.pass.equals(postSnapshot.child("pass").value)){
                                passIsSuccess=true
                                loginViewData.name= postSnapshot.child("name").value.toString()
                                loginViewData.name= postSnapshot.key.toString()
                            }
                        }
                    }
                    if(phoneIsSuccess&&passIsSuccess){
                        getToMainEvent().call()
                        Log.e("Rio", "login is ok")
                    }
                    else{
                        if(!phoneIsSuccess){
                            loginViewData.message="Phone is incorrect!"
                            Log.e("Rio", "Phone is incorrect!")
                        }else{
                            loginViewData.message="Pass is incorrect!"
                            Log.e("Rio", "Pass is incorrect!")
                        }
                        hideLoading()
                        onShowMessageEvent().call()
                    }
                }else{
                    Log.e("Rio", "dataSnapshot.getValue() null")
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