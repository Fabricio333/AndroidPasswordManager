package com.vaultsafe.mobile

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

class WelcomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    WelcomeScreen(
                        onGenerate = {
                            startActivity(Intent(this, GenerateActivity::class.java))
                        },
                        onRecover = {
                            startActivity(Intent(this, RecoveryActivity::class.java))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen(onGenerate: () -> Unit, onRecover: () -> Unit) {
    Column {
        Button(onClick = onGenerate) { Text("Generate New Seed") }
        Button(onClick = onRecover) { Text("Recover with Seed Phrase") }
    }
}
