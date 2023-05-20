import 'package:flutter/material.dart';

class NullPinCircle extends StatelessWidget {
  final int circleIndex;
  const NullPinCircle({super.key, required this.circleIndex});

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.all(4),
      child: ClipOval(
        child: Material(
          color: Colors.transparent,
          child: InkWell(
            //splashColor: AppColors.pinCircleBorderColor,
            onTap: null,
            child: Container(
              decoration: BoxDecoration(
                shape: BoxShape.circle,
                color: Colors.transparent,
                border: Border.all(color: Colors.transparent, width: 1),
              ),
              child: Center(
                child: Text(
                  circleIndex.toString(),
                  style: const TextStyle(color: Colors.transparent),
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }
}
