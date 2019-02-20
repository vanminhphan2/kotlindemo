package com.example.rio.kottlin_demo.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Messages")
class Message(@PrimaryKey var id:String="",
              @ColumnInfo var idUser:String="",
              @ColumnInfo var type:String="",
              @ColumnInfo var contentMess:String="",
              @ColumnInfo var sendTime:String="",
              @ColumnInfo var receiveTime:String="",
              @ColumnInfo var stastus:String="",
              @ColumnInfo var linkImg:String =""):
    Serializable