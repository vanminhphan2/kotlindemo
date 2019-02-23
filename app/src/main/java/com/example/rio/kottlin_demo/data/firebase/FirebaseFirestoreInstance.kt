package com.example.rio.kottlin_demo.data.firebase

import android.annotation.SuppressLint
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

    fun getUserByPhone(phone:String):Task<QuerySnapshot>{
        return getUserCollection().whereEqualTo("phone",phone).get()
    }

    fun getUserByLoginToken(token:String):Task<QuerySnapshot>{
        return getUserCollection().whereEqualTo("loginToken",token).get()
    }

    fun getLoginToken(tokent: String): Task<QuerySnapshot> {
        return getSessionsCollection().whereEqualTo("token",tokent).get()
    }

    //==========Create Method==============

    fun createUserAccount(idUser:String,phone:String):Task<Void>{

        val user = HashMap<String, Any>()
        user["phone"] = phone
        user["isUpdateInfo"] = false
        return getUserCollection().document(idUser).set(user)
    }


    fun createLoginToken(token:String):Task<Void>{

        val session = HashMap<String, Any>()
        session["token"] = token
        return getSessionsCollection().document(token).set(session)
    }

    //==========Update Method==============

    fun updateUserAccount(user: User):Task<Void>{
        return getUserCollection().document(user.id).set(user, SetOptions.merge())
    }


    //==========Delete/Remove Method==============
}