
import 'method_channel.dart';

class CheckBiometric {
 static Future<bool?> isBiometricAvailable() async {
    Future<bool?> status = MethodChannelInit.platform.invokeMethod<bool>("isMobileHasBiometric");
    return status;
  }

 static Future<bool?> isAuthenticated() async {
    Future<bool?> status =  MethodChannelInit.platform.invokeMethod<bool>("checkBiometric");
    return status;
  }
}
