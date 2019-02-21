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

    fun createSingleBox(key:String, userLogin:User,userReceive:User){

        val listId= listOf(userLogin.id,userReceive.id)

        getBoxsReference().child(userLogin.id).child(key).child("name").setValue(userReceive.name)
        getBoxsReference().child(userLogin.id).child(key).child("type").setValue("single")
        getBoxsReference().child(userLogin.id).child(key).child("members").setValue(listId)
        getBoxsReference().child(userLogin.id).child(key).child("isBlock").setValue(false)

        getBoxsReference().child(userReceive.id).child(key).child("name").setValue(userLogin.name)
        getBoxsReference().child(userReceive.id).child(key).child("type").setValue("single")
        getBoxsReference().child(userReceive.id).child(key).child("members").setValue(listId)
        getBoxsReference().child(userReceive.id).child(key).child("isBlock").setValue(false)
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


    fun getBoxByIdUser(idUser:String): Query {
        return getBoxsReference().child(idUser)
    }

    fun getListMessageByIdBox(idBox:String): Query {
        return getMessagesReference().child(idBox)
    }

    fun getListIdBoxByUserId(idUser: String): Query {
        return getBoxsReference().child(idUser)
    }

    fun removeListener(databaseReference : DatabaseReference,listener: ValueEventListener){
        databaseReference.removeEventListener(listener)
    }


    fun removeEventGetBoxByIdUser(idUser:String,listener: ValueEventListener) {
        getBoxsReference().child(idUser).removeEventListener(listener)
    }

}