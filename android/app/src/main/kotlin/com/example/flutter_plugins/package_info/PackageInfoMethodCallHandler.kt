package com.example.flutter_plugins.package_info

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class PackageInfoMethodCallHandler(
    private val applicationContext: Context
) : MethodCallHandler {
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        try {
            if (call.method == "getAll") {
                val packageManager = applicationContext.packageManager

                val info = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    packageManager.getPackageInfo(applicationContext.packageName, PackageManager.PackageInfoFlags.of(0))
                } else {
                    @Suppress("DEPRECATION") packageManager.getPackageInfo(applicationContext.packageName, 0)
                }

                val buildSignature = getBuildSignature(packageManager)

                val installerPackage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    packageManager.getInstallSourceInfo(applicationContext.packageName).installingPackageName
                } else {
                    @Suppress("DEPRECATION") packageManager.getInstallerPackageName(applicationContext.packageName)
                }


                val infoMap = HashMap<String, String>()
                infoMap.apply {
                    put("appName", info.applicationInfo.loadLabel(packageManager).toString())
                    put("packageName", applicationContext.packageName)
                    put("version", info.versionName)
                    put("buildNumber", getLongVersionCode(info).toString())
                    if (buildSignature != null) put("buildSignature", buildSignature)
                    if (installerPackage != null) put("installerStore", installerPackage)
                }.also { resultingMap ->
                    result.success(resultingMap)
                }
            } else {
                result.notImplemented()
            }
        } catch (ex: PackageManager.NameNotFoundException) {
            result.error("Name not found", ex.message, null)
        }
    }

    @Suppress("deprecation", "PackageManagerGetSignatures")
    private fun getBuildSignature(pm: PackageManager): String? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val packageInfo = pm.getPackageInfo(
                    applicationContext.packageName, PackageManager.GET_SIGNING_CERTIFICATES
                )
                val signingInfo = packageInfo.signingInfo ?: return null

                if (signingInfo.hasMultipleSigners()) {
                    signatureToSha1(signingInfo.apkContentsSigners.first().toByteArray())
                } else {
                    signatureToSha1(signingInfo.signingCertificateHistory.first().toByteArray())
                }
            } else {
                val packageInfo = pm.getPackageInfo(
                    applicationContext.packageName, PackageManager.GET_SIGNATURES
                )
                val signatures = packageInfo.signatures

                if (signatures.isNullOrEmpty() || packageInfo.signatures.first() == null) {
                    null
                } else {
                    signatureToSha1(signatures.first().toByteArray())
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            null
        } catch (e: NoSuchAlgorithmException) {
            null
        }
    }

    @Suppress("deprecation")
    private fun getLongVersionCode(info: PackageInfo): Long {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            info.longVersionCode
        } else {
            info.versionCode.toLong()
        }
    }

    @Throws(NoSuchAlgorithmException::class)
    private fun signatureToSha1(sig: ByteArray): String {
        val digest = MessageDigest.getInstance("SHA1")
        digest.update(sig)
        val hashText = digest.digest()
        return bytesToHex(hashText)
    }

    private fun bytesToHex(bytes: ByteArray): String {
        val hexArray = charArrayOf(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        )
        val hexChars = CharArray(bytes.size * 2)
        var v: Int
        for (j in bytes.indices) {
            v = bytes[j].toInt() and 0xFF
            hexChars[j * 2] = hexArray[v ushr 4]
            hexChars[j * 2 + 1] = hexArray[v and 0x0F]
        }

        return String(hexChars)
    }
}