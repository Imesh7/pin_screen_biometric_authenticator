import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'biometric_authenticator_platform_interface.dart';

/// An implementation of [TestPlatform] that uses method channels.
class MethodChannelBiometricAutheticator extends BiometricAuthenticatorPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('pin_input_biometric_authenticator');

  @override
  Future<bool?> getIsBiometricSupport() async {
    try {
      final version =
          await methodChannel.invokeMethod<bool>('isMobileHasBiometric');
      return version;
    } on PlatformException catch (e) {
      debugPrint(e.message);
      rethrow;
    } catch (e) {
      rethrow;
    }
  }

  @override
  Future<bool?> biometricAuthenticate() async {
    try {
      final authenticateResult =
          await methodChannel.invokeMethod<bool>('checkBiometric');
      return authenticateResult;
    } catch (e) {
      rethrow;
    }
  }
}
