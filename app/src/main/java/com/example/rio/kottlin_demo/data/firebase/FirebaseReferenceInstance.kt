package com.example.rio.kottlin_demo.data.firebase

import com.example.rio.kottlin_demo.data.model.Message
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

    fun getMessagesReference():DatabaseReference{
        return getDatabase().child("messages")
    }

    fun removeListener(databaseReference : DatabaseReference,listener: ValueEventListener){
        databaseReference.removeEventListener(listener)
    }

    fun createUserAccount(idUser:String,phone:String){

        getUsersReference().child(idUser).child("phone").setValue(phone)
        getUsersReference().child(idUser).child("isUpdateInfo").setValue(false)
    }

    fun updateUserAccount(user:User){

        getUsersReference().child(user.id).child("isUpdateInfo").setValue(true)
        getUsersReference().child(user.id).child("name").setValue(user.name)
        getUsersReference().child(user.id).child("pass").setValue(user.pass)
    }

    fun createLoginToken(token:String,phone:String){

        getSessionsReference().child(token).setValue(phone)
    }

    fun createSingleBox(key:String, idUser1:String,idUser2:String){
        val listId= listOf(idUser1,idUser2)
        getBoxsReference().child(key).child("name").setValue("single chat")
        getBoxsReference().child(key).child("type").setValue("single")
        getBoxsReference().child(key).child("members").setValue(listId)
    }

    fun createMessage(idBox:String,mess:Message){
        mess.id=FirebaseReferenceInstance.getMessagesReference().push().key.toString()
        getMessagesReference().child(idBox).child(mess.id).setValue(mess)
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


    fun getBoxBy2IdUser(): Query {
        return getBoxsReference().orderByChild("type").equalTo("single")
    }

}