package com.example.rio.kottlin_demo.data.local.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.rio.kottlin_demo.data.local.db.dao.UserDao
import com.example.rio.kottlin_demo.data.model.User

@Database(entities = [User::class], version = 2, exportSchema = false)
abstract class DaoDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}
