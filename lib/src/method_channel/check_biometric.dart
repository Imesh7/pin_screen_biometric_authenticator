
import 'dart:async';

import 'package:flutter/services.dart';

import '../../biometric_authenticator_platform_interface.dart';



class CheckBiometric {


 static Future<bool?> isBiometricAvailable() {
    return BiometricAuthenticatorPlatform.instance.getIsBiometricSupport();
  }

  static Future<bool?> authenticateBiometric() {
    return BiometricAuthenticatorPlatform.instance.biometricAuthenticate();
  }

}
