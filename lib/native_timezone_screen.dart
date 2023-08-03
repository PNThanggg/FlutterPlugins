import 'package:flutter/material.dart';

import 'native_timezone/native_timezone.dart';

class NativeTimezoneScreen extends StatefulWidget {
  const NativeTimezoneScreen({Key? key}) : super(key: key);

  @override
  State<NativeTimezoneScreen> createState() => _NativeTimezoneScreenState();
}

class _NativeTimezoneScreenState extends State<NativeTimezoneScreen> {
  String _timezone = 'Unknown';
  String _offset = 'Offset';
  List<String> _availableTimezones = <String>[];

  @override
  void initState() {
    super.initState();
    _initData();
  }

  Future<void> _initData() async {
    try {
      _timezone = "${await NativeTimezone.getLocalTimezone()} ${await NativeTimezone.getDisplayName()}";
      _offset = await NativeTimezone.getOffset();
    } catch (e) {
      debugPrint('Could not get the local timezone');
    }

    try {
      _availableTimezones = await NativeTimezone.getAvailableTimezones();
      _availableTimezones.sort();
    } catch (e) {
      debugPrint('Could not get available timezones');
    }

    if (mounted) {
      setState(() {});
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Local timezone app'),
      ),
      body: Column(
        children: <Widget>[
          Text('Local timezone: $_timezone\n'),
          Text('Local offset: $_offset\n'),
          const Text('Available timezones: '),
          Expanded(
            child: ListView.builder(
              itemCount: _availableTimezones.length,
              itemBuilder: (_, index) => Text(_availableTimezones[index]),
            ),
          )
        ],
      ),
    );
  }
}
