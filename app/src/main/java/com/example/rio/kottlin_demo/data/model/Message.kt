package com.example.rio.kottlin_demo.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "Messages")
class Message(@PrimaryKey
              @SerializedName("id")
              @Expose var id:String="",
              @ColumnInfo
              @SerializedName("idUser")
              @Expose var idUser:String="",
              @ColumnInfo
              @SerializedName("type")
              @Expose var type:String="",
              @ColumnInfo
              @SerializedName("contentMess")
              @Expose var contentMess:String="",
              @ColumnInfo
              @SerializedName("sendTime")
              @Expose var sendTime:String="",
              @ColumnInfo
              @SerializedName("receiveTime")
              @Expose var receiveTime:String="",
              @ColumnInfo
              @SerializedName("stastus")
              @Expose var stastus:String="",
              @ColumnInfo
              @SerializedName("linkImg")
              @Expose var linkImg:String =""):
    Serializable