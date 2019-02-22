package com.example.rio.kottlin_demo.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity(tableName = "Boxs")
data class Box(
    @PrimaryKey
    @SerializedName("id")
    @Expose var id: String="",
    @ColumnInfo
    @SerializedName("name")
    @Expose var name:String="",
    @ColumnInfo
    @SerializedName("members")
    @Expose var members: ArrayList<String> = arrayListOf(),
    @ColumnInfo
    @SerializedName("type")
    @Expose var type:String="",
    @ColumnInfo
    @SerializedName("content")
    @Expose var content:String="",
    @ColumnInfo
    @SerializedName("avatar")
    @Expose var avatar:String="",
    @ColumnInfo
    @SerializedName("listMessage")
    @Expose var listMessage: ArrayList<Message> =arrayListOf()
):
    Serializable