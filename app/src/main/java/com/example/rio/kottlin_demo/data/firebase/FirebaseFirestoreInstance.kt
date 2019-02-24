package com.example.rio.kottlin_demo.data.firebase

import android.annotation.SuppressLint
import android.util.Log
import com.example.rio.kottlin_demo.data.model.Box
import com.example.rio.kottlin_demo.data.model.Message
import com.example.rio.kottlin_demo.data.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*

object FirebaseFirestoreInstance{

    @SuppressLint("StaticFieldLeak")
    private val db= FirebaseFirestore.getInstance()

    //==========Get Method==============
    fun getUserCollection():CollectionReference{
        return this.db.collection("Users")
    }

    fun getSessionsCollection():CollectionReference{
        return this.db.collection("Sessions")
    }

    fun getSingleChatBoxsCollection():CollectionReference{
        return this.db.collection("SingleChatBoxs")
    }

    fun getListMessagesCollection():CollectionReference{
        return this.db.collection("Messages")
    }

    fun getUserByPhone(phone:String):Task<QuerySnapshot>{
        return getUserCollection().whereEqualTo("phone",phone).get()
    }

    fun getUserByLoginToken(token:String):Task<QuerySnapshot>{
        return getUserCollection().whereEqualTo("loginToken",token).get()
    }

    fun getUserByPhoneAndPass(phone:String,pass:String):Task<QuerySnapshot>{
        return getUserCollection()
            .whereEqualTo("phone",phone)
            .whereEqualTo("pass",pass).get()
    }

    fun getLoginToken(tokent: String): Task<QuerySnapshot> {
        return getSessionsCollection().whereEqualTo("token",tokent).get()
    }

    fun getBoxBy2IdUser(idUserLogin:String,idUserReceive:String): Task<DocumentSnapshot> {
        return getSingleChatBoxsCollection().document(idUserLogin)
            .collection(idUserReceive).document(idUserReceive).get()
    }

    fun getListMess(idListMess: String): Task<QuerySnapshot> {
        return getListMessagesCollection().document(idListMess).collection(idListMess).get()
    }

    //==========Create Method==============

    fun createUserAccount(user:User):Task<Void>{
        return getUserCollection().document(user.id).set(user)
    }


    fun createLoginToken(u:User):Task<Void>{

        val session = HashMap<String, Any>()
        session["token"] = u.loginToken
        return getSessionsCollection().document(u.id).set(session)
    }

    fun createSingleBox( idUser:String,box: Box):Task<Void>{

        return getSingleChatBoxsCollection().document(idUser)
            .collection(box.id).document(box.id).set(box)
    }

    fun createSingleMess( idListMess:String,mess: Message):Task<Void>{

        return getListMessagesCollection().document(idListMess)
            .collection(idListMess).document(mess.id).set(mess)
    }
    //==========Update Method==============

    fun updateUserAccount(user: User):Task<Void>{
        return getUserCollection().document(user.id).set(user, SetOptions.merge())
    }

    //==========Delete/Remove Method==============
}