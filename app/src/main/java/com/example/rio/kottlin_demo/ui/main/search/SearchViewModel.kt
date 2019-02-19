package com.example.rio.kottlin_demo.ui.main.search

import android.util.Log
import com.example.rio.kottlin_demo.data.AppDataManager
import com.example.rio.kottlin_demo.data.firebase.FirebaseReferenceInstance
import com.example.rio.kottlin_demo.data.model.User
import com.example.rio.kottlin_demo.ui.base.BaseViewModel
import com.example.rio.kottlin_demo.utils.AppConstants
import com.example.rio.kottlin_demo.utils.ConvertData
import com.example.rio.kottlin_demo.utils.SingleLiveEvent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class SearchViewModel @Inject constructor(private var appDataManager: AppDataManager) :
    BaseViewModel<SearchViewData>() {

    var searchViewData:SearchViewData

    private val updateListUser: SingleLiveEvent<Void>

    fun getUpdateListUserEvent():SingleLiveEvent<Void>{
        return updateListUser
    }

    init {
        searchViewData = SearchViewData()
        setDataView()
        updateListUser=SingleLiveEvent()
    }

    fun setDataView() {
        viewData.setValue(searchViewData)
    }

    fun getListUser(){
        FirebaseReferenceInstance.getUsersReference().addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.e("Rio ","loi get data m03: "+ p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.getValue() != null) {
                    searchViewData.listUser=ConvertData.convertDataSnapshotToArrUser(p0)
                    Log.e("Rio ","list data user--> "+searchViewData.listUser.toString())
                    getUpdateListUserEvent().call()
                }
                else{
                    Log.e("Rio ","data null")
                }
                FirebaseReferenceInstance.removeListener(FirebaseReferenceInstance.getUsersReference(),this);
            }

        })
    }
}