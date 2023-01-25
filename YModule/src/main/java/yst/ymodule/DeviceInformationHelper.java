package yst.ymodule;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.util.UUID;

public class DeviceInformationHelper {


    private static String Device_IMEI = "";
    private static String Device_Model = "";
    private static String Device_Name = "";
    private static String Device_Brand = "";

    /**
     * Return pseudo unique ID
     *
     * @return ID
     */
    public static String getUniquePsuedoID() {
        // If all else fails, if the user does have lower than API 9 (lower
        // than Gingerbread), has reset their device or 'Secure.ANDROID_ID'
        // returns 'null', then simply the ID returned will be solely based
        // off their Android device information. This is where the collisions
        // can happen.
        // Thanks http://www.pocketmagic.net/?p=1662!
        // Try not to use DISPLAY, HOST or ID - these items could change.
        // If there are collisions, there will be overlapping data
        String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);

        // Thanks to @Roman SL!
        // http://stackoverflow.com/a/4789483/950427
        // Only devices with API >= 9 have android.os.Build.SERIAL
        // http://developer.android.com/reference/android/os/Build.html#SERIAL
        // If a user upgrades software or roots their device, there will be a duplicate entry
        String serial = null;
        try {
            serial = Build.class.getField("SERIAL").get(null).toString();

            // Go ahead and return the serial for api => 9
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            // String needs to be initialized
            serial = "serial"; // some value
        }

        // Thanks @Joe!
        // http://stackoverflow.com/a/2853253/950427
        // Finally, combine the values we have found by using the UUID class to create a unique identifier
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    private static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private static String getDeviceBrand() {
        String model = Build.BRAND;
        return capitalize(model);
    }

    //<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />

//	public static String getMacAddress(Context context) {
//		WifiManager wimanager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//		String macAddress = wimanager.getConnectionInfo().getMacAddress();
//		if (macAddress == null) {
//			macAddress =""; // Device don't have mac address or wi-fi is disabled
//		}
//    return macAddress;
//}

    @SuppressLint("MissingPermission")
    private static String getDeviceIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
//        return "55884758458585";
    }

    @SuppressLint("InlinedApi")
    public static String getDeviceSerial() {
        try {
            String model = Build.SERIAL;
            return capitalize(model);
        } catch (Exception e) {
            return "";
        }
    }

    private static String getDeviceModel() {
        String model = Build.MODEL;
        return capitalize(model);
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static String getDevice_IMEI(Context context) {
        if (Device_IMEI.equals(""))
            Device_IMEI = DeviceInformationHelper.getDeviceIMEI(context);
        return Device_IMEI;
    }

    public static String getDevice_Model() {
        if (Device_Model.equals(""))
            Device_Model = DeviceInformationHelper.getDeviceModel();

        return Device_Model;
    }

    public static String getDevice_Name() {
        if (Device_Name.equals(""))
            Device_Name = DeviceInformationHelper.getDeviceName();
        return Device_Name;
    }

    public static String getDevice_Brand() {
        if (Device_Brand.equals(""))
            Device_Brand = DeviceInformationHelper.getDeviceBrand();
        return Device_Brand;
    }

}
