package com.zhong.base.utils;

/**
 * Created by zhong on 16/7/5.
 */

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * 检查权限的工具类
 *
 * @author mafei
 */
public class PermissionsChecker {
    private final Context mContext;
    /**
     * 读取手机权限
     */
    public static final int READ_PHONE_STATE_CODE = 1;
    /**
     * 获取相机权限
     */
    public static final int CAMERA_CODE = 2;

    /**
     * 获取存储权限
     */
    public static final int WRITE_EXTERNAL_STORAGE_CODE = 3;


    public PermissionsChecker(Context context) {
        mContext = context.getApplicationContext();
    }

    // 判断权限集合
    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    // 判断是否缺少权限
    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission)
                == PackageManager.PERMISSION_DENIED;
    }

    public static String toDescription(int type) {
        switch (type) {
            case READ_PHONE_STATE_CODE:
                return "需要在系统“权限”中打开“电话”开关，才能更好的为你服务";
            case CAMERA_CODE:
                return "需要在系统“权限”中打开“相机”开关，才能相机拍照";
            case WRITE_EXTERNAL_STORAGE_CODE:
                return "需要在系统“权限”中打开“存储”开关，才能离线缓存";
            default:
                return "需要在系统“权限”中打开相关权限，才能更好的为你服务";
        }
    }
}


/**
 * 以下权限只需要在AndroidManifest.xml中声明即可使用
 android.permission.ACCESS_LOCATION_EXTRA_COMMANDS
 android.permission.ACCESS_NETWORK_STATE
 android.permission.ACCESS_NOTIFICATION_POLICY
 android.permission.ACCESS_WIFI_STATE
 android.permission.ACCESS_WIMAX_STATE
 android.permission.BLUETOOTH
 android.permission.BLUETOOTH_ADMIN
 android.permission.BROADCAST_STICKY
 android.permission.CHANGE_NETWORK_STATE
 android.permission.CHANGE_WIFI_MULTICAST_STATE
 android.permission.CHANGE_WIFI_STATE
 android.permission.CHANGE_WIMAX_STATE
 android.permission.DISABLE_KEYGUARD
 android.permission.EXPAND_STATUS_BAR
 android.permission.FLASHLIGHT
 android.permission.GET_ACCOUNTS
 android.permission.GET_PACKAGE_SIZE
 android.permission.INTERNET
 android.permission.KILL_BACKGROUND_PROCESSES
 android.permission.MODIFY_AUDIO_SETTINGS
 android.permission.NFC
 android.permission.READ_SYNC_SETTINGS
 android.permission.READ_SYNC_STATS
 android.permission.RECEIVE_BOOT_COMPLETED
 android.permission.REORDER_TASKS
 android.permission.REQUEST_INSTALL_PACKAGES
 android.permission.SET_TIME_ZONE
 android.permission.SET_WALLPAPER
 android.permission.SET_WALLPAPER_HINTS
 android.permission.SUBSCRIBED_FEEDS_READ
 android.permission.TRANSMIT_IR
 android.permission.USE_FINGERPRINT
 android.permission.VIBRATE
 android.permission.WAKE_LOCK
 android.permission.WRITE_SYNC_SETTINGS
 com.android.alarm.permission.SET_ALARM
 com.android.launcher.permission.INSTALL_SHORTCUT
 com.android.launcher.permission.UNINSTALL_SHORTCUT
 */