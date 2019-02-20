package com.example.rio.kottlin_demo.data

import com.example.rio.kottlin_demo.data.local.db.DbHelper
import com.example.rio.kottlin_demo.data.local.prefs.PreferencesHelper
import com.example.rio.kottlin_demo.data.model.User
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataManager @Inject constructor(
    private val mPreferencesHelper: PreferencesHelper,
    private val mDbHelper: DbHelper
) :DataManager {

    override fun getAllUsers(): Observable<MutableList<User>> {
        return mDbHelper.getAllUsers()
    }

    override fun insertUser(user: User?): Flowable<User> {
        return mDbHelper.insertUser(user)
    }

    override fun findUserByPhone(phone: String?): Observable<User> {
        return mDbHelper.findUserByPhone(phone)
    }

    override fun getInfoUserLogin(id: String?): Observable<User> {
        return mDbHelper.getInfoUserLogin(id)
    }

    override fun getPhone(): String? {
        return mPreferencesHelper.getPhone()
    }

    override fun loginByPhone(phone: String?, pass: String?): Observable<User> {
        return mDbHelper.loginByPhone(phone, pass)
    }

    override fun setPhone(phone: String?) {
        mPreferencesHelper.setPhone(phone)
    }

    override fun getPass(): String? {
        return mPreferencesHelper.getPass()
    }

    override fun setPass(pass: String?) {
        mPreferencesHelper.setPass(pass)
    }

    override fun getLoginToken(): String? {
        return mPreferencesHelper.getLoginToken()
    }

    override fun setLoginToken(token: String?) {
        mPreferencesHelper.setLoginToken(token)
    }

    override fun getUserId(): String {
        return mPreferencesHelper.getUserId()
    }

    override fun setUserId(id: String?) {
        mPreferencesHelper.setUserId(id)
    }

}