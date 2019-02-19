package com.example.rio.kottlin_demo.utils

import java.util.*
import java.util.UUID.randomUUID



object AppConstants {

    val API_STATUS_CODE_LOCAL_ERROR = 0

    val DB_NAME = "kotlin_demo.db"

    val NULL_INDEX = -1L

    val PREF_NAME = "kotlin_demo_pref"

    val SEED_DATABASE_OPTIONS = "seed/options.json"

    val SEED_DATABASE_QUESTIONS = "seed/questions.json"

    val STATUS_CODE_FAILED = "failed"

    val STATUS_CODE_SUCCESS = "success"

    val TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss"

    val LOGIN_TO_REGISTER = 1

    val SETTING_INTENT = 20
    val REQUEST_CODE_CHOOSE_FROM_GALLERY = 21
    val REQUEST_CODE_CHOOSE_FROM_EDIT = 22

    val INFO_USER_KEY = "INFO_USER"
    val ID_USER_CHOOSE = "ID_USER_CHOOSE"

    val REQUEST_CODE_TO_REGISTER_ACTIVITY = 2
    fun generateTokenString(): String {
        val uuid = UUID.randomUUID().toString()
        return uuid
    }
}// This utility class is not publicly instantiable
