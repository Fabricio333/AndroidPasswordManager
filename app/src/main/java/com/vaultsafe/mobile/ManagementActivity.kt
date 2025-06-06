package com.vaultsafe.mobile

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.vaultsafe.mobile.utils.CryptoUtils
import com.vaultsafe.mobile.data.KeyManager
import com.vaultsafe.mobile.data.NonceManager

class ManagementActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ManagementScreen()
                }
            }
        }
    }
}

@Composable
fun ManagementScreen() {
    val context = LocalContext.current
    val storedKey = remember { KeyManager.getPrivateKey(context) }
    var privateKey by remember { mutableStateOf(storedKey ?: "") }
    var email by remember { mutableStateOf("") }
    var site by remember { mutableStateOf("") }
    var nonce by remember { mutableStateOf("0") }
    var password by remember { mutableStateOf("") }
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    fun refreshNonce(currentSite: String) {
        if (currentSite.isNotEmpty()) {
            nonce = NonceManager.getNonce(context, currentSite).toString()
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = privateKey,
            onValueChange = { privateKey = it },
            label = { Text(stringResource(id = R.string.private_key_label)) }
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(id = R.string.email_label)) }
        )

        TextField(
            value = site,
            onValueChange = {
                site = it
                refreshNonce(it)
            },
            label = { Text(stringResource(id = R.string.site_label)) }
        )

        TextField(
            value = nonce,
            onValueChange = { nonce = it },
            label = { Text(stringResource(id = R.string.nonce_auto_label)) }
        )

        Button(onClick = {
            val input = "$email:$site:$nonce"
            val hmac = CryptoUtils.hmacSha256(privateKey, input)
            password = "PASS-" + hmac.take(16)
            NonceManager.incrementNonce(context, site)
            Toast.makeText(
                context,
                context.getString(R.string.nonce_updated, NonceManager.getNonce(context, site)),
                Toast.LENGTH_SHORT
            ).show()
        }) {
            Text(stringResource(id = R.string.derive_password))
        }

        Text(text = stringResource(id = R.string.derived_password, password))

        Button(onClick = {
            clipboardManager.setText(androidx.compose.ui.text.AnnotatedString(password))
            Toast.makeText(context, stringResource(id = R.string.copied_toast), Toast.LENGTH_SHORT).show()
        }) {
            Text(stringResource(id = R.string.copy_clipboard))
        }

        if (!KeyManager.hasKey(context)) {
            Button(onClick = {
                KeyManager.savePrivateKey(context, privateKey)
                Toast.makeText(context, stringResource(id = R.string.key_stored), Toast.LENGTH_SHORT).show()
            }) {
                Text(stringResource(id = R.string.save_key))
            }
        } else {
            Text(stringResource(id = R.string.key_stored))
        }
    }
}
