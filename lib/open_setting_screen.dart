import 'package:flutter/material.dart';

import 'open_setting/open_settings.dart';

class OpenSettingScreen extends StatefulWidget {
  const OpenSettingScreen({Key? key}) : super(key: key);

  @override
  State<OpenSettingScreen> createState() => _OpenSettingScreenState();
}

class _OpenSettingScreenState extends State<OpenSettingScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(
          'Open Settings',
          style: TextStyle(color: Colors.white),
        ),
        backgroundColor: Colors.blue,
      ),
      body: SafeArea(
        child: Center(
          child: Padding(
            padding: const EdgeInsets.all(24.0),
            child: SingleChildScrollView(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openWIFISetting();
                    },
                    child: const Center(
                      child: Text('Wi-fi'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openDataRoamingSetting();
                    },
                    child: const Center(
                      child: Text('Data Roaming'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openLocationSourceSetting();
                    },
                    child: const Center(
                      child: Text('Location Source'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openAppSetting();
                    },
                    child: const Center(
                      child: Text('App Settings'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openBluetoothSetting();
                    },
                    child: const Center(
                      child: Text('Bluetooth'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openNotificationSetting();
                    },
                    child: const Center(
                      child: Text('Notification'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openSecuritySetting();
                    },
                    child: const Center(
                      child: Text('Security'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openSoundSetting();
                    },
                    child: const Center(
                      child: Text('Sound'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openMainSetting();
                    },
                    child: const Center(
                      child: Text('Main Setting'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openDateSetting();
                    },
                    child: const Center(
                      child: Text('Date'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openDisplaySetting();
                    },
                    child: const Center(
                      child: Text('Display'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openAccessibilitySetting();
                    },
                    child: const Center(
                      child: Text('Accessibility'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openAddAccountSetting();
                    },
                    child: const Center(
                      child: Text('Add Account'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openAirplaneModeSetting();
                    },
                    child: const Center(
                      child: Text('Air Plane Mode'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openApnSetting();
                    },
                    child: const Center(
                      child: Text('Apn'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openApplicationDetailsSetting();
                    },
                    child: const Center(
                      child: Text('Application Details'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openApplicationDevelopmentSetting();
                    },
                    child: const Center(
                      child: Text('Application Development'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openAppNotificationBubbleSetting();
                    },
                    child: const Center(
                      child: Text('App Notification Bubble'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openAppNotificationSetting();
                    },
                    child: const Center(
                      child: Text('App Notification'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openSearchSetting();
                    },
                    child: const Center(
                      child: Text('Search'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openBatterySaverSetting();
                    },
                    child: const Center(
                      child: Text('Battery Saver'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openBiometricEnrollSetting();
                    },
                    child: const Center(
                      child: Text('Biometric Enroll'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openCaptioningSetting();
                    },
                    child: const Center(
                      child: Text('Captioning'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openCastSetting();
                    },
                    child: const Center(
                      child: Text('Cast'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openChannelNotificationSetting();
                    },
                    child: const Center(
                      child: Text('Channel Notification'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openActionConditionProviderSetting();
                    },
                    child: const Center(
                      child: Text('Action Condition Provider'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openDataUsageSetting();
                    },
                    child: const Center(
                      child: Text('Data Usage'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openDeviceInfoSetting();
                    },
                    child: const Center(
                      child: Text('Device Info'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openDreamSetting();
                    },
                    child: const Center(
                      child: Text('Dream'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openHardKeyboardSetting();
                    },
                    child: const Center(
                      child: Text('Hard Keyboard'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openHomeSetting();
                    },
                    child: const Center(
                      child: Text('Home'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openIgnoreBackgroundDataRestrictionsSetting();
                    },
                    child: const Center(
                      child: Text('Ignore Background Data Restrictions'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openIgnoreBatteryOptimizationSetting();
                    },
                    child: const Center(
                      child: Text('Ignore Battery Optimization'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openInputMethodSetting();
                    },
                    child: const Center(
                      child: Text('Input Method'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openInputMethodSubtypeSetting();
                    },
                    child: const Center(
                      child: Text('Input Method Subtype'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openInternalStorageSetting();
                    },
                    child: const Center(
                      child: Text('Internal Storage'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openLocaleSetting();
                    },
                    child: const Center(
                      child: Text('Locale'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openManageAllApplicationsSetting();
                    },
                    child: const Center(
                      child: Text('Manage All Applications'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openManageAllFilesAccessPermissionSetting();
                    },
                    child: const Center(
                      child: Text('Manage All Files Access Permission'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openManageApplicationSetting();
                    },
                    child: const Center(
                      child: Text('Manage Application'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openManageAppAllFilesAccessPermissionSetting();
                    },
                    child: const Center(
                      child: Text('Manage App All Files Access Permission'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openManageDefaultAppsSetting();
                    },
                    child: const Center(
                      child: Text('Manage Default Apps'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openManageOverlayPermissionSetting();
                    },
                    child: const Center(
                      child: Text('Manage Overlay Permission'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openManageUnknownAppSourceSetting();
                    },
                    child: const Center(
                      child: Text('Manage Unknown App Source'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openManageWriteSetting();
                    },
                    child: const Center(
                      child: Text('Manage Write'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openMemoryCardSetting();
                    },
                    child: const Center(
                      child: Text('Memory Card'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openNetworkOperatorSetting();
                    },
                    child: const Center(
                      child: Text('Network Operator'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openNFCSetting();
                    },
                    child: const Center(
                      child: Text('NFC'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openNightDisplaySetting();
                    },
                    child: const Center(
                      child: Text('Night Display'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openNotificationAssistantSetting();
                    },
                    child: const Center(
                      child: Text('Notification Assistant'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openNotificationListenerDetailSetting();
                    },
                    child: const Center(
                      child: Text('Notification Listener Detail'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openActionNotificationListenerSetting();
                    },
                    child: const Center(
                      child: Text('Action Notification Listener'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openNotificationPolicyAccessSetting();
                    },
                    child: const Center(
                      child: Text('Notification Policy Access'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openActionPrintSetting();
                    },
                    child: const Center(
                      child: Text('Action Print'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openPrivacySetting();
                    },
                    child: const Center(
                      child: Text('Privacy'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openProcessWifiEasyConnectUriSetting();
                    },
                    child: const Center(
                      child: Text('Process Wifi Easy Connect Uri'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openQuickAccessWalletSetting();
                    },
                    child: const Center(
                      child: Text('Quick Access Wallet'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openQuickLaunchSetting();
                    },
                    child: const Center(
                      child: Text('Quick Launch'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openShowRegulatoryInfoSetting();
                    },
                    child: const Center(
                      child: Text('Show Regulatory Info'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openShowWorkPolicyInfoSetting();
                    },
                    child: const Center(
                      child: Text('Show Work Policy Info'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openStorageVolumeAccessSetting();
                    },
                    child: const Center(
                      child: Text('Storage Volume Access'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openSyncSetting();
                    },
                    child: const Center(
                      child: Text('Sync'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openUsageAccessSetting();
                    },
                    child: const Center(
                      child: Text('Usage Access'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openUserDictionarySetting();
                    },
                    child: const Center(
                      child: Text('User Dictionary'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openVoiceControllAirplaneModeSetting();
                    },
                    child: const Center(
                      child: Text('Voice Controll Airplane Mode'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openVoiceControllBatterySaverModeSetting();
                    },
                    child: const Center(
                      child: Text('Voice Controll Battery Saver Mode'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openVoiceControllDoNotDisturbModeSetting();
                    },
                    child: const Center(
                      child: Text('Voice Controll Do Not Disturb Mode'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openVoiceInputSetting();
                    },
                    child: const Center(
                      child: Text('Voice Input'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openVPNSetting();
                    },
                    child: const Center(
                      child: Text('VPN'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openVrListenerSetting();
                    },
                    child: const Center(
                      child: Text('Vr Listener'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openWebViewSetting();
                    },
                    child: const Center(
                      child: Text('Web View'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openWifiAddNetworksSetting();
                    },
                    child: const Center(
                      child: Text('Wifi Add Networks'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openWifiIpSetting();
                    },
                    child: const Center(
                      child: Text('Wifi Ip'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openWirelessSetting();
                    },
                    child: const Center(
                      child: Text('Wireless'),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                  ElevatedButton(
                    onPressed: () {
                      OpenSettings.openZenModePrioritySetting();
                    },
                    child: const Center(
                      child: Text('Zen Mode Priority '),
                    ),
                  ),
                  const SizedBox(
                    height: 15,
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}
