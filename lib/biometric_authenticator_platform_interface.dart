import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'biometric_authenticator_method_channel.dart';

abstract class BiometricAuthenticatorPlatform extends PlatformInterface {
  /// Constructs a TestPlatform.
  BiometricAuthenticatorPlatform() : super(token: _token);

  static final Object _token = Object();

  static BiometricAuthenticatorPlatform _instance = MethodChannelBiometricAutheticator();

  /// The default instance of [TestPlatform] to use.
  ///
  /// Defaults to [MethodChannelTest].
  static BiometricAuthenticatorPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [TestPlatform] when
  /// they register themselves.
  static set instance(BiometricAuthenticatorPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }


  Future<bool?> getIsBiometricSupport() {
    throw UnimplementedError('CheckIsDesvicesupportBiometric() has not been implemented.');
  }

  Future<bool?> biometricAuthenticate(){
    throw UnimplementedError('BiometricAuthenticate() has not been implemented.');
  }
  
}
