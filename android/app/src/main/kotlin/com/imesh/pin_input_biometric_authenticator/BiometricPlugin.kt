package com.imesh.pin_input_biometric_authenticator

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import java.util.concurrent.Executor
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.EventChannel.EventSink



typealias MyCallback = (authResult: Boolean, result: MethodChannel.Result) -> Unit
/** TestPlugin */
class BiometricPlugin: FlutterPlugin, MethodCallHandler, ActivityAware , EventChannel.StreamHandler{
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var executor: Executor

    private lateinit var context : Context
    private lateinit var channel : MethodChannel
    private lateinit var eventChannel: EventChannel
    private lateinit var androidActivityFrag: FragmentActivity
    private var sink: EventChannel.EventSink? = null;

    private lateinit var biometricAuthentication: BiometricAuthentication;


    private val TAG = "BiometricAuthentication"


    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        this.context = flutterPluginBinding.applicationContext
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "pin_input_biometric_authenticator")
        eventChannel = EventChannel(flutterPluginBinding.binaryMessenger, "pin_input_biometric_authenticator_event")
        channel.setMethodCallHandler(this)
        eventChannel.setStreamHandler(this)
        biometricAuthentication = BiometricAuthentication()

    }

    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        if(call.method == "isMobileHasBiometric") {
            val isSupportBiometric = biometricAuthentication.getIsBiometricSupport(
                context
            )
            result.success(isSupportBiometric)
        } else if(call.method == "checkBiometric") {
            executor = ContextCompat.getMainExecutor(context);
            biometricAuthentication.checkBiometricEvent(androidActivityFrag,executor ,context, sink)

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

    override fun onListen(p0: Any?, eventSink: EventSink?) {
        sink = eventSink;
    }

    override fun onCancel(p0: Any?) {
        sink = null;
    }
}
