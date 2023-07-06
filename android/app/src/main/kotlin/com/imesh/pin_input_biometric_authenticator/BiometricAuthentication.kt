package com.imesh.pin_input_biometric_authenticator
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.media.metrics.Event
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.provider.Settings
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import io.flutter.embedding.android.FlutterFragmentActivity
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class BiometricAuthentication() : FlutterFragmentActivity(),  FlutterPlugin, ActivityAware, MethodChannel.MethodCallHandler {
    private lateinit var biometricPrompt: androidx.biometric.BiometricPrompt
    private lateinit var promptInfo: androidx.biometric.BiometricPrompt.PromptInfo
    private lateinit var context : Context
    private lateinit var activity: Activity
    private var biometricAuthenticatedStatus : Boolean = false
    private var biometricUtils : BiometricUtils = BiometricUtils();

    private val TAG = "BiometricAuthentication"

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    @RequiresApi(Build.VERSION_CODES.P)
        fun getIsBiometricSupport(applicationContext: Context): Boolean {

            val biometricManager = BiometricManager.from(applicationContext)
            when (biometricManager.canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
                        or BiometricManager.Authenticators.DEVICE_CREDENTIAL
                        or BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
                BiometricManager.BIOMETRIC_SUCCESS -> return true
                    //Log.d("MY_APP_TAG", "App can authenticate using biometrics.")

                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->return  false
                    //Log.e("MY_APP_TAG", "No biometric features available on this device.")
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->return false
                    //Log.e("MY_APP_TAG", "Biometric features are currently unavailable.")
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    // Prompts the user to create credentials that your app accepts.

                    try {
                        startActivity(Intent(Settings.ACTION_BIOMETRIC_ENROLL))
                    } catch (e: Exception) {
                        startActivity(Intent(Settings.ACTION_SETTINGS))
                    }
                        return false
                    //startActivityForResult(enrollIntent, 500)
                }
            }
            return false
        }

    @SuppressLint("WrongConstant")
    @RequiresApi(Build.VERSION_CODES.M)
    fun checkBiometricEvent(
        fragmentActivity: FragmentActivity,
        executor: Executor,
        context: Context,
        eventChannelResult: EventChannel.EventSink?
    ): Boolean {
        val biometricManager = BiometricManager.from(context)
        val hasBiometricsCapability = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS
        println("biometric event")
        if (hasBiometricsCapability) {
            val cryptoObject = BiometricPrompt.CryptoObject(
                biometricUtils.initializeCryptoObjectGenerate(BiometricUtils())
            );
            biometricPrompt = androidx.biometric.BiometricPrompt(fragmentActivity, executor,
                object :androidx.biometric.BiometricPrompt.AuthenticationCallback(){
                    override fun onAuthenticationSucceeded(
                        result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        println("biometric event success")
                        eventChannelResult?.success(true);
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        eventChannelResult?.success(false)
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        Log.e(TAG, "Biometric login failed---------$errorCode---$errString ---")
                    }
                })

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                promptInfo = promptInfoBuild()
            }
            biometricPrompt.authenticate(promptInfo, cryptoObject)

        } else{
            Toast.makeText(context,"Device not Activated Biometric", Toast.LENGTH_SHORT).show()
        }
        return biometricAuthenticatedStatus
    }


    private fun promptInfoBuild() : PromptInfo {
        return androidx.biometric.BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            //.setNegativeButtonText("cancel")
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
                        or BiometricManager.Authenticators.DEVICE_CREDENTIAL
                /*or BiometricManager.Authenticators.BIOMETRIC_WEAK*/)
            .build()
    }


    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        TODO("Not yet implemented")
        context = binding.applicationContext
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        TODO("Not yet implemented")
    }




    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        TODO("Not yet implemented")
        this.activity = binding.activity
        println(activity)
    }

    override fun onDetachedFromActivityForConfigChanges() {
        TODO("Not yet implemented")

    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        TODO("Not yet implemented")

    }

    override fun onDetachedFromActivity() {
        TODO("Not yet implemented")
    }

    override fun onMethodCall(methodCall: MethodCall, methodChannelResult: MethodChannel.Result) {
        TODO("Not yet implemented")
        methodChannelResult.success(biometricAuthenticatedStatus)
    }


}

