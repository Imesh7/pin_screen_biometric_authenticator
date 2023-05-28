import 'package:flutter/material.dart';
import 'package:test/src/widgets/icon_pin_circle.dart';
import 'package:test/src/widgets/null_pin_circle.dart';
import 'package:test/src/widgets/pin_circle.dart';
import 'package:test/src/widgets/pin_textfield.dart';
import 'method_channel/check_biometric.dart';

typedef AuthResultCallBack = void Function(bool? result);

class PinScreenWithBiometric extends StatefulWidget {
  final int textFieldLength;
  final bool isBiometricAuthenticationEnabled;
  final TextEditingController controller;
  final double circleHeight;
  final double circleWidth;
  final Color borderColor;
  final double borderWidth;
  final AuthResultCallBack authResultCallBack;

  ///Create Pin screen with Textfield
  const PinScreenWithBiometric(
      {super.key,
      required this.textFieldLength,
      required this.controller,
      this.circleHeight = 65.0,
      this.circleWidth = 65.0,
      this.isBiometricAuthenticationEnabled = true,
      this.borderColor = Colors.black,
      this.borderWidth = 1,
      required this.authResultCallBack});

  @override
  State<PinScreenWithBiometric> createState() => _PinScreenWithBiometricState();
}

class _PinScreenWithBiometricState extends State<PinScreenWithBiometric> {
  bool? isBiometricAvailable = false;
  final TextEditingController _textEditingController = TextEditingController();

  @override
  void initState() {
    setBiometric();
    _textEditingController.addListener(() {
      widget.controller.text = _textEditingController.text;
    });
    super.initState();
  }

  Future setBiometric() async {
    isBiometricAvailable = await CheckBiometric.isBiometricAvailable();
    debugPrint('biometric status --------$isBiometricAvailable');
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 20),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          PinTextField(
            textFielsLength: widget.textFieldLength,
            textEditingController: _textEditingController,
          ),
          Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: List.generate(
                      3,
                      (index) => SizedBox(
                            height: widget.circleHeight,
                            width: widget.circleWidth,
                            child: PinCircle(
                              circleBorder: Border.all(
                                  color: widget.borderColor,
                                  width: widget.borderWidth),
                              textEditingController: _textEditingController,
                              circleIndex: index + 1,
                            ),
                          ))),
              Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: List.generate(
                      3,
                      (index) => SizedBox(
                            height: widget.circleHeight,
                            width: widget.circleWidth,
                            child: PinCircle(
                              circleBorder: Border.all(
                                  color: widget.borderColor,
                                  width: widget.borderWidth),
                              textEditingController: _textEditingController,
                              circleIndex: 3 + (index + 1),
                            ),
                          ))),
              Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: List.generate(
                      3,
                      (index) => SizedBox(
                            height: widget.circleHeight,
                            width: widget.circleWidth,
                            child: PinCircle(
                              circleBorder: Border.all(
                                  color: widget.borderColor,
                                  width: widget.borderWidth),
                              textEditingController: _textEditingController,
                              circleIndex: 6 + (index + 1),
                            ),
                          ))),
              Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: List.generate(3, (index) {
                    switch (index) {
                      case 0:
                        return SizedBox(
                            height: widget.circleHeight,
                            width: widget.circleWidth,
                            child: NullPinCircle(circleIndex: index));
                      case 2:
                        return SizedBox(
                            height: widget.circleHeight,
                            width: widget.circleWidth,
                            child: IconPinCircle(
                                textEditingController: _textEditingController,
                                circleIndex: index));
                      default:
                        return SizedBox(
                          height: widget.circleHeight,
                          width: widget.circleWidth,
                          child: PinCircle(
                              circleBorder: Border.all(
                                  color: widget.borderColor,
                                  width: widget.borderWidth),
                              textEditingController: _textEditingController,
                              circleIndex: index - 1),
                        );
                    }
                  }
                      )),
            ],
          ),
          if (widget.isBiometricAuthenticationEnabled)
            Column(
              children: [
                isBiometricAvailable!
                    ? IconButton(
                        onPressed: () async {
                          widget.authResultCallBack(
                              await CheckBiometric.authenticateBiometric());
                        },
                        icon: const Icon(
                          Icons.fingerprint,
                          size: 30,
                        ))
                    : const Text("device not have biometric")
              ],
            )
        ],
      ),
    );
  }
}
