package com.vaultsafe.mobile

import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.res.stringResource
import com.vaultsafe.mobile.utils.BiometricHelper
import com.vaultsafe.mobile.data.KeyManager

class BiometricUnlockActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    BiometricScreen(onUnlock = {
                        // TODO: Load and decrypt key after authentication
                    })
                }
            }
        }
    }
}

@Composable
fun BiometricScreen(onUnlock: () -> Unit) {
    val context = LocalContext.current
    Column {
        Button(onClick = {
            val prompt = BiometricHelper.createPrompt(context as androidx.fragment.app.FragmentActivity) {
                onUnlock()
                val key = KeyManager.getPrivateKey(context)
                Toast.makeText(context, stringResource(id = R.string.key_loaded), Toast.LENGTH_SHORT).show()
            }
            BiometricHelper.showBiometricPrompt(prompt)
        }) {
            Text(text = stringResource(id = R.string.unlock_with_biometrics))
        }
    }
}
