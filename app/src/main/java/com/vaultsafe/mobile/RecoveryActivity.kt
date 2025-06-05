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
import com.vaultsafe.mobile.utils.Bip39Utils

class RecoveryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    RecoveryScreen(onContinue = { phrase ->
                        if (Bip39Utils.validateMnemonic(phrase)) {
                            Toast.makeText(this, "Seed valid", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Invalid seed", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun RecoveryScreen(onContinue: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = text, onValueChange = { text = it }, label = { Text("Seed Phrase") })
        Button(onClick = { onContinue(text) }) { Text("Continue") }
    }
}
