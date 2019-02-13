package com.example.rio.kottlin_demo.data.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseInstance() {

    private var databaseReference: DatabaseReference

    init {
        databaseReference = FirebaseDatabase.getInstance().getReference()
    }

    fun getDatabase():DatabaseReference{
        return this.databaseReference
    }
}