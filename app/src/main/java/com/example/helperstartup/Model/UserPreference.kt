package com.example.helperstartup.Model

import android.content.Context

class UserPreference(context: Context) {
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(value: User) {
        val editor = preferences.edit()
        editor.putString(NAME, value.name)
        editor.putString(EMAIL, value.email)
        editor.putString(TOKEN, value.token)
        editor.putBoolean(IS_LOGIN, value.isLogin)
        editor.putString(IMAGE, value.image)
        editor.putString(PHONE_NUMBER, value.phoneNumber)
        editor.putInt(ID, value.id)
        editor.apply()
    }

    fun updatePhone(phone: String) {
        val editor = preferences.edit()
        editor.putString(PHONE_NUMBER, phone)
        editor.apply()
    }

    fun getUser(): User {
        val model = User("", "", "", false, "", "", 0)
        model.name = preferences.getString(NAME, "").toString()
        model.email = preferences.getString(EMAIL, "").toString()
        model.token = preferences.getString(TOKEN, "").toString()
        model.isLogin = preferences.getBoolean(IS_LOGIN, false)
        model.image = preferences.getString(IMAGE, "").toString()
        model.phoneNumber = preferences.getString(PHONE_NUMBER, "").toString()
        model.id = preferences.getInt(ID, 0)
        return model
    }

    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val TOKEN = "token"
        private const val IMAGE = "image"
        private const val PHONE_NUMBER = "phoneNumber"
        private const val IS_LOGIN = "isLogin"
        private const val ID = "id"
    }
}