package com.example.rio.kottlin_demo.utils

import com.example.rio.kottlin_demo.data.model.Box
import com.example.rio.kottlin_demo.data.model.User
import com.google.firebase.database.DataSnapshot
import org.json.JSONArray

object ConvertData {

    fun convertDataSnapshotToUser(snapshot: DataSnapshot): User {
        return User(
            snapshot.getChildren().first().key.toString(),
            snapshot.getChildren().first().child("name").getValue().toString(),
            snapshot.getChildren().first().child("phone").getValue().toString(),
            snapshot.getChildren().first().child("pass").getValue().toString()
        )
    }

    fun convertSnapshotToUser(snapshot: DataSnapshot): User {
        return User(
            snapshot.key.toString(),
            snapshot.child("name").getValue().toString(),
            snapshot.child("phone").getValue().toString(),
            snapshot.child("pass").getValue().toString()
        )
    }

    fun convertSnapshotToBox(snapshot: DataSnapshot): Box {
        val list= ArrayList<String>()
        val data = JSONArray(snapshot.child("members").value.toString())
        for (i in 0..(data.length() - 1)) {
            list.add(data.get(i).toString())
        }
        return Box(
            snapshot.key.toString(),
            snapshot.child("name").getValue().toString(),
            list,
            snapshot.child("type").getValue().toString(),
            snapshot.child("content").getValue().toString()
        )
    }


    fun convertDataSnapshotToArrUser(snapshot: DataSnapshot): ArrayList<User> {
        val list = ArrayList<User>()
        for (item in snapshot.children) {
            val user = User(
                item.key.toString(),
                item.child("name").getValue().toString(),
                item.child("phone").getValue().toString(),
                item.child("pass").getValue().toString()
            )
            list.add(user)
        }
        return list
    }
}