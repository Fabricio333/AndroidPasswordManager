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
                val key = KeyManager.getPrivateKey(context)
                android.widget.Toast.makeText(context, context.getString(R.string.key_loaded), android.widget.Toast.LENGTH_SHORT).show()
            }
            prompt.authenticate(
                BiometricPrompt.PromptInfo.Builder()
                    .setTitle(stringResource(id = R.string.unlock_title))
                    .setNegativeButtonText(stringResource(id = R.string.cancel))
                    .build()
            )
        }) {
            Text(stringResource(id = R.string.use_biometrics))
        }
    }
}
