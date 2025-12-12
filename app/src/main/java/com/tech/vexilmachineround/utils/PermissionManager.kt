package com.tech.vexilmachineround.utils

import android.app.Activity
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.net.toUri

object PermissionManager {

    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    //callback contracts.
    interface PermissionResultCallback {
        fun onPermissionResult(granted: List<String>, denied: List<String>)
    }

    /**
     * Simple check for any manifest permission.
     * On pre‑M devices returns true for dangerous permissions because they
     * are granted at install time.
     */

    fun hasPermission(permission: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ContextCompat.checkSelfPermission(
                appContext,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        } else true
    }

    fun hasPermissions(permissions: Array<String>): Boolean {
        return permissions.all { hasPermission(it) }
    }


    /**
     * For normal / signature permissions, just checking is enough.
     */

    fun hasSignatureOrNormalPermission(permission: String): Boolean {
        return hasPermission(permission)
    }


    /**
     * THe below helper helps you call from Activity/Fragment to create a launcher
     * that is wired to PermissionManager’s standardized callback.
     *
     * Example:
     *   private lateinit var cameraPermissionLauncher: ActivityResultLauncher<Array<String>>
     *
     *   override fun onCreate(...) {
     *       cameraPermissionLauncher =
     *           PermissionManager.createRequestMultiplePermissionsLauncher(this) { granted, denied -> ... }
     *   }
     *
     */

    fun createRequestMultiplePermissionsLauncher(
        caller: ActivityResultCaller,
        callback: PermissionResultCallback
    ): ActivityResultLauncher<Array<String>> {
        return caller.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            val granted = mutableListOf<String>()
            val denied = mutableListOf<String>()
            result.forEach { (permission, isGranted) ->
                if (isGranted) granted.add(permission) else denied.add(permission)
            }
            callback.onPermissionResult(granted, denied)
        }
    }


    // -------- Overlay (SYSTEM_ALERT_WINDOW) --------

    fun hasOverLayPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.canDrawOverlays(appContext)
        } else true
    }

    //open overlay settings.
    fun openOverlaySettings(activity: Activity) {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            "package:${appContext.packageName}".toUri()
        )
        activity.startActivity(intent)
    }

    //exact alarm.
    @RequiresApi(Build.VERSION_CODES.S)
    fun canScheduleExactAlarm(): Boolean {
        val alarmManager = appContext.getSystemService(AlarmManager::class.java)
        return alarmManager?.canScheduleExactAlarms() == true
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun openExactAlarmSettings(activity: Activity) {
        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
            data = "package:${appContext.packageName}".toUri()
        }
        activity.startActivity(intent)
    }


    // -------- has external storage permission (MANAGE_EXTERNAL_STORAGE) --------
    fun hasManageAllFilesPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            true
        }
    }

    fun openManageAllFilesSettings(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                data = "package:${appContext.packageName}".toUri()
            }
            activity.startActivity(intent)
        }
    }

    // Open this app’s App Info screen (manual permission management)
    fun openAppInfoSettings(activity: Activity) {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", appContext.packageName, null)
        )
        activity.startActivity(intent)
    }

}