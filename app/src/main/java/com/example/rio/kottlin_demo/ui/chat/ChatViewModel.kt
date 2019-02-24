package com.example.rio.kottlin_demo.ui.chat

import android.text.Editable
import android.util.Log
import com.example.rio.kottlin_demo.data.AppDataManager
import com.example.rio.kottlin_demo.data.firebase.FirebaseFirestoreInstance
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
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
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
//                    getDataBox2User()
                    getDataBoxFromFireStore()
                }, { thow ->
                    Log.e("Rio ", "loi 01201:   " + thow.message)
                })
        )

    }

    fun setData() {
        disposable.add(
            appDataManager.getInfoUserLogin(chatViewData.user.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ u ->
                    Log.e("Rio ", "get info ok ee :   " + chatViewData.mainBox.toString())
                    chatViewData.user = u
                    chatViewData.isHadBox = true
                    chatViewData.userReceive.id = chatViewData.mainBox.id
                    getListMessFromFireStore()
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
                        chatViewData.mainBox = p0.getValue(Box::class.java)!!
                        onGetListChatSuccessEvent().call()
                        onGetMessFromRealTimeDbListenerEvent()

                        Log.e("Rio ", "boxs 1133:  " + chatViewData.mainBox.toString())

                        FirebaseReferenceInstance.removeEventGetBoxBy2IdUser(
                            chatViewData.user.id, chatViewData.userReceive.id,
                            this
                        )
                    }
                }
            })
    }

    private fun getDataBoxFromFireStore() {
        FirebaseFirestoreInstance.getBoxBy2IdUser(chatViewData.user.id, chatViewData.userReceive.id)
            .addOnSuccessListener { documents ->
                if (documents != null && documents.exists()) {
                    chatViewData.mainBox = documents.toObject(Box::class.java)!!
                    chatViewData.isHadBox = true
                    Log.e("Rio ", "boxs 1144:  " + chatViewData.mainBox.toString())
                    FirebaseFirestoreInstance.getBoxBy2IdUser(chatViewData.userReceive.id, chatViewData.user.id)
                        .addOnSuccessListener { document ->
                            if (document != null && document.exists()) {
                                chatViewData.boxReceive = document.toObject(Box::class.java)!!
                                chatViewData.idListMessageReceive = chatViewData.boxReceive.idListMessage
                                Log.e("Rio ", "boxs 114455:  " + chatViewData.boxReceive.toString())
                                getListMessFromFireStore()

                            } else {
                                Log.e("Rio ", "getDataBoxFromFireStore documents.isEmpty!")
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.e("Rio", "checkExitsPhoneOnFireStore  error: " + exception.message)
                        }
                } else {
                    Log.e("Rio ", "getDataBoxFromFireStore documents.isEmpty!")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Rio", "checkExitsPhoneOnFireStore  error: " + exception.message)

            }
    }

    private fun getListMessFromFireStore() {
        FirebaseFirestoreInstance.getListMess(chatViewData.mainBox.idListMessage)
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.e("Rio", document.id + " => " + document.data)
                    chatViewData.listMess.add(document.toObject(Message::class.java))
                }
                onGetListChatSuccessEvent().call()
                onAddMessEvent().call()
                onGetMessFromFireStoreListenerEvent()
                Log.e("Rio", "Lay list xong")

            }
            .addOnFailureListener { exception ->
                Log.e("Rio", "Error getting documents: ", exception)
            }
    }

    private fun onGetMessFromRealTimeDbListenerEvent() {
        Log.e("Rio ", "id 1 --> " + chatViewData.user.id + "    ---id2  ---> " + chatViewData.userReceive.id)
        FirebaseReferenceInstance.getMessListener(chatViewData.user.id, chatViewData.userReceive.id)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {

//                    Log.e("Rio ", "onChildAdded: ${dataSnapshot.key}")
                    val mess = dataSnapshot.getValue(Message::class.java)
                    if (mess != null) {
//                        chatViewData.mainBox.listMessage.add(mess)
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

    private fun onGetMessFromFireStoreListenerEvent() {

        FirebaseFirestoreInstance.getListMessagesCollection().document(chatViewData.mainBox.idListMessage)
            .collection(chatViewData.mainBox.idListMessage)
            .addSnapshotListener(EventListener<QuerySnapshot> { snapshots, e ->
                if (e != null) {
                    Log.e("Rio", "onGetMessFromFireStoreListenerEvent failed.", e)
                    return@EventListener
                }
                Log.e("Rio", "dang ki ok")

                for (dc in snapshots!!.documentChanges) {
                    when (dc.type) {
                        DocumentChange.Type.ADDED -> {
                            chatViewData.listMess.add(dc.document.toObject(Message::class.java))
                            onAddMessEvent().call()
                        }
                        DocumentChange.Type.MODIFIED -> Log.e("Rio", "Modified listMess: " + dc.document.data)
                        DocumentChange.Type.REMOVED -> Log.e("Rio", "Removed listMess: " + dc.document.data)
                    }
                }
            })
    }

    fun onBackClick() {
        onClickBackEvent().call()
    }

    fun onSendClick() {
        if (!chatViewData.contentMess.isEmpty()) {
            if (chatViewData.isHadBox) {
//                sendMessToServer()
                sendSingleMessToFireStore()
            } else {
//                createNewBox()
                createNewBoxToFireStore()
            }
        }
    }

    fun onUpdateContent(value: Editable) {
        chatViewData.contentMess = value.toString()
    }

    fun createNewBox() {

        val mess = Message()
        mess.contentMess = chatViewData.contentMess
        mess.sendTime = AppConstants.getTimeNow()
        mess.stastus = "sent"
        mess.idUser = chatViewData.user.id
        mess.type = "text"
        mess.id = FirebaseReferenceInstance.getSingleChatBoxsReference().child(chatViewData.user.id)
            .child(chatViewData.userReceive.id).push().key.toString()
        onClickSendEvent().call()
        onGetListChatSuccessEvent().call()

        FirebaseReferenceInstance.createSingleBox(
            chatViewData.user,
            chatViewData.userReceive, mess
        )
        chatViewData.isHadBox = true

    }

    private fun createNewBoxToFireStore() {


        chatViewData.mainBox.id = chatViewData.userReceive.id
        chatViewData.mainBox.avatar = chatViewData.userReceive.avatar
        chatViewData.mainBox.name = chatViewData.userReceive.name
        chatViewData.mainBox.content = chatViewData.contentMess
        chatViewData.mainBox.isBlock = false
        chatViewData.mainBox.members = arrayListOf()
        chatViewData.mainBox.type = "single"
        chatViewData.mainBox.idListMessage = AppConstants.generateTokenString()

        val boxUserReceive = Box()
        boxUserReceive.id = chatViewData.user.id
        boxUserReceive.avatar = chatViewData.user.avatar
        boxUserReceive.name = chatViewData.user.name
        chatViewData.mainBox.content = chatViewData.contentMess
        chatViewData.mainBox.isBlock = false
        chatViewData.mainBox.members = arrayListOf()
        chatViewData.mainBox.type = "single"
        boxUserReceive.idListMessage = AppConstants.generateTokenString()
        chatViewData.idListMessageReceive = boxUserReceive.idListMessage


        FirebaseFirestoreInstance.createSingleBox(chatViewData.user.id, chatViewData.mainBox)
            .addOnSuccessListener {
                onGetMessFromFireStoreListenerEvent()
                FirebaseFirestoreInstance.createSingleBox(chatViewData.userReceive.id, boxUserReceive)
                    .addOnSuccessListener {
                        chatViewData.isHadBox = true
                        sendSingleMessToFireStore()
                    }
                    .addOnFailureListener { e ->

                        Log.e("Rio", "Error createNewBoxToFireStore: ", e)
                    }
            }
            .addOnFailureListener { e ->

                Log.e("Rio", "Error createNewBoxToFireStore: ", e)
            }
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
        onClickSendEvent().call()
        Log.e("Rio ", "sendMessToServer 001 :" + mess.toString())
        FirebaseReferenceInstance.createMessage(chatViewData.user.id, chatViewData.userReceive.id, mess)
    }

    private fun sendSingleMessToFireStore() {

        chatViewData.messSent.contentMess = chatViewData.contentMess
        chatViewData.messSent.sendTime = AppConstants.getTimeNow()
        chatViewData.messSent.stastus = "sent"
        chatViewData.messSent.idUser = chatViewData.user.id
        chatViewData.messSent.type = "text"
        chatViewData.messSent.id = FirebaseFirestoreInstance.getListMessagesCollection()
            .document(chatViewData.mainBox.idListMessage)
            .collection(chatViewData.mainBox.idListMessage).document().id

        chatViewData.messReceive.contentMess = chatViewData.contentMess
        chatViewData.messReceive.sendTime = AppConstants.getTimeNow()
        chatViewData.messReceive.stastus = "sent"
        chatViewData.messReceive.type = "text"
        chatViewData.messReceive.idUser = chatViewData.idListMessageReceive
        chatViewData.messReceive.id = FirebaseFirestoreInstance.getListMessagesCollection()
            .document(chatViewData.idListMessageReceive)
            .collection(chatViewData.idListMessageReceive).document().id

        FirebaseFirestoreInstance.createSingleMess(chatViewData.mainBox.idListMessage, chatViewData.messSent)
//            .addOnSuccessListener {
        chatViewData.messSent = Message()
        FirebaseFirestoreInstance.createSingleMess(chatViewData.idListMessageReceive, chatViewData.messReceive)
//                    .addOnSuccessListener {
        chatViewData.messReceive = Message()
        onClickSendEvent().call()
        Log.e("Rio", "send mess ok ")
//                    }
//                    .addOnFailureListener { e ->

//                        Log.e("Rio", "Error createNewBoxToFireStore: ", e)
//                    }
//            }
//            .addOnFailureListener { e ->

//                Log.e("Rio", "Error createNewBoxToFireStore: ", e)
//            }

    }

    fun getIdUserLogin() {
        chatViewData.user.id = appDataManager.getUserId()
        Log.e("Rio", "getIdUserLogin : " + chatViewData.user.id)
    }
}