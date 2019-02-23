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

    fun getSingleChatBoxsReference():DatabaseReference{
        return getDatabase().child("SingleChatBoxs")
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

    fun createSingleBox( userLogin:User,userReceive:User,mess:Message){

        val listId= listOf(userLogin.id,userReceive.id)
//        val idBox1=FirebaseReferenceInstance.getSingleChatBoxsReference().push().key.toString()
//        val idBox2=FirebaseReferenceInstance.getSingleChatBoxsReference().push().key.toString()

        getSingleChatBoxsReference().child(userLogin.id).child(userReceive.id).child("name").setValue(userReceive.name)
        getSingleChatBoxsReference().child(userLogin.id).child(userReceive.id).child("type").setValue("single")
        getSingleChatBoxsReference().child(userLogin.id).child(userReceive.id).child("members").setValue(listId)
        getSingleChatBoxsReference().child(userLogin.id).child(userReceive.id).child("isBlock").setValue(false)
        getSingleChatBoxsReference().child(userLogin.id).child(userReceive.id).child("avatar").setValue("chua co")
        getSingleChatBoxsReference().child(userLogin.id).child(userReceive.id).child("listMess").child(mess.id).setValue(mess)

        getSingleChatBoxsReference().child(userReceive.id).child(userLogin.id).child("name").setValue(userLogin.name)
        getSingleChatBoxsReference().child(userReceive.id).child(userLogin.id).child("type").setValue("single")
        getSingleChatBoxsReference().child(userReceive.id).child(userLogin.id).child("members").setValue(listId)
        getSingleChatBoxsReference().child(userReceive.id).child(userLogin.id).child("isBlock").setValue(false)
        getSingleChatBoxsReference().child(userReceive.id).child(userLogin.id).child("avatar").setValue("chua co")
        getSingleChatBoxsReference().child(userReceive.id).child(userLogin.id).child("listMess").child(mess.id).setValue(mess)
    }

    fun createMessage(idUserLogin: String,idUserReceive: String,mess:Message){
        getSingleChatBoxsReference().child(idUserLogin).child(idUserReceive).child("listMess").child(mess.id).setValue(mess)
        getSingleChatBoxsReference().child(idUserReceive).child(idUserLogin).child("listMess").child(mess.id).setValue(mess)
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


    fun getBoxBy2IdUser(idUserLogin:String,idUserReceive:String): Query {
        return getSingleChatBoxsReference().child(idUserLogin).child(idUserReceive)
    }

    fun getMessListener(idUserLogin:String,idUserReceive:String): Query {
        return getSingleChatBoxsReference().child(idUserLogin).child(idUserReceive).child("listMess")
    }

//    fun getListMessageByIdBox(idBox:String): Query {
//        return getMessagesReference().child(idBox)
//    }

    fun getListIdBoxByUserId(idUser: String): Query {
        return getSingleChatBoxsReference().child(idUser)
    }

    fun removeListener(databaseReference : DatabaseReference,listener: ValueEventListener){
        databaseReference.removeEventListener(listener)
    }


    fun removeEventGetBoxBy2IdUser(idUser:String,idUserReceive:String,listener: ValueEventListener) {
        getSingleChatBoxsReference().child(idUser).child(idUserReceive).removeEventListener(listener)
    }

    fun removeEventGetBoxByIdUser(idUser:String,listener: ValueEventListener) {
        getSingleChatBoxsReference().child(idUser).removeEventListener(listener)
    }

    fun removeEventGetMess(idUser:String,idUserReceive:String,listener: ValueEventListener) {
        getSingleChatBoxsReference().child(idUser).child(idUserReceive).child("listMess").removeEventListener(listener)
    }

}