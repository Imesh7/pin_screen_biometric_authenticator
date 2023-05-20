import 'package:flutter/material.dart';

class PinTextField extends StatelessWidget {
  final int textFielsLength;
  final TextEditingController textEditingController;
  final TextStyle textStyle;
  final bool obsecureText;
  const PinTextField(
      {super.key,
      required this.textFielsLength,
      required this.textEditingController,
      this.textStyle = const TextStyle(letterSpacing: 3.0),
      this.obsecureText = true});

  @override
  Widget build(BuildContext context) {
    return TextField(
      showCursor: false,
      controller: textEditingController,
      //showCursor: true,
      readOnly: true,
      textAlign: TextAlign.center,
      style: textStyle,
      maxLength: textFielsLength,
      obscureText: obsecureText,
      decoration: const InputDecoration(
        counterText: '',
        //border: InputBorder.none,
      ),
      onChanged: ((value) {
        debugPrint("textfield value $value");
      }),
    );
  }
}
