package com.radha.bhaktiguru.learning

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.itsxtt.patternlock.PatternLockView
import com.radha.bhaktiguru.databinding.ActivityPatternBinding

class PatternActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatternBinding
    private lateinit var patternLockView: PatternLockView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatternBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn.setOnClickListener {
            if (isBiometricAvailable()) {
                authenticateWithBiometric()
            } else {
                // Biometric authentication is not available on this device
                // Handle accordingly
            }
        }

        binding.patternLockView.setOnPatternListener(object : PatternLockView.OnPatternListener {
            override fun onStarted() {
                super.onStarted()
            }

            override fun onProgress(ids: ArrayList<Int>) {
                super.onProgress(ids)
            }

            override fun onComplete(ids: ArrayList<Int>): Boolean {

                Toast.makeText(this@PatternActivity, "$ids", Toast.LENGTH_SHORT).show()
                /*
                 * A return value required
                 * if the pattern is not correct and you'd like change the pattern to error state, return false
                 * otherwise return true
                 */
                return false
            }
        })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
            .build()
    }


    private fun isBiometricAvailable(): Boolean {
        val biometricManager = BiometricManager.from(this)

        return when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> false
        }
    }

    private fun authenticateWithBiometric() {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Authenticate using your fingerprint or face")
            .setConfirmationRequired(false)
            .setNegativeButtonText("Cancel")
            .build()

        val biometricPrompt = BiometricPrompt(
            this,
            ContextCompat.getMainExecutor(this), object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        applicationContext,
                        "Authentication error: $errString", Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        applicationContext,
                        "Authentication succeeded!", Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        applicationContext, "Authentication failed",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        )

        biometricPrompt.authenticate(promptInfo)
    }


}