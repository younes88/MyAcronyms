package yst.ymodule;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by Kasper on 1/27/2015.
 * this class has methods to do somethings with intents externally
 */
public class IntentHelper {

    public static void DialNumber(String number, Context context) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + Uri.encode(number)));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(callIntent);
    }

    public static void SendEmailTo(String emailAddress, String subject, String title, Context context) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", emailAddress, null));
        if (TextUtils.isEmpty(subject))
            subject = "";
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (TextUtils.isEmpty(title))
            title = "Send email...";
        context.startActivity(Intent.createChooser(emailIntent, title));
    }

    public static void OpenWebSite(String url, Context context) {
        if (!url.toLowerCase().startsWith("http://"))
            url = "http://" + url;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }

    public static void ShowShareTextDialog(Context context, String dialogTitle, String textToShare, String subject) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, PersianReshape.reshape(textToShare));
        context.startActivity(Intent.createChooser(sharingIntent, PersianReshape.reshape(dialogTitle)));
    }
}
