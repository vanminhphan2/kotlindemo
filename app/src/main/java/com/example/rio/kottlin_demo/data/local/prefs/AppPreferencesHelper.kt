package com.example.rio.kottlin_demo.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import com.example.rio.kottlin_demo.di.anotation.PreferenceInfo
import javax.inject.Inject

class AppPreferencesHelper @Inject constructor(context: Context, @PreferenceInfo prefFileName: String) : PreferencesHelper {

    private val PREF_KEY_PASS = "PREF_KEY_PASS"

    private val PREF_KEY_PHONE = "PREF_KEY_PHONE"

    private val PREF_KEY_TOKEN = "PREF_KEY_TOKEN"

    private val PREF_KEY_USER_ID = "PREF_KEY_USER_ID"

    private val mPrefs: SharedPreferences

    init {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)
    }

    override fun getPhone(): String? {
        return mPrefs.getString(PREF_KEY_PHONE, null)
    }

    override fun setPhone(phone: String) {
        mPrefs.edit().putString(PREF_KEY_PHONE, phone).apply()
    }

    override fun getPass(): String? {
        return mPrefs.getString(PREF_KEY_PASS, null)
    }

    override fun setPass(pass: String) {
        mPrefs.edit().putString(PREF_KEY_PASS, pass).apply()
    }

    override fun getLoginToken(): String? {
        return mPrefs.getString(PREF_KEY_TOKEN, null)
    }

    override fun setLoginToken(token: String) {
        mPrefs.edit().putString(PREF_KEY_TOKEN, token).apply()
    }

    override fun getUserId(): String? {
        return mPrefs.getString(PREF_KEY_USER_ID, null)
    }

    override fun setUserId(id: String) {
        mPrefs.edit().putString(PREF_KEY_USER_ID, id).apply()
    }
}