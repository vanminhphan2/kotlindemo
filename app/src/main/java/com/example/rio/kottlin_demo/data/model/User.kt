package com.example.rio.kottlin_demo.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.reactivex.annotations.NonNull
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "Users")
data class User  constructor(
    @PrimaryKey
    @SerializedName("id")
    @Expose var id: String = "",

    @ColumnInfo
    @SerializedName("loginToken")
    @Expose var loginToken: String = "",

    @ColumnInfo
    @SerializedName("name")
    @Expose var name: String = "",

    @ColumnInfo
    @SerializedName("phone")
    @Expose var phone: String = "",

    @ColumnInfo
    @SerializedName("pass")
    @Expose var pass: String = "",

    @ColumnInfo
    @SerializedName("avatar")
    @Expose var avatar: String = "",

    @ColumnInfo
    @SerializedName("timeCreate")
    @Expose var timeCreate: String = "",

    @ColumnInfo
    @SerializedName("timeUpdate")
    @Expose var timeUpdate: String = "",

    @ColumnInfo
    @SerializedName("isOnline")
    @Expose var isOnline: Boolean = false,

    @ColumnInfo
    @SerializedName("maritalStatus")
    @Expose var maritalStatus: String = "",

    @ColumnInfo
    @SerializedName("isUpdateInfo")
    @Expose var isUpdateInfo: Boolean = false
) : Serializable