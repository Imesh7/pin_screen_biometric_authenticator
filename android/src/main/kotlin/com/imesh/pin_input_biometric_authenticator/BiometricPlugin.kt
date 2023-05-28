package com.imesh.pin_input_biometric_authenticator

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import com.imesh.pin_input_biometric_authenticator.BiometricAuthentication

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import java.util.concurrent.Executor
import io.flutter.embedding.android.FlutterFragmentActivity
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import androidx.core.content.ContextCompat
import android.util.Log
import android.view.Display
import androidx.biometric.BiometricManager
import androidx.fragment.app.FragmentActivity
import io.flutter.embedding.engine.FlutterEngine
import java.util.concurrent.Executors


typealias MyCallback = (authResult: Boolean, result: MethodChannel.Result) -> Unit
/** TestPlugin */
class BiometricPlugin: FlutterPlugin, MethodCallHandler, ActivityAware {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var executor: Executor

    private lateinit var context : Context
    private lateinit var myCallback : MyCallback
    lateinit var methodChannelResult :MethodChannel.Result
    private lateinit var channel : MethodChannel
    private lateinit var androidActivityFrag: FragmentActivity


    private val TAG = "BiometricAuthentication"



    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        this.context = flutterPluginBinding.applicationContext
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "pin_input_biometric_authenticator")
        channel.setMethodCallHandler(this)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        if(call.method == "isMobileHasBiometric") {
            val isSupportBiometric = BiometricAuthentication().getIsBiometricSupport(
                context
            )
            result.success(isSupportBiometric)
        } else if(call.method == "checkBiometric") {
            executor = ContextCompat.getMainExecutor(context);

            BiometricAuthentication()
                .checkBiometric(androidActivityFrag,executor ,context, result)
        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
        context = binding.applicationContext
    }

    override fun onAttachedToActivity(p0: ActivityPluginBinding) {
        androidActivityFrag = p0.activity as FragmentActivity
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
