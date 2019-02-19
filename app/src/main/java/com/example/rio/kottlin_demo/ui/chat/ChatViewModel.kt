package com.example.rio.kottlin_demo.ui.chat

import android.util.Log
import com.example.rio.kottlin_demo.data.AppDataManager
import com.example.rio.kottlin_demo.data.firebase.FirebaseReferenceInstance
import com.example.rio.kottlin_demo.ui.base.BaseViewModel
import com.example.rio.kottlin_demo.utils.ConvertData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class ChatViewModel@Inject constructor(private var appDataManager: AppDataManager): BaseViewModel<ChatViewData>(){

    var chatViewData:ChatViewData

    init {
        chatViewData=ChatViewData()
    }

    fun getBoxDataFromServer(){

//        FirebaseReferenceInstance.getUserByPhone(phone).addValueEventListener(object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//                Log.e("Rio ","loi get data m02: "+ p0.message)
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//
//                Log.e("Rio ","login "+ p0.toString())
//                if (p0.getValue() != null) {
//                    Log.e("Rio ","login success have a info user:  "+ p0.getValue().toString())
//                    splashViewData.user= ConvertData.convertDataSnapshotToUser(p0)
//                    getToMainEvent().call()
//                }
//                else{
//                    Log.e("Rio ","user null! , go to login!")
//                    getToLoginEvent().call()
//                }
//
//                FirebaseReferenceInstance.removeListener(FirebaseReferenceInstance.getUsersReference(),this);
//            }
//
//        })
    }
}