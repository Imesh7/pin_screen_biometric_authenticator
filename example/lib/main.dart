import 'package:flutter/material.dart';
import 'package:pin_input_biometric_authenticator/pin_input_biometric_authenticator.dart';

void main() {
  //WidgetsFlutterBinding.ensureInitialized();
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(home: HomeScreen());
  }
}

class HomeScreen extends StatelessWidget {
  HomeScreen({super.key});

  final TextEditingController textEditingController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
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
                showDialog(
                    context: context,
                    builder: (_) {
                      return SizedBox(
                        height: 200,
                        width: 200,
                        child: AlertDialog(
                          title: Center(
                              child: Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            crossAxisAlignment: CrossAxisAlignment.center,
                            children: [
                              result!
                                  ? Container(
                                      height: 50,
                                      width: 50,
                                      decoration: BoxDecoration(
                                          color: Colors.green,
                                          borderRadius:
                                              BorderRadius.circular(25)),
                                      child: const Icon(Icons.add))
                                  : Container(
                                      height: 50,
                                      width: 50,
                                      decoration: BoxDecoration(
                                          color: Colors.red,
                                          borderRadius:
                                              BorderRadius.circular(25)),
                                      child: const Icon(Icons.sms_failed)),
                              const SizedBox(
                                height: 10,
                              ),
                              Text(
                                  "Bioemetric ${result ? 'Success' : 'Failed'}"),
                            ],
                          )),
                          content: Text(
                              "You have Authenticated ${result ? 'Successfully' : 'Failed'}"),
                        ),
                      );
                    });
              },
            ),
          ],
        ),
      ),
    );
  }
}
