package com.project.autoservicedata.token

import android.content.Context
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TokenManager(private val context: Context) {
    companion object {
        private const val TOKEN_KEY = "jwt_token"
        private const val PREFERENCES_KEY = "token_preferences"
        private const val KEY_WERB = "Bearer "
    }

    fun getToken(): String {
        val prefs = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
        return KEY_WERB + prefs.getString(TOKEN_KEY,null)

    }

    fun saveToken(token: String) {
        val prefs = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
        prefs.edit().putString(TOKEN_KEY, token).apply()
    }

    fun deleteToken() {
        val prefs = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
        prefs.edit().remove(TOKEN_KEY).apply()
    }
}