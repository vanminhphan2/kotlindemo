package com.example.rio.kottlin_demo.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Users")
data class User(@PrimaryKey val id:Long,@ColumnInfo val name:String, @ColumnInfo val phone:String , @ColumnInfo val pass:String ):Serializable