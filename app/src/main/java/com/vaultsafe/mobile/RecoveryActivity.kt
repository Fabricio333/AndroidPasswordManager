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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.vaultsafe.mobile.utils.Bip39Utils
import com.vaultsafe.mobile.data.KeyManager

class RecoveryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Bip39Utils.loadWordlist(this)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    RecoveryScreen(onContinue = {
                        Toast.makeText(this, getString(R.string.valid_seed), Toast.LENGTH_SHORT).show()
                    })
                }
            }
        }
    }
}

@Composable
fun RecoveryScreen(onContinue: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    val context = LocalContext.current
    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = text, onValueChange = { text = it }, label = { Text(stringResource(id = R.string.seed_prompt)) })
        Button(onClick = {
            if (Bip39Utils.validateMnemonic(text)) {
                KeyManager.savePrivateKey(context, text)
                onContinue(text)
            } else {
                Toast.makeText(context, stringResource(id = R.string.invalid_seed), Toast.LENGTH_SHORT).show()
            }
        }) { Text(stringResource(id = R.string.continue_button)) }
    }
}
