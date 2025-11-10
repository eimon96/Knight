package com.eimon.knight

import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit

internal class StorageHandler(private val activity: MainActivity) {

    internal fun saveToPrefs(key: String, value: String) {
        activity.getSharedPreferences("knight_app_prefs", MODE_PRIVATE)
            .edit {
                putString(key, value)
                apply()
            }
    }

    internal fun getFromPrefs(key: String, default: String = ""): String {
        return activity.getSharedPreferences("knight_app_prefs", MODE_PRIVATE)
            .getString(key, default) ?: default
    }
}