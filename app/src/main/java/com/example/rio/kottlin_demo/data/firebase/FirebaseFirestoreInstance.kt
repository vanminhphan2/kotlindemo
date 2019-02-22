package com.example.rio.kottlin_demo.data.firebase

import android.annotation.SuppressLint
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseFirestoreInstance{

    @SuppressLint("StaticFieldLeak")
    private val db= FirebaseFirestore.getInstance()

    fun getUserCollection():CollectionReference{
        return this.db.collection("Users")
    }

    fun getSessionsCollection():CollectionReference{
        return this.db.collection("Sessions")
    }

    fun getSingleChatBoxsCollection():CollectionReference{
        return this.db.collection("SingleChatBoxs")
    }
}