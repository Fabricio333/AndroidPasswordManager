package com.vaultsafe.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class BackupVerificationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    VerificationScreen()
                }
            }
        }
    }
}

@Composable
fun VerificationScreen() {
    var word1 by remember { mutableStateOf("") }
    Column {
        TextField(value = word1, onValueChange = { word1 = it }, label = { Text("Word #1") })
        Button(onClick = {}) { Text("Verify") }
    }
}
