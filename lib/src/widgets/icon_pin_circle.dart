import 'package:flutter/material.dart';

class IconPinCircle extends StatelessWidget {
  final int circleIndex;
  TextEditingController textEditingController;
  IconPinCircle(
      {super.key,
      required this.circleIndex,
      required this.textEditingController});

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.all(4),
      child: ClipOval(
        child: Material(
          color: Colors.transparent,
          child: InkWell(
            //splashColor: AppColors.pinCircleBorderColor,
            onTap: () {
              if (textEditingController.text.isNotEmpty) {
                
              List<String> intes = textEditingController.text.split('');
              final data = intes.removeLast();
              debugPrint(intes.toString());
              textEditingController.text = intes.join();
              }
            },
            child: Container(
              decoration: BoxDecoration(
                shape: BoxShape.circle,
                color: Colors.transparent,
                border: Border.all(color: Colors.black, width: 1),
              ),
              child: const Center(
                child: Icon(
                  Icons.backspace,
                  color: Colors.black,
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }
}
