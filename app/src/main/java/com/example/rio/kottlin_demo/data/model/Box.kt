package com.example.rio.kottlin_demo.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "Boxs")
data class Box(@PrimaryKey var id:String="",
               @ColumnInfo var name:String="",
               @ColumnInfo var members: ArrayList<String> = arrayListOf(),
               @ColumnInfo var type:String="",
               @ColumnInfo var content:String="" ):
    Serializable