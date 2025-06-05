package com.vaultsafe.mobile

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.vaultsafe.mobile.utils.CryptoUtils

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
    var privateKey by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var site by remember { mutableStateOf("") }
    var nonce by remember { mutableStateOf("0") }
    var password by remember { mutableStateOf("") }
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = privateKey, onValueChange = { privateKey = it }, label = { Text("Private Key") })
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        TextField(value = site, onValueChange = { site = it }, label = { Text("Site") })
        TextField(value = nonce, onValueChange = { nonce = it }, label = { Text("Nonce") })
        Button(onClick = {
            val input = "$privateKey/$email/$site/$nonce"
            val hash = CryptoUtils.sha256(input)
            password = "PASS" + hash.take(16) + "249+"
        }) { Text("Derive Password") }
        Text("Password: $password")
        Button(onClick = {
            clipboardManager.setText(androidx.compose.ui.text.AnnotatedString(password))
            Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show()
        }) { Text("Copy to Clipboard") }
    }
}
