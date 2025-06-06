package com.vaultsafe.mobile.data

import android.content.Context

object NonceManager {
    private const val PREFIX = "nonce_"

    fun getNonce(context: Context, site: String): Int {
        val prefs = VaultStorage.prefs(context)
        return prefs.getInt(PREFIX + site, 0)
    }

    fun incrementNonce(context: Context, site: String) {
        val prefs = VaultStorage.prefs(context)
        val value = prefs.getInt(PREFIX + site, 0) + 1
        prefs.edit().putInt(PREFIX + site, value).apply()
    }

    fun setNonce(context: Context, site: String, value: Int) {
        val prefs = VaultStorage.prefs(context)
        prefs.edit().putInt(PREFIX + site, value).apply()
    }
}
