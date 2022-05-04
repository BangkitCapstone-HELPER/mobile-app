package com.example.helperstartup.model

import android.content.Context

class UserPreference(context: Context) {
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(value: User) {
        val editor = preferences.edit()
        editor.putString(NAME, value.name)
        editor.putString(EMAIL, value.email)
        editor.putString(TOKEN, value.token)
        editor.putBoolean(IS_LOGIN, value.isLogin)
        editor.apply()
    }
    fun getUser(): User {
        val model = User("", "", "", false)
        model.name = preferences.getString(NAME, "").toString()
        model.email = preferences.getString(EMAIL, "").toString()
        model.token = preferences.getString(TOKEN, "").toString()
        model.isLogin = preferences.getBoolean(IS_LOGIN, false)
        return model
    }

    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val TOKEN = "token"
        private const val IS_LOGIN = "isLogin"
    }
}