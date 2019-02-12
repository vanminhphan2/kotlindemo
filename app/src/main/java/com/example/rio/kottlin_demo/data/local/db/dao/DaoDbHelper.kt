package com.example.rio.kottlin_demo.data.local.db.dao

import com.example.rio.kottlin_demo.data.local.db.DaoDatabase
import com.example.rio.kottlin_demo.data.local.db.DbHelper
import com.example.rio.kottlin_demo.data.model.User
import io.reactivex.Flowable
import io.reactivex.Observable
import java.util.concurrent.Callable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DaoDbHelper @Inject constructor(private val daoDatabase: DaoDatabase) :DbHelper {

    override fun getAllUsers(): Observable<List<User>> {
        return Observable.fromCallable<List<User>> { daoDatabase.userDao().loadAll() }
    }

    override fun insertUser(user: User?): Flowable<User> {
        return Flowable.fromCallable {
            daoDatabase.userDao().insert(user)
            daoDatabase.userDao().loginByPhone(user!!.phone, user.pass)
        }
    }

    override fun findUserByPhone(phone: String?): Observable<User> {
        return Observable.fromCallable { daoDatabase.userDao().findUserByPhone(phone) }
    }

    override fun loginByPhone(phone: String?, pass: String?): Observable<User> {
        return Observable.fromCallable { daoDatabase.userDao().loginByPhone(phone, pass) }
    }
}