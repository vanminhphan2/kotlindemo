package com.example.rio.kottlin_demo.utils

import com.example.rio.kottlin_demo.data.model.User
import com.google.firebase.database.DataSnapshot

object ConvertData {

    fun convertDataSnapshotToUser(snapshot: DataSnapshot): User {
        return User(
            snapshot.getChildren().first().key.toString(),
            snapshot.getChildren().first().child("name").getValue().toString(),
            snapshot.getChildren().first().child("phone").getValue().toString(),
            snapshot.getChildren().first().child("pass").getValue().toString()
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