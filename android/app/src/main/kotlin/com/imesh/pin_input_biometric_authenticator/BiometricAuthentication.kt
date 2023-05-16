package com.imesh.pin_input_biometric_authenticator
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.provider.Settings
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.AuthenticationCallback
import androidx.core.content.ContextCompat
import io.flutter.embedding.android.FlutterFragmentActivity
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class BiometricAuthentication() : FlutterFragmentActivity(),  FlutterPlugin, ActivityAware, MethodChannel.MethodCallHandler {
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: androidx.biometric.BiometricPrompt
    private lateinit var promptInfo: androidx.biometric.BiometricPrompt.PromptInfo
    private lateinit var context : Context
    private lateinit var activity: Activity
    private var biometricAuthenticatedStatus : Boolean = false


    private val TAG = "BiometricAuthentication"



    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.go_to_setting)
        call(context)
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

    private fun call(context: Context){
        Log.e(TAG, "called create method" )
        executor = ContextCompat.getMainExecutor(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            promptInfo = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                //.setNegativeButtonText("cancel")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
                .build()
            executor = Executors.newSingleThreadExecutor()
        }
    }

    @SuppressLint("WrongConstant")
    @RequiresApi(Build.VERSION_CODES.M)
    fun checkBiometric(
        context: Context,
        biometricPrompt: BiometricPrompt,
                       promptInfo: BiometricPrompt.PromptInfo): Boolean {
        val biometricManager = BiometricManager.from(context)
        val hasBiometricsCapability = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS

        if (hasBiometricsCapability) {

            val cryptoObject = BiometricPrompt.CryptoObject(
                BiometricUtils().initializeCryptoObjectGenerate(BiometricUtils())
            );
            biometricPrompt.authenticate(promptInfo, cryptoObject)



        } else{
            /*try {
                var intent =Intent(Settings.ACTION_BIOMETRIC_ENROLL)
                startActivityForResult(intent, 100)
            } catch (e: Exception) {
                startActivity(Intent(Settings.ACTION_SETTINGS))
            }*/
            showGoToSettingsDialog("SS","ss")
        }
      return biometricAuthenticatedStatus
    }

    private fun showGoToSettingsDialog(title: String, descriptionText: String) {
        val view: View = LayoutInflater.from(activity).inflate(R.layout.go_to_setting, null, false)

        val message: TextView = view.findViewById(R.id.fingerprint_required)
        val description: TextView = view.findViewById(R.id.go_to_setting_description)
        message.setText(title)
        description.setText(descriptionText)
        val context: Context = ContextThemeWrapper(activity, R.style.LaunchTheme)
        val goToSettingHandler = DialogInterface.OnClickListener { dialog, which ->
            //completionHandler.complete(Messages.AuthResult.FAILURE)
            // stop()
            activity.startActivity(Intent(Settings.ACTION_SECURITY_SETTINGS))
        }
        val cancelHandler = DialogInterface.OnClickListener { dialog, which ->
            //completionHandler.complete(Messages.AuthResult.FAILURE)
            // stop()
        }

        AlertDialog.Builder(context)
            .setView(view)
            .setPositiveButton("ss", goToSettingHandler)
            .setNegativeButton("ss", cancelHandler)
            .setCancelable(false)
            .show();

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

    override fun onMethodCall(p0: MethodCall, p1: MethodChannel.Result) {
        TODO("Not yet implemented")
        p1.success(biometricAuthenticatedStatus)
    }


}

