import 'package:flutter/material.dart';
import 'package:pin_input_biometric_authenticator/pin_input_biometric_authenticator.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: HomePage(),
    );
  }
}

class HomePage extends StatelessWidget {
   HomePage({super.key});

  final TextEditingController textEditingController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      body: Center(
        child: SizedBox(
          width: MediaQuery.of(context).size.width * 0.75,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              //implementation
              PinScreenWithBiometric(
                controller: textEditingController,
                textFieldLength: 8,
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
