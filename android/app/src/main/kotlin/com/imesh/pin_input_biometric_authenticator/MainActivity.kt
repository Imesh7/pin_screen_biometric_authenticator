package com.imesh.pin_input_biometric_authenticator

import android.app.Activity
import android.content.Context
import android.os.*
import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import io.flutter.embedding.android.FlutterFragmentActivity
import io.flutter.embedding.android.KeyData.CHANNEL
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodChannel
import java.util.concurrent.Executor
import java.util.concurrent.Executors


typealias MyCallback = (authResult: Boolean, result: MethodChannel.Result) -> Unit

class MainActivity() : FlutterFragmentActivity() , FlutterPlugin , ActivityAware{
    private val CHANNEL = "access_biometric"

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: androidx.biometric.BiometricPrompt
    private lateinit var promptInfo: androidx.biometric.BiometricPrompt.PromptInfo
    private lateinit var context : Context
    private lateinit var myCallback : MyCallback;
    lateinit var methodChannelResult :MethodChannel.Result

    private val TAG = "BiometricAuthentication"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "called onCreate method" )

        myCallback  = { authResult: Boolean, authenticationResult: MethodChannel.Result ->
            authenticationResult.success(authResult)
        }
        executor = ContextCompat.getMainExecutor(applicationContext);
        biometricPrompt = androidx.biometric.BiometricPrompt(this, executor,
            object :androidx.biometric.BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationSucceeded(
                    result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    myCallback(true, methodChannelResult);

                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    myCallback(false, methodChannelResult)
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Log.e(TAG, "Biometric login failed---------$errorCode---$errString ---")
                }
            })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            promptInfo = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                //.setNegativeButtonText("cancel")
                .setAllowedAuthenticators(
                    BiometricManager.Authenticators.BIOMETRIC_STRONG
                            or BiometricManager.Authenticators.DEVICE_CREDENTIAL
                            /*or BiometricManager.Authenticators.BIOMETRIC_WEAK*/)
                .build()
            executor = Executors.newSingleThreadExecutor()
        }
    }




    @RequiresApi(Build.VERSION_CODES.P)
    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        //BiometricAuthentication(this)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
                call, result ->
            if(call.method == "isMobileHasBiometric"){
               val isSupportBiometric = BiometricAuthentication().getIsBiometricSupport(
                   this.applicationContext)
                result.success(isSupportBiometric)
            } else if(call.method == "checkBiometric"){
                methodChannelResult = result;
                val biometricResult = BiometricAuthentication()
                    .checkBiometric(applicationContext,biometricPrompt, promptInfo)

                //result.success(result)
            }
        }
    }



    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        TODO("Not yet implemented")
        println("this method will call")
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        TODO("Not yet implemented")
    }

    override fun onAttachedToActivity(p0: ActivityPluginBinding) {
    }

    override fun onDetachedFromActivityForConfigChanges() {
        TODO("Not yet implemented")
    }

    override fun onReattachedToActivityForConfigChanges(p0: ActivityPluginBinding) {
        TODO("Not yet implemented")
    }

    override fun onDetachedFromActivity() {
        TODO("Not yet implemented")
    }


}