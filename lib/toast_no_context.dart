import 'package:flutter/material.dart';

import 'toast/toast.dart';

class ToastNoContext extends StatefulWidget {
  const ToastNoContext({super.key});

  @override
  _ToastNoContextState createState() => _ToastNoContextState();
}

class _ToastNoContextState extends State<ToastNoContext> {
  void showLongToast() {
    Toast.showToast(
      msg: "This is Long Toast",
      toastLength: ToastLength.LENGTH_LONG,
      fontSize: 18.0,
    );
  }

  void showWebColoredToast() {
    Toast.showToast(
      msg: "This is Colored Toast with android duration of 5 Sec",
      toastLength: ToastLength.LENGTH_SHORT,
      textColor: Colors.black,
      timeInSecForIosWeb: 5,
    );
  }

  void showColoredToast() {
    Toast.showToast(
        msg: "This is Colored Toast with android duration of 5 Sec",
        toastLength: ToastLength.LENGTH_SHORT,
        backgroundColor: Colors.red,
        textColor: Colors.white);
  }

  void showShortToast() {
    Toast.showToast(
        msg: "This is Short Toast", toastLength: ToastLength.LENGTH_SHORT, timeInSecForIosWeb: 1);
  }

  void showTopShortToast() {
    Toast.showToast(
        msg: "This is Top Short Toast",
        toastLength: ToastLength.LENGTH_SHORT,
        gravity: ToastGravity.TOP,
        timeInSecForIosWeb: 1);
  }

  void showCenterShortToast() {
    Toast.showToast(
        msg: "This is Center Short Toast",
        toastLength: ToastLength.LENGTH_SHORT,
        gravity: ToastGravity.CENTER);
  }

  void cancelToast() {
    Toast.cancel();
  }

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Flutter Toast'),
      ),
      body: Center(
        child: Column(
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.all(10.0),
              child:
              ElevatedButton(onPressed: showLongToast, child: const Text('Show Long Toast')),
            ),
            Padding(
              padding: const EdgeInsets.all(10.0),
              child: ElevatedButton(
                  onPressed: showShortToast, child: const Text('Show Short Toast')),
            ),
            Padding(
              padding: const EdgeInsets.all(10.0),
              child: ElevatedButton(
                  onPressed: showCenterShortToast, child: const Text('Show Center Short Toast')),
            ),
            Padding(
              padding: const EdgeInsets.all(10.0),
              child: ElevatedButton(
                  onPressed: showTopShortToast, child: const Text('Show Top Short Toast')),
            ),
            Padding(
              padding: const EdgeInsets.all(10.0),
              child: ElevatedButton(
                  onPressed: showColoredToast, child: const Text('Show Colored Toast')),
            ),
            Padding(
              padding: const EdgeInsets.all(10.0),
              child: ElevatedButton(
                  onPressed: showWebColoredToast, child: const Text('Show  Web Colored Toast')),
            ),
            Padding(
              padding: const EdgeInsets.all(10.0),
              child: ElevatedButton(
                onPressed: cancelToast,
                child: const Text('Cancel Toasts'),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
