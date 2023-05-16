import 'package:flutter/material.dart';

class PinCircle extends StatelessWidget {
  final int circleIndex;
  final TextEditingController textEditingController;
  final BoxBorder circleBorder;
  final TextStyle buttonTextStyle;
   const PinCircle(
      {super.key,
      required this.circleIndex,
      required this.textEditingController,
      required this.circleBorder,
      this.buttonTextStyle = const TextStyle(color: Colors.black)});

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.all(4),
      child: ClipOval(
        child: Material(
          color: Colors.transparent,
          child: InkWell(
            //splashColor: AppColors.pinCircleBorderColor,
            onTap: () {
              debugPrint('previos text =${textEditingController.text}');
              if (textEditingController.value.text.length < 6) {
                textEditingController.text =
                    '${textEditingController.text}${circleIndex.toString()}';
              }

              debugPrint("after text =${textEditingController.text}");
            },
            child: Container(
              decoration: BoxDecoration(
                shape: BoxShape.circle,
                color: Colors.transparent,
                border: circleBorder,
              ),
              child: Center(
                child: Text(
                  circleIndex.toString(),
                  style: buttonTextStyle,
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }
}
