import 'package:flutter/material.dart';

import 'toast_context.dart';
import 'toast_no_context.dart';

GlobalKey<NavigatorState> navigatorKey = GlobalKey<NavigatorState>();

class ToastScreen extends StatefulWidget {
  const ToastScreen({Key? key}) : super(key: key);

  @override
  State<ToastScreen> createState() => _ToastScreenState();
}

class _ToastScreenState extends State<ToastScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Toast"),
      ),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          ElevatedButton(
            onPressed: () {
              Navigator.of(context).push(MaterialPageRoute(
                builder: (context) => const ToastNoContext(),
              ));
            },
            child: const Text("Flutter Toast No Context"),
          ),
          const SizedBox(
            height: 24.0,
          ),
          ElevatedButton(
            onPressed: () {
              Navigator.of(context).push(MaterialPageRoute(
                builder: (context) => const ToastContext(),
              ));
            },
            child: const Text("Flutter Toast Context"),
          ),
        ],
      ),
    );
  }
}
