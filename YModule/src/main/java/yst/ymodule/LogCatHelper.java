package yst.ymodule;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;

/**
 * Created by Kasper on 4/3/2015.
 */
public class LogCatHelper {

    public static boolean isDebug = false;
    static String tag = "Narin";
    static LogCatHelper logCatHelper;

    public static LogCatHelper DefaultInstance(String tag) {
        if (TextUtils.isEmpty(tag))
            tag = LogCatHelper.tag;
        if (logCatHelper == null) {
            logCatHelper = new LogCatHelper();
            Logger.init(tag).methodOffset(2);
        }
        return logCatHelper;
    }

    public static void ShowDebugLog(String Tag, String Message) {
        DefaultInstance(Tag).ShowDebugLog(Message);
    }

    public static void ShowInfoLog(String Tag, String Message) {
        DefaultInstance(Tag).ShowInfoLog(Message);
    }

    public static void ShowWarningLog(String Tag, String Message) {
        DefaultInstance(Tag).ShowWarningLog(Message);
    }

    public static void ShowErrorLog(String Tag, String ErrText) {
        DefaultInstance(Tag).ShowErrorLog(ErrText);
    }

    public static void ShowJsonLog(String Tag, String jsonText) {
        if (!isDebug)
            return;
        try {
            DefaultInstance(Tag);
            Logger.json(jsonText);
        } catch (Exception ignored) {
        }
    }

    public static void ShowXMLLog(String Tag, String xmlText) {
        if (!isDebug)
            return;
        DefaultInstance(Tag);
        try {
            Logger.xml(xmlText);
        } catch (Exception ignored) {
        }
    }

    public void ShowDebugLog(String Message) {
        if (!isDebug)
            return;
        Logger.d(Message);
    }

    public void ShowInfoLog(String Message) {
        if (!isDebug)
            return;

        Logger.i(Message);
    }

    public void ShowWarningLog(String Message) {
        if (!isDebug)
            return;
        Logger.w(Message);
    }

    public void ShowErrorLog(String ErrText) {
//        if (!isDebug)
//            return;
        Logger.e(ErrText);
    }

}
