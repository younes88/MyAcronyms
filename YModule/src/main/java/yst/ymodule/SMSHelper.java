package yst.ymodule;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;


/**
 * @author MrHadi
 */
public class SMSHelper {

    Context br;

    public SMSHelper(Context con) {
        this.br = con;
    }

    public boolean SendSMS(String PhoneNumber, String msg) {
        try {
            LogCatHelper.ShowDebugLog(null, "Sending SMS to: " + PhoneNumber);
            LogCatHelper.ShowDebugLog(null, "Message Body: " + msg);
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(PhoneNumber, null, msg, null, null);
            LogCatHelper.ShowDebugLog(null, "Sending SMS Succedd");
            return true;
        } catch (Exception e) {
            LogCatHelper.ShowErrorLog(null, "Error Sending SMS: " + e.getLocalizedMessage());
            return false;
        }
    }

    public boolean SendSMS_byIntent(String PhoneNumber, String msg) {
        try {
            LogCatHelper.ShowDebugLog(null, "Sending SMS to: " + PhoneNumber);
            LogCatHelper.ShowDebugLog(null, "Message Body: " + msg);
            Uri uri = Uri.parse("smsto:" + PhoneNumber);
            Intent it = new Intent(Intent.ACTION_SENDTO, uri);
            it.putExtra("sms_body", msg);
            br.startActivity(it);
            LogCatHelper.ShowDebugLog(null, "Sending SMS Succedd");
            return true;
        } catch (Exception e) {
            LogCatHelper.ShowErrorLog(null, "Error Sending SMS: " + e.getLocalizedMessage());
            return false;
        }
    }
}
