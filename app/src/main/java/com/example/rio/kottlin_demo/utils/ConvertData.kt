package com.example.rio.kottlin_demo.utils

import android.util.Log
import com.example.rio.kottlin_demo.data.model.Box
import com.example.rio.kottlin_demo.data.model.Message
import com.example.rio.kottlin_demo.data.model.User
import com.google.firebase.database.DataSnapshot
import org.json.JSONArray

object ConvertData {

//    fun convertDataSnapshotToUser(snapshot: DataSnapshot): User {
//        return User(
//            snapshot.getChildren().first().key.toString(),
//            snapshot.getChildren().first().child("name").getValue().toString(),
//            snapshot.getChildren().first().child("phone").getValue().toString(),
//            snapshot.getChildren().first().child("pass").getValue().toString()
//        )
//    }

//    fun convertSnapshotToUser(snapshot: DataSnapshot): User {
//        return User(
//            snapshot.key.toString(),
//            snapshot.child("name").getValue().toString(),
//            snapshot.child("phone").getValue().toString(),
//            snapshot.child("pass").getValue().toString()
//        )
//    }

//    fun convertSnapshotToBox(snapshot: DataSnapshot): Box {
//        val list = ArrayList<String>()
//        val listMember = convertDataSnapshotToListMember(snapshot.child("members"))
//        val listMess = convertDataSnapshotToListMessage(snapshot.child("listMess"))
//        for (i in 0..(listMember.size - 1)) {
//            list.add(listMember[i])
//        }
//        return Box(
//            snapshot.key.toString(),
//            snapshot.child("name").value.toString(),
//            list,
//            snapshot.child("type").value.toString(),
//            snapshot.child("content").value.toString(),
//            snapshot.child("avatar").value.toString(),
//            listMess
//        )
//    }


    fun convertDataSnapshotToArrUser(snapshot: DataSnapshot): ArrayList<User> {
        val list = ArrayList<User>()
        for (item in snapshot.children) {
//            val user = User(
//                item.key.toString(),
//                item.child("name").getValue().toString(),
//                item.child("phone").getValue().toString(),
//                item.child("pass").getValue().toString()
//            )
//            list.add(user)
        }
        return list
    }

    fun convertDataSnapshotToListIdBox(snapshot: DataSnapshot): ArrayList<String> {
        val list = ArrayList<String>()
        for (item in snapshot.children) {
            val id = item.key.toString()
            list.add(id)
        }
        return list
    }

//    fun convertDataSnapshotToListBox(snapshot: DataSnapshot): ArrayList<Box> {
//        val list = ArrayList<Box>()
//        for (item in snapshot.children) {
//            val listMember = convertDataSnapshotToListMember(item.child("members"))
//            val listMess = convertDataSnapshotToListMessage(item.child("listMess"))
//            val box = Box(
//                item.key.toString(),
//                item.child("name").getValue().toString(),
//                listMember,
//                item.child("type").getValue().toString(),
//                item.child("content").getValue().toString(),
//                item.child("avatar").getValue().toString(),
//                listMess
//            )
//            list.add(box)
//        }
//        return list
//    }

    fun convertDataSnapshotToBox(box: Box,snapshot: DataSnapshot): Box {

            for (itemMess in snapshot.child("listMess").children) {
                val mess = itemMess.getValue(Message::class.java)
                if (mess != null) {
                    mess.id = itemMess.key.toString()
//                    box.listMessage.add(mess)
                }
            }
            box.id = snapshot.key.toString()
            return box
    }

    fun convertDataSnapshotToListMessage(snapshot: DataSnapshot): ArrayList<Message> {
        val list = ArrayList<Message>()
        for (item in snapshot.children) {
            val mess = Message(
                item.key.toString(),
                item.child("idUser").getValue().toString(),
                item.child("type").getValue().toString(),
                item.child("contentMess").getValue().toString(),
                item.child("sendTime").getValue().toString(),
                item.child("receiveTime").getValue().toString(),
                item.child("status").getValue().toString(),
                item.child("linkImg").getValue().toString()
            )
            list.add(mess)
        }
        return list
    }

    fun convertDataSnapshotToListMember(snapshot: DataSnapshot): ArrayList<String> {
        val list = ArrayList<String>()
        for (item in 0..snapshot.children.count() - 1) {
            val member = snapshot.child(item.toString()).getValue().toString()
            list.add(member)
        }
        return list
    }


}