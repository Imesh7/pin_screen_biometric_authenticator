import 'package:flutter/material.dart';
import 'package:pin_input_biometric_authenticator/pin_input_biometric_authenticator.dart';


void main() {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  ///String _platformVersion = 'Unknown';
  //final _testPlugin = Test();
  bool? status = false;

  @override
  void initState() {
    super.initState();
  }


  final TextEditingController textEditingController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            children: [
              PinInptBiometricAuthenticator(
                controller: textEditingController,
                textFieldLength: 6,
                authResultCallBack: (bool? result) {
                  debugPrint("BIOMETRIC RESULT IS `$result`");
                },
              ),
            ],
          ),
        ),
      ),
    );
  }
}
