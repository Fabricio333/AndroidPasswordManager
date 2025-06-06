package com.vaultsafe.mobile.utils

import android.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object CryptoUtils {
    fun sha256(input: String): String {
        val digest = java.security.MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(input.toByteArray())
        return hash.joinToString("") { "%02x".format(it) }
    }

    fun hmacSha256(key: String, data: String): String {
        val mac = Mac.getInstance("HmacSHA256")
        val spec = SecretKeySpec(key.toByteArray(), "HmacSHA256")
        mac.init(spec)
        val result = mac.doFinal(data.toByteArray())
        return Base64.encodeToString(result, Base64.NO_WRAP)
    }
}
