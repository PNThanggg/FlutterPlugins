import 'package:flutter/material.dart';
import 'dart:async';

import 'logcat/logcat.dart';

class LogcatScreen extends StatefulWidget {
  const LogcatScreen({super.key});

  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<LogcatScreen> {
  String _logs = 'Nothing yet';

  @override
  void initState() {
    super.initState();
    _getLogs();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Logcat Plugin example app'),
      ),
      body: SingleChildScrollView(
        child: Text(_logs),
      ),
    );
  }

  Future<void> _getLogs() async {
    final String logs = await Logcat.execute();
    setState(() {
      _logs = logs;
    });
  }
}