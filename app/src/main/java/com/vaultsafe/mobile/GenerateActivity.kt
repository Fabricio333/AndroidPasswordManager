package com.vaultsafe.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.vaultsafe.mobile.utils.Bip39Utils
import com.vaultsafe.mobile.data.KeyManager

class GenerateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Bip39Utils.loadWordlist(this)
        val mnemonic = Bip39Utils.generateMnemonic()
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GenerateScreen(mnemonic)
                }
            }
        }
    }
}

@Composable
fun GenerateScreen(mnemonic: String) {
    val writtenDown = remember { mutableStateOf(false) }
    val context = LocalContext.current
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = mnemonic)
        if (!writtenDown.value) {
            Text(stringResource(id = R.string.backup_instruction))
            Button(onClick = {
                writtenDown.value = true
                KeyManager.savePrivateKey(context, mnemonic)
            }) {
                Text(stringResource(id = R.string.written_down_confirm))
            }
        } else {
            Text(stringResource(id = R.string.proceed_message))
        }
    }
}
