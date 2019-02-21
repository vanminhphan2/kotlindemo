package com.example.rio.kottlin_demo.ui.main.boxs

import android.util.Log
import com.example.rio.kottlin_demo.data.AppDataManager
import com.example.rio.kottlin_demo.data.firebase.FirebaseReferenceInstance
import com.example.rio.kottlin_demo.ui.base.BaseViewModel
import com.example.rio.kottlin_demo.utils.ConvertData
import com.example.rio.kottlin_demo.utils.SingleLiveEvent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BoxsViewModel @Inject constructor(private var appDataManager: AppDataManager) : BaseViewModel<BoxsViewData>(){


    var boxsViewData:BoxsViewData
    private val getListBoxSuccess: SingleLiveEvent<Void> = SingleLiveEvent()

    private val updateListBox=SingleLiveEvent<Void>()

    fun getListBoxSuccessEvent(): SingleLiveEvent<Void> {
        return updateListBox
    }

    fun getUpdateListBoxEvent(): SingleLiveEvent<Void> {
        return getListBoxSuccess
    }

    init {
        boxsViewData= BoxsViewData()
        setDataView()
    }


    fun setDataView() {
        viewData.setValue(boxsViewData)
    }

    fun getUserLogin() {
        disposable.add(
            appDataManager.getInfoUserLogin(boxsViewData.user.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ u ->
                    Log.e("Rio ", "get info ok :   " + u.toString())
                    boxsViewData.user = u
                    getListBox()
                }, { thow ->
                    Log.e("Rio ", "loi 01201:   " + thow.message)
                })
        )
    }

    fun getListBox() {

        FirebaseReferenceInstance.getListIdBoxByUserId(boxsViewData.user.id).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e("Rio ", "loi get data m03: " + p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.getValue() != null) {

                    boxsViewData.listBox= ConvertData.convertDataSnapshotToListBox(p0)
                    if (boxsViewData.listBox.size>0){
                        getListBoxSuccessEvent().call()
                    }

                } else {
                    Log.e("Rio ", "data null")
                }
                FirebaseReferenceInstance.removeListener(FirebaseReferenceInstance.getBoxsReference(), this);
            }

        })
    }

}