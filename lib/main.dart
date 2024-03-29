import 'package:flutter/material.dart';

import 'battery_screen.dart';
import 'connectivity_screen.dart';
import 'device_info_screen.dart';
import 'logcat_screen.dart';
import 'native_timezone_screen.dart';
import 'network_info_screen.dart';
import 'open_setting_screen.dart';
import 'package_info_screen.dart';
import 'sensor_screen.dart';
import 'share_screen.dart';
import 'sms_screen.dart';
import 'toast_screen.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Sensors Demo',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key}) : super(key: key);

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Plugins"),
      ),
      body: SingleChildScrollView(
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              TextButton(
                  onPressed: () {
                    Navigator.push(
                        context, MaterialPageRoute(builder: (context) => const SensorScreen()));
                  },
                  child: const Text("Sensor")),
              SizedBox(
                height: MediaQuery.of(context).size.height * 0.01,
              ),
              TextButton(
                  onPressed: () {
                    Navigator.push(context,
                        MaterialPageRoute(builder: (context) => const PackageInfoScreen()));
                  },
                  child: const Text("Package Info")),
              TextButton(
                  onPressed: () {
                    Navigator.push(
                        context, MaterialPageRoute(builder: (context) => const ShareScreen()));
                  },
                  child: const Text("Share")),
              TextButton(
                  onPressed: () {
                    Navigator.push(context,
                        MaterialPageRoute(builder: (context) => const ConnectivityScreen()));
                  },
                  child: const Text("Connectivity")),
              TextButton(
                  onPressed: () {
                    Navigator.push(context,
                        MaterialPageRoute(builder: (context) => const NetworkInfoScreen()));
                  },
                  child: const Text("Network info")),
              TextButton(
                  onPressed: () {
                    Navigator.push(
                        context, MaterialPageRoute(builder: (context) => const DeviceInfoScree()));
                  },
                  child: const Text("Device info")),
              TextButton(
                  onPressed: () {
                    Navigator.push(
                        context, MaterialPageRoute(builder: (context) => const BatteryScreen()));
                  },
                  child: const Text("Battery")),
              TextButton(
                  onPressed: () {
                    Navigator.push(
                        context, MaterialPageRoute(builder: (context) => const ToastScreen()));
                  },
                  child: const Text("Toast")),
              TextButton(
                  onPressed: () {
                    Navigator.push(context,
                        MaterialPageRoute(builder: (context) => const NativeTimezoneScreen()));
                  },
                  child: const Text("Native timezone")),
              TextButton(
                  onPressed: () {
                    Navigator.push(context,
                        MaterialPageRoute(builder: (context) => const SmsScreen()));
                  },
                  child: const Text("Sms")),
              TextButton(
                  onPressed: () {
                    Navigator.push(context,
                        MaterialPageRoute(builder: (context) => const OpenSettingScreen()));
                  },
                  child: const Text("Open setting")),
              TextButton(
                  onPressed: () {
                    Navigator.push(context,
                        MaterialPageRoute(builder: (context) => const LogcatScreen()));
                  },
                  child: const Text("Logcat")),
            ],
          ),
        ),
      ),
    );
  }
}
