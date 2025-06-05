package com.vaultsafe.mobile.data

import android.content.Context

object KeyManager {
    private const val KEY_PRIVATE = "private_key"

    fun savePrivateKey(context: Context, key: String) {
        val prefs = VaultStorage.prefs(context)
        prefs.edit().putString(KEY_PRIVATE, key).apply()
    }

    fun getPrivateKey(context: Context): String? {
        val prefs = VaultStorage.prefs(context)
        return prefs.getString(KEY_PRIVATE, null)
    }

    fun hasKey(context: Context): Boolean {
        val prefs = VaultStorage.prefs(context)
        return prefs.contains(KEY_PRIVATE)
    }
}
