package com.quyenlx.learningjapanese.Util;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by LxQuyen on 20/08/2016.
 */
public class DeviceSupport {
    /**
     * Checking device has camera hardware or not
     */
    public static boolean isDeviceSupportCamera(Context context) {
        if (context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
}
