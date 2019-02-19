package com.example.rio.kottlin_demo.data.firebase

import com.example.rio.kottlin_demo.data.model.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

object FirebaseReferenceInstance {

    private var database: DatabaseReference

    init {
        database = FirebaseDatabase.getInstance().getReference()
    }

    fun getDatabase():DatabaseReference{
        return this.database
    }


    fun setDatabase(mDatabase: DatabaseReference) {
        this.database = mDatabase
    }


    fun getUsersReference():DatabaseReference{
        return getDatabase().child("users")
    }

    fun getSessionsReference():DatabaseReference{
        return getDatabase().child("sessions")
    }

    fun getBoxsReference():DatabaseReference{
        return getDatabase().child("boxs")
    }

    fun removeListener(databaseReference : DatabaseReference,listener: ValueEventListener){
        databaseReference.removeEventListener(listener)
    }

    fun updateInfoUserById(idUser:String, userInfo:User){
        val childUpdates = HashMap< String, Any>()
        childUpdates.put(idUser, userInfo);
        getUsersReference().updateChildren(childUpdates);
    }

    fun removeUserById(userID:String){
        getUsersReference().child(userID).removeValue();
    }

    fun getUserById(userId: String): Query {
        return getUsersReference().child(userId)
    }

    fun getUserByPhone(phone: String): Query {
        return getUsersReference().orderByChild("phone").equalTo(phone)
    }

    fun checkPhoneByToken(tokent: String): Query {
        return getSessionsReference().child(tokent)
    }


    fun getBoxBy2IdUser(idUserSent: String,idUserSend:String): Query {
        var listId:List<String>

        return getBoxsReference().orderByChild("type").equalTo("single")
    }

}