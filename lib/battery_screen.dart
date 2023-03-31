import 'dart:async';

import 'package:flutter/material.dart';

import 'battery/battery_plus.dart';
import 'battery/src/enums.dart';

class BatteryScreen extends StatefulWidget {
  const BatteryScreen({Key? key}) : super(key: key);

  @override
  State<BatteryScreen> createState() => _BatteryScreenState();
}

class _BatteryScreenState extends State<BatteryScreen> {
  final Battery _battery = Battery();

  BatteryState? _batteryState;
  StreamSubscription<BatteryState>? _batteryStateSubscription;

  @override
  void initState() {
    super.initState();
    _battery.batteryState.then(_updateBatteryState);
    _batteryStateSubscription = _battery.onBatteryStateChanged.listen(_updateBatteryState);
  }

  @override
  void dispose() {
    super.dispose();
    if (_batteryStateSubscription != null) {
      _batteryStateSubscription!.cancel();
    }
  }

  void _updateBatteryState(BatteryState state) {
    if (_batteryState == state) return;
    setState(() {
      _batteryState = state;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Battery screen'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text('$_batteryState'),
            ElevatedButton(
              onPressed: () {
                _battery.batteryLevel.then(
                  (batteryLevel) {
                    showDialog<void>(
                      context: context,
                      builder: (_) => AlertDialog(
                        content: Text('Battery: $batteryLevel%'),
                        actions: <Widget>[
                          TextButton(
                            onPressed: () {
                              Navigator.pop(context);
                            },
                            child: const Text('OK'),
                          )
                        ],
                      ),
                    );
                  },
                );
              },
              child: const Text('Get battery level'),
            ),
            ElevatedButton(
                onPressed: () {
                  _battery.isInBatterySaveMode.then(
                    (isInPowerSaveMode) {
                      showDialog<void>(
                        context: context,
                        builder: (_) => AlertDialog(
                          content: Text('Is on low power mode: $isInPowerSaveMode'),
                          actions: <Widget>[
                            TextButton(
                              onPressed: () {
                                Navigator.pop(context);
                              },
                              child: const Text('OK'),
                            )
                          ],
                        ),
                      );
                    },
                  );
                },
                child: const Text('Is on low power mode'))
          ],
        ),
      ),
    );
  }
}
