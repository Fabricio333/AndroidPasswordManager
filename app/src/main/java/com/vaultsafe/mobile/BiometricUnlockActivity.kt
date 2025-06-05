package com.vaultsafe.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.vaultsafe.mobile.utils.BiometricHelper

class BiometricUnlockActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    BiometricScreen(onUnlock = {
                        // TODO load and decrypt key
                    })
                }
            }
        }
    }
}

@Composable
fun BiometricScreen(onUnlock: () -> Unit) {
    Column {
        val context = LocalContext.current
        Button(onClick = {
            val prompt = BiometricHelper.createPrompt(context as androidx.fragment.app.FragmentActivity) {
                onUnlock()
            }
            prompt.authenticate(
                BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Unlock")
                    .setNegativeButtonText("Cancel")
                    .build()
            )
        }) {
            Text("Use Biometrics")
        }
    }
}
