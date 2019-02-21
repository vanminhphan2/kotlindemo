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
import javax.inject.Inject

class ChatViewModel @Inject constructor(private var appDataManager: AppDataManager) : BaseViewModel<ChatViewData>() {

    var chatViewData: ChatViewData

    private val onClickBack: SingleLiveEvent<Void> = SingleLiveEvent()
    private val onClickSend: SingleLiveEvent<Void> = SingleLiveEvent()
    private val getListChatSuccess: SingleLiveEvent<Void> = SingleLiveEvent()
    private val onAddMess: SingleLiveEvent<Void> = SingleLiveEvent()

    fun onClickBackEvent(): SingleLiveEvent<Void> {
        return onClickBack
    }

    fun onClickSendEvent(): SingleLiveEvent<Void> {
        return onClickSend
    }

    fun onGetListChatSuccessEvent(): SingleLiveEvent<Void> {
        return getListChatSuccess
    }

    fun onAddMessEvent(): SingleLiveEvent<Void> {
        return onAddMess
    }

    init {
        chatViewData = ChatViewData()
    }

    fun bindInfoUserReceive() {

    }

    fun getBoxDataFromServer() {

        disposable.add(
            appDataManager.getInfoUserLogin(chatViewData.user.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ u ->
                    Log.e("Rio ", "get info ok :   " + u.toString())
                    chatViewData.user = u
                    getDataBox2User()
                }, { thow ->
                    Log.e("Rio ", "loi 01201:   " + thow.message)
                })
        )

    }

    private fun getDataBox2User() {

        FirebaseReferenceInstance.getBoxByIdUser(chatViewData.user.id)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("Rio ", "loi get data box: " + p0.message)
                    FirebaseReferenceInstance.removeListener(
                        FirebaseReferenceInstance.getBoxsReference(),
                        this
                    )
                }

                override fun onDataChange(p0: DataSnapshot) {

                    Log.e("Rio ", "boxs 01:  " + p0.toString())
                    if (p0.getValue() != null) {

                        for (p in p0.getChildren()) {
                            if (p.child("members").value != null) {
                                val data = ConvertData.convertDataSnapshotToListMember(p.child("members"))
                                if (data.size == 2 && data.contains(chatViewData.userReceive.id)) {
                                    chatViewData.isHadBox = true
                                    chatViewData.mainBox = ConvertData.convertSnapshotToBox(p)
                                }
                            }
                        }

                        if (chatViewData.isHadBox) {
                            Log.e("Rio ", "da co box--- " + chatViewData.mainBox.toString())
                            geListMessageDataById()   //get data of box
                        }

                    }
                    FirebaseReferenceInstance.removeEventGetBoxByIdUser(
                        chatViewData.user.id,
                        this
                    )
                }

            })
    }

    private fun geListMessageDataById() {
        FirebaseReferenceInstance.getListMessageByIdBox(chatViewData.mainBox.id)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("Rio ", "loi 01211:   " + p0.message)

                    FirebaseReferenceInstance.removeListener(
                        FirebaseReferenceInstance.getMessagesReference().child(chatViewData.mainBox.id),
                        this
                    )
                }

                override fun onDataChange(p0: DataSnapshot) {
                    Log.e("Rio ", "get list mess success:   " + p0.toString())
                    chatViewData.listChat = ConvertData.convertDataSnapshotToListMessage(p0)
                    onGetListChatSuccessEvent().call()

                }
            })
    }

    fun onBackClick() {
        onClickBackEvent().call()
    }

    fun onSendClick() {
        if (!chatViewData.contentMess.isEmpty()) {
            if (chatViewData.isHadBox) {
                sendMessToServer()
                onClickSendEvent().call()
            } else {

                FirebaseReferenceInstance.getBoxByIdUser(chatViewData.user.id)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            Log.e("Rio ", "loi get data box: " + p0.message)
                            FirebaseReferenceInstance.removeListener(
                                FirebaseReferenceInstance.getBoxsReference(),
                                this
                            )
                        }

                        override fun onDataChange(p0: DataSnapshot) {

                            Log.e("Rio ", "boxs111:  " + p0.toString())
                            if (p0.getValue() != null) {

                                Log.e("Rio ", "boxs222:  " + p0.getValue().toString())
                                for (p in p0.getChildren()) {

                                    Log.e("Rio ", "p0 to string:  " + p.toString())
                                    val data = ConvertData.convertDataSnapshotToListMember(p.child("members"))
                                    if (data.size == 2 && data.contains(chatViewData.user.id) && data.contains(
                                            chatViewData.userReceive.id
                                        )
                                    ) {
                                        Log.e("Rio ", "p0 to hereeeee:  " + p.toString())
                                        chatViewData.isHadBox = true
                                        chatViewData.mainBox = ConvertData.convertSnapshotToBox(p)
                                    }

                                }

                                if (chatViewData.isHadBox) {
                                    Log.e("Rio ", "da co box--- " + chatViewData.mainBox.toString())
                                    geListMessageDataById()

                                    sendMessToServer()
                                    onClickSendEvent().call()
                                } else {
                                    createNewBox()
                                    sendMessToServer()
                                    onClickSendEvent().call()
                                    FirebaseReferenceInstance.removeEventGetBoxByIdUser(chatViewData.user.id, this)
                                }
                            } else {
                                createNewBox()
                                sendMessToServer()
                                onClickSendEvent().call()
                                FirebaseReferenceInstance.removeEventGetBoxByIdUser(chatViewData.user.id, this)
                            }

                        }

                    })

            }
        }
    }

    fun onUpdateContent(value: Editable) {
        chatViewData.contentMess = value.toString()
    }

    fun createNewBox() {
        Log.e("Rio ", "boxs null:-->create new box  ")
//        chatViewData.mainBox.id = FirebaseReferenceInstance.getBoxsReference().push().key.toString()

        chatViewData.mainBox.members = arrayListOf(chatViewData.user.id, chatViewData.userReceive.id)

        FirebaseReferenceInstance.createSingleBox(
//            chatViewData.mainBox.id,
            chatViewData.user,
            chatViewData.userReceive
        )
        chatViewData.isHadBox = true
    }

    fun sendMessToServer() {
        val mess = Message()
        mess.contentMess = chatViewData.contentMess
        mess.sendTime = AppConstants.getTimeNow()
        mess.stastus = "sent"
        mess.idUser = chatViewData.user.id
        mess.type = "text"
        chatViewData.listChat.add(mess)
        onAddMessEvent().call()
        mess.id=FirebaseReferenceInstance.getMessagesReference().push().key.toString()
        for (id in 0 until chatViewData.mainBox.members.size) {
            FirebaseReferenceInstance.createMessage(chatViewData.mainBox.members[id], mess)
        }
    }

    fun getIdUserLogin() {
        chatViewData.user.id = appDataManager.getUserId()
    }
}