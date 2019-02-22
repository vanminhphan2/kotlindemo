package com.example.rio.kottlin_demo.ui.chat

import android.text.Editable
import android.util.Log
import com.example.rio.kottlin_demo.data.AppDataManager
import com.example.rio.kottlin_demo.data.firebase.FirebaseReferenceInstance
import com.example.rio.kottlin_demo.data.model.Box
import com.example.rio.kottlin_demo.data.model.Message
import com.example.rio.kottlin_demo.ui.base.BaseViewModel
import com.example.rio.kottlin_demo.utils.AppConstants
import com.example.rio.kottlin_demo.utils.ConvertData
import com.example.rio.kottlin_demo.utils.SingleLiveEvent
import com.google.firebase.database.ChildEventListener
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

    fun setData(){
        disposable.add(
            appDataManager.getInfoUserLogin(chatViewData.user.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ u ->
                    Log.e("Rio ", "get info ok ee :   " + chatViewData.mainBox.toString())
                    chatViewData.user = u
                    chatViewData.isHadBox = true
                    chatViewData.userReceive.id=chatViewData.mainBox.id
                    onGetListChatSuccessEvent().call()
                    onGetMessListenerEvent()
                }, { thow ->
                    Log.e("Rio ", "loi 01204:   " + thow.message)
                })
        )
    }

    private fun getDataBox2User() {

        FirebaseReferenceInstance.getBoxBy2IdUser(chatViewData.user.id, chatViewData.userReceive.id)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("Rio ", "loi get data box: " + p0.message)
                    FirebaseReferenceInstance.removeListener(
                        FirebaseReferenceInstance.getSingleChatBoxsReference(),
                        this
                    )
                }

                override fun onDataChange(p0: DataSnapshot) {

                    Log.e("Rio ", "boxs 01:  " + p0.toString())
                    if (p0.value != null) {
                        Log.e("Rio ", "boxs 1122:  " + p0.toString())
                        chatViewData.isHadBox = true
                        chatViewData.mainBox  = p0.getValue(Box::class.java)!!
//                        chatViewData.mainBox  = ConvertData.convertSnapshotToBox(p0.child(chatViewData.userReceive.id))
                        onGetListChatSuccessEvent().call()
                        onGetMessListenerEvent()

                        Log.e("Rio ", "boxs 1133:  " + chatViewData.mainBox.toString())

                        FirebaseReferenceInstance.removeEventGetBoxBy2IdUser(
                            chatViewData.user.id, chatViewData.userReceive.id,
                            this
                        )
                    }
                }
            })
    }

    private fun onGetMessListenerEvent() {
                    Log.e("Rio ", "id 1 --> "+chatViewData.user.id+"    ---id2  ---> "+ chatViewData.userReceive.id)
        FirebaseReferenceInstance.getMessListener(chatViewData.user.id, chatViewData.userReceive.id)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {

//                    Log.e("Rio ", "onChildAdded: ${dataSnapshot.key}")
                    val mess = dataSnapshot.getValue(Message::class.java)
                    if (mess != null) {
                        chatViewData.mainBox.listMessage.add(mess)
                        onAddMessEvent().call()
                    }
                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
//                    Log.e("Rio ", "onChildChanged: ${dataSnapshot.key}")

                    val mess = dataSnapshot.getValue(Message::class.java)
                    val commentKey = dataSnapshot.key

                }

                override fun onChildRemoved(dataSnapshot: DataSnapshot) {
//                    Log.e("Rio ", "onChildRemoved: ${dataSnapshot.key}")
                    val commentKey = dataSnapshot.key
                }

                override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
//                    Log.e("Rio ", "onChildMoved: ${dataSnapshot.key}")

                    val mess = dataSnapshot.getValue(Message::class.java)
                    val commentKey = dataSnapshot.key

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("Rio ", "onGetMessListenerEvent:onCancelled", databaseError.toException())
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
            } else {
                createNewBox()
            }
        }
    }

    fun onUpdateContent(value: Editable) {
        chatViewData.contentMess = value.toString()
    }

    fun createNewBox() {

        chatViewData.mainBox.members = arrayListOf(chatViewData.user.id, chatViewData.userReceive.id)

        val mess = Message()
        mess.contentMess = chatViewData.contentMess
        mess.sendTime = AppConstants.getTimeNow()
        mess.stastus = "sent"
        mess.idUser = chatViewData.user.id
        mess.type = "text"
        mess.id = FirebaseReferenceInstance.getSingleChatBoxsReference().child(chatViewData.user.id).child(chatViewData.userReceive.id).push().key.toString()
        onClickSendEvent().call()
        onGetListChatSuccessEvent().call()

        FirebaseReferenceInstance.createSingleBox(
            chatViewData.user,
            chatViewData.userReceive, mess
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
        mess.id = FirebaseReferenceInstance.getSingleChatBoxsReference().child(chatViewData.user.id)
            .child(chatViewData.userReceive.id).push().key.toString()
//        onAddMessEvent().call()
        onClickSendEvent().call()
        Log.e("Rio ", "sendMessToServer 001 :"+  mess.toString())
        FirebaseReferenceInstance.createMessage(chatViewData.user.id, chatViewData.userReceive.id, mess)
    }

    fun getIdUserLogin() {
        chatViewData.user.id = appDataManager.getUserId()
    }
}