package com.vaultsafe.mobile.utils

import android.content.Context
import java.security.SecureRandom

object Bip39Utils {
    private lateinit var wordlist: List<String>

    fun loadWordlist(context: Context) {
        if (!::wordlist.isInitialized) {
            val text = context.assets.open("bip39_wordlist.txt").bufferedReader().use { it.readText() }
            wordlist = text.split('\n').map { it.trim() }.filter { it.isNotEmpty() }
        }
    }

    fun generateMnemonic(): String {
        val entropy = ByteArray(16)
        SecureRandom().nextBytes(entropy)
        val indices = entropy.map { (it.toInt() and 0xFF) % 2048 }
        return indices.joinToString(" ") { wordlist[it] }
    }

    fun validateMnemonic(phrase: String): Boolean {
        val words = phrase.trim().split(" ")
        return words.all { wordlist.contains(it) } && words.size >= 12
    }
}
