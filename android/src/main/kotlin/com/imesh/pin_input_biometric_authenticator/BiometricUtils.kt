package com.imesh.pin_input_biometric_authenticator

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import java.security.AlgorithmParameters
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class BiometricUtils {

    private  val ANDROID_KEYSTORE = "AndroidKeyStore"
    private  val YOUR_SECRET_KEY_NAME = "Y0UR$3CR3TK3YN@M3"
    private  val KEY_SIZE = 128
    private  val ENCRYPTION_BLOCK_MODE = KeyProperties.BLOCK_MODE_GCM
    private  val ENCRYPTION_PADDING = KeyProperties.ENCRYPTION_PADDING_NONE
    private  val ENCRYPTION_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES

    private fun getCipher(): Cipher {
        return Cipher.getInstance("$ENCRYPTION_ALGORITHM/$ENCRYPTION_BLOCK_MODE/$ENCRYPTION_PADDING")
    }

        @RequiresApi(Build.VERSION_CODES.M)
        fun generateSecretKey(biometricUtils: BiometricUtils, keyName:String): SecretKey {
            // 1
            val keyStore = KeyStore.getInstance(biometricUtils.ANDROID_KEYSTORE)
            keyStore.load(null) // Keystore must be loaded before it can be accessed
            keyStore.getKey(keyName, null)?.let { return it as SecretKey }

            // 2
            val paramsBuilder = KeyGenParameterSpec.Builder(
                keyName,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
            paramsBuilder.apply {
                setBlockModes(biometricUtils.ENCRYPTION_BLOCK_MODE)
                setEncryptionPaddings(biometricUtils.ENCRYPTION_PADDING)
                setKeySize(biometricUtils.KEY_SIZE)
                setUserAuthenticationRequired(true)
            }

            // 3
            val keyGenParams = paramsBuilder.build()
            val keyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES,
                biometricUtils.ANDROID_KEYSTORE
            )
            keyGenerator.init(keyGenParams)

            return keyGenerator.generateKey()
        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun initializeCryptoObjectGenerate(biometricUtils: BiometricUtils): Cipher {
            val initializeCipher = biometricUtils.getCipher();
            val secretKey = generateSecretKey(
                biometricUtils,
                biometricUtils.YOUR_SECRET_KEY_NAME
            )
            initializeCipher.init(Cipher.ENCRYPT_MODE,secretKey)
            return initializeCipher;
        }

}