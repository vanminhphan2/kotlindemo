package com.example.rio.kottlin_demo.ui.chat

import android.text.Editable
import android.util.Log
import com.example.rio.kottlin_demo.data.AppDataManager
import com.example.rio.kottlin_demo.data.firebase.FirebaseReferenceInstance
import com.example.rio.kottlin_demo.data.model.Message
import com.example.rio.kottlin_demo.ui.base.BaseViewModel
import com.example.rio.kottlin_demo.utils.AppConstants
import com.example.rio.kottlin_demo.utils.ConvertData
import com.example.rio.kottlin_demo.utils.SingleLiveEvent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import javax.inject.Inject
import java.util.*


class ChatViewModel @Inject constructor(private var appDataManager: AppDataManager) : BaseViewModel<ChatViewData>() {

    var chatViewData: ChatViewData

    private val onClickBack: SingleLiveEvent<Void> = SingleLiveEvent()
    private val onClickSend: SingleLiveEvent<Void> = SingleLiveEvent()

    fun onClickBackEvent():SingleLiveEvent<Void>{
        return onClickBack
    }

    fun onClickSendEvent():SingleLiveEvent<Void>{
        return onClickSend
    }

    init {
        chatViewData = ChatViewData()
    }

    fun getBoxDataFromServer() {

        chatViewData.idUserLogin = appDataManager.getUserId()
        disposable.add(appDataManager.getInfoUserLogin(chatViewData.idUserLogin)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ u ->
                Log.e("Rio ", "get info ok :   " + u.toString())
                chatViewData.user = u

                Log.e("Rio ", "id1:   " + chatViewData.idUserLogin + "    id2: " + chatViewData.idUserReceive)
                FirebaseReferenceInstance.getBoxBy2IdUser().addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        Log.e("Rio ", "loi get data box: " + p0.message)
                    }

                    override fun onDataChange(p0: DataSnapshot) {

                        Log.e("Rio ", "boxs:  " + p0.toString())
                        if (p0.getValue() != null) {
                            var check=false
                            for (p in p0.getChildren()) {
                                val data = JSONArray(p.child("members").value.toString())
                                if (data.length() == 2) {
                                    val s1 = arrayOf(chatViewData.idUserLogin, chatViewData.idUserReceive)
                                    val s2 = arrayOf(data.get(0), data.get(1))
                                    if (Arrays.equals(s1, s2)) {
                                        check=true
                                        chatViewData.mainBox=ConvertData.convertSnapshotToBox(p)

                                    }
                                }

                            }

                            if(check){
                                Log.e("Rio ", "da co box--- "+  chatViewData.mainBox.toString())

                            }
                            else{
                                createNewBox()
                            }

                        } else {
                            createNewBox()
                        }


                        FirebaseReferenceInstance.removeListener(
                            FirebaseReferenceInstance.getBoxsReference(),
                            this
                        );
                    }

                })
            }, { thow ->
                Log.e("Rio ", "loi 01201:   " + thow.message)
            })
        )

    }

    fun onBackClick(){
        onClickBackEvent().call()
    }

    fun onSendClick(){
        if(!chatViewData.contentMess.isEmpty()){
            sendMessToServer()
            onClickSendEvent().call()
        }
    }

    fun onUpdateContent(value: Editable){
        chatViewData.contentMess=value.toString()
    }

    fun  sendMessToServer(){
        val mess=Message()
        mess.contentMess=chatViewData.contentMess
        mess.sendTime=AppConstants.getTimeNow()
        mess.stastus="sent"
        mess.idUser=chatViewData.idUserLogin
        mess.type="text"
        chatViewData.listChat.add(mess)
        FirebaseReferenceInstance.createMessage(chatViewData.mainBox.id,mess)
    }

    fun createNewBox(){
        Log.e("Rio ", "boxs null:-->create new box  ")
        chatViewData.mainBox.id=FirebaseReferenceInstance.getBoxsReference().push().key.toString()
        FirebaseReferenceInstance.createSingleBox(chatViewData.mainBox.id,
            chatViewData.idUserLogin,
            chatViewData.idUserReceive
        )
    }
}