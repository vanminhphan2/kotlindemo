package com.example.rio.kottlin_demo.ui.main.boxs

import android.util.Log
import com.example.rio.kottlin_demo.data.AppDataManager
import com.example.rio.kottlin_demo.data.firebase.FirebaseReferenceInstance
import com.example.rio.kottlin_demo.data.model.Box
import com.example.rio.kottlin_demo.data.model.Message
import com.example.rio.kottlin_demo.ui.base.BaseViewModel
import com.example.rio.kottlin_demo.utils.ConvertData
import com.example.rio.kottlin_demo.utils.SingleLiveEvent
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BoxsViewModel @Inject constructor(private var appDataManager: AppDataManager) : BaseViewModel<BoxsViewData>() {


    var boxsViewData: BoxsViewData
    private val getListBoxSuccess: SingleLiveEvent<Void> = SingleLiveEvent()
    private val onAddBox: SingleLiveEvent<Void> = SingleLiveEvent()

    private val updateListBox = SingleLiveEvent<Void>()

    fun getListBoxSuccessEvent(): SingleLiveEvent<Void> {
        return updateListBox
    }

    fun getUpdateListBoxEvent(): SingleLiveEvent<Void> {
        return getListBoxSuccess
    }

    fun onAddBoxEvent(): SingleLiveEvent<Void> {
        return onAddBox
    }

    init {
        boxsViewData = BoxsViewData()
        setDataView()
    }


    fun setDataView() {
        viewData.setValue(boxsViewData)
    }

    fun getUserLogin() {
        disposable.add(
            appDataManager.getInfoUserLogin(appDataManager.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ u ->
                    boxsViewData.user = u
                    getListBox()
                }, { thow ->
                    Log.e("Rio ", "loi 01202:   " + thow.message)
                })
        )
    }

    fun getListBox() {

        FirebaseReferenceInstance.getListIdBoxByUserId(boxsViewData.user.id)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("Rio ", "loi get data boxs: " + p0.message)
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.getValue() != null) {

//                    boxsViewData.listBox=ConvertData.convertDataSnapshotToListBox(p0)
                        for (item in p0.children) {
                            val box = item.getValue(Box::class.java)
                            if (box != null) {
                                for (itemMess in item.child("listMess").children) {
                                    val mess = itemMess.getValue(Message::class.java)
                                    if (mess != null) {
                                        mess.id = itemMess.key.toString()
                                        box.listMessage.add(mess)
                                    }
                                }
                                box.id = item.key.toString()

                                boxsViewData.listBox.add(box)
                            }
                        }
                        if (boxsViewData.listBox.size > 0) {
                            getListBoxSuccessEvent().call()
                        }

                    } else {
                        Log.e("Rio ", "data null")
                    }
                    onDataListBoxListener()
                    FirebaseReferenceInstance.removeEventGetBoxByIdUser(boxsViewData.user.id, this);
                }

            })
    }

    fun onDataListBoxListener() {
        FirebaseReferenceInstance.getListIdBoxByUserId(boxsViewData.user.id)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {

                    val box = dataSnapshot.getValue(Box::class.java)
                    if (box != null) {
                        boxsViewData.listBox.add(ConvertData.convertDataSnapshotToBox(box, dataSnapshot))
                        onAddBoxEvent().call()
                    }
                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
//                    Log.e("Rio ", "onChildChanged: ${dataSnapshot.key}")

                    val mess = dataSnapshot.getValue(Box::class.java)
                    val commentKey = dataSnapshot.key

                }

                override fun onChildRemoved(dataSnapshot: DataSnapshot) {
//                    Log.e("Rio ", "onChildRemoved: ${dataSnapshot.key}")
                    val commentKey = dataSnapshot.key
                }

                override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
//                    Log.e("Rio ", "onChildMoved: ${dataSnapshot.key}")

                    val mess = dataSnapshot.getValue(Box::class.java)
                    val commentKey = dataSnapshot.key

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("Rio ", "onDataListBoxListener:onCancelled", databaseError.toException())
                }
            })
    }
}