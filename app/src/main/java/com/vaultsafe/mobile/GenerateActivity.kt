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
import com.vaultsafe.mobile.utils.Bip39Utils

class GenerateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = mnemonic)
        if (!writtenDown.value) {
            Text("Back up your phrase")
            Button(onClick = { writtenDown.value = true }) {
                Text("I've written it down")
            }
        } else {
            Text("You may proceed")
        }
    }
}
