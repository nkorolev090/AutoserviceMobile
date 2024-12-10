package com.project.deviceToken

import android.content.Context

class DeviceTokenManager(private val context: Context) {

    companion object {
        const val DEVICE_TOKEN_KEY = "device_token"
        private const val PREFERENCES_KEY = "token_preferences"
    }

    fun saveToken(token: String) {
        val prefs = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
        prefs.edit().putString(DEVICE_TOKEN_KEY, token).apply()
    }

    fun getToken(): String? {
        val prefs = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
        return prefs.getString(DEVICE_TOKEN_KEY,null)
    }

    fun deleteToken() {
        val prefs = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
        prefs.edit().remove(DEVICE_TOKEN_KEY).apply()
    }
}