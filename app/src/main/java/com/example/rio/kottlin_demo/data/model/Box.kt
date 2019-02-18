package com.example.rio.kottlin_demo.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "Boxs")
class Box(@PrimaryKey val id:String, @ColumnInfo val name:String, @ColumnInfo val idSentUser:String, @ColumnInfo val idReceiveUser:String ,@ColumnInfo val content:String ):
    Serializable