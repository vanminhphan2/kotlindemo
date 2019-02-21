package com.example.rio.kottlin_demo.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "Users")
data class User(
    @PrimaryKey
    @SerializedName("id")
    @Expose var id: String="",

    @ColumnInfo
    @SerializedName("name")
    @Expose var name: String="",

    @ColumnInfo
    @SerializedName("phone")
    @Expose var phone: String="",

    @ColumnInfo
    @SerializedName("pass")
    @Expose var pass: String=""
) : Serializable