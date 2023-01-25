package yst.ymodule;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import ir.yst.ymodule.R;

public class NotificationHelper {

    public static ArrayList<Integer> lstNofifyIDs = new ArrayList<Integer>();

    public static int GenerateNTFID() {
        for (int i = 1; i < 9999; i++)
            if (!lstNofifyIDs.contains(i)) {
                return i;
            }
        return 1;
    }

    public static Bitmap BuildBitmapFromString(String text, Typeface face, String textColor, int textSize, boolean isBold, Layout.Alignment alignment, int bitmapWidth, int bitmapHeight) {
        Bitmap myBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_4444);
        Canvas myCanvas = new Canvas(myBitmap);
        TextPaint paint = new TextPaint();
        paint.setAntiAlias(true);
        paint.setSubpixelText(true);
        if (!isBold)
            paint.setTypeface(face);
        else {
            Typeface faceBold = Typeface.create(face, Typeface.BOLD);
            paint.setTypeface(faceBold);
        }
        paint.setStyle(Paint.Style.FILL);
        if (!textColor.startsWith("#"))
            textColor = "#" + textColor;
        paint.setColor(Color.parseColor(textColor));
        paint.setTextSize(textSize);
//        paint.setTextAlign(textAlign);
//        if (textAlign == Paint.Align.CENTER)
//            myCanvas.drawText(text, bitmapWidth / 2, bitmapHeight / 2, paint);
//        else if (textAlign == Paint.Align.RIGHT)
//            myCanvas.drawText(text, bitmapWidth, bitmapHeight / 2, paint);

        drawMultiLineEllipsizedText(myCanvas, paint, 0, 0, bitmapWidth, bitmapHeight, text, alignment);
        return myBitmap;

    }

    //-------not good way
    public static Bitmap BuildBitmapFromString(Context context, String text, Typeface face, String textColor, int textSize, boolean isBold, Layout.Alignment alignment, int bitmapWidth, int bitmapHeight) {
        Bitmap myBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_4444);
        Canvas myCanvas = new Canvas(myBitmap);
        TextPaint paint = new TextPaint();
        paint.setAntiAlias(true);
        paint.setSubpixelText(true);
        if (!isBold)
            paint.setTypeface(face);
        else {
            Typeface faceBold = Typeface.create(face, Typeface.BOLD);
            paint.setTypeface(faceBold);
        }
        paint.setStyle(Paint.Style.FILL);
        if (!textColor.startsWith("#"))
            textColor = "#" + textColor;
        paint.setColor(Color.parseColor(textColor));
        paint.setTextSize(textSize);

        TextView tv = new TextView(context);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.setMargins(0, 0, 0, 0); // llp.setMargins(left, top, right, bottom);
        tv.setLayoutParams(llp);
        tv.setTextSize(textSize);

        tv.setTextColor(Color.parseColor(textColor));
        tv.setText(text);
        tv.setTypeface(UIHelper.getTypeFace(context));
        TextJustifyUtils.Simplejustify(tv);
        tv.setDrawingCacheEnabled(true);
        tv.measure(View.MeasureSpec.makeMeasureSpec(myCanvas.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(myCanvas.getHeight(), View.MeasureSpec.EXACTLY));
        tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());
        myCanvas.drawBitmap(tv.getDrawingCache(), 0, 0, paint);
        tv.setDrawingCacheEnabled(false);

        return myBitmap;
    }

    public static void drawMultiLineEllipsizedText(final Canvas _canvas, final TextPaint _textPaint, final float _left, final float _top, final float _right, final float _bottom, final String _text, Layout.Alignment _alignment) {
        final float height = _bottom - _top;

        final StaticLayout measuringTextLayout = new StaticLayout(_text, _textPaint, (int) Math.abs(_right - _left), _alignment, 1.0f, 0.0f, false);

        int line = 0;
        final int totalLineCount = measuringTextLayout.getLineCount();
        for (line = 0; line < totalLineCount; line++) {
            final int lineBottom = measuringTextLayout.getLineBottom(line);
            if (lineBottom > height) {
                break;
            }
        }
        line--;

        if (line < 0) {
            return;
        }

        int lineEnd;
        try {
            lineEnd = measuringTextLayout.getLineEnd(line);
        } catch (Throwable t) {
            lineEnd = _text.length();
        }
        String truncatedText = _text.substring(0, Math.max(0, lineEnd));

        if (truncatedText.length() < 3) {
            return;
        }

        if (truncatedText.length() < _text.length()) {
            truncatedText = truncatedText.substring(0, Math.max(0, truncatedText.length() - 3));
            truncatedText += "...";
        }
        final StaticLayout drawingTextLayout = new StaticLayout(truncatedText, _textPaint, (int) Math.abs(_right - _left), _alignment, 1.25f, 0.0f, false);

        _canvas.save();
        _canvas.translate(_left, _top);
        drawingTextLayout.draw(_canvas);
        _canvas.restore();
    }

    public static void ShowNotification(Context context, String Title, String contentTitle, String contentText, int IconID, int millisToWaitBeforeShow, int NOTIFY_ID, Intent OnClickIntent, boolean AutoCancel, boolean ClearAble, boolean Vibrate, String SoundPath, String IconPath, boolean PlayNTFSound) {

        if (NOTIFY_ID < 0)
            NOTIFY_ID = GenerateNTFID();

        NotificationManager mNManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent intent = PendingIntent.getActivity(context, NOTIFY_ID, OnClickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int imgvWidth = context.getResources().getDimensionPixelSize(R.dimen.ntf_image_width);
        int contentWidth = metrics.widthPixels - imgvWidth;//- (int) UnitHelper.ConvertDpToPixel(14, context);
        int titleHeight = context.getResources().getDimensionPixelSize(R.dimen.ntf_title_height);
        int subTitleHeight = context.getResources().getDimensionPixelSize(R.dimen.ntf_sub_title_height);


        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.ntf_layout);
        contentView.setImageViewBitmap(R.id.imgvNTFImage, BitmapFactory.decodeFile(IconPath));
        Bitmap titleBitmap = BuildBitmapFromString(contentTitle, UIHelper.getTypeFace(context), "ffffff", (int) UnitHelper.ConvertSpToPixel(17, context), true, Layout.Alignment.ALIGN_NORMAL, contentWidth, titleHeight);
        Bitmap subTitleBitmap = BuildBitmapFromString(contentText, UIHelper.getTypeFace(context), "cccccc", (int) UnitHelper.ConvertSpToPixel(15, context), false, Layout.Alignment.ALIGN_NORMAL, contentWidth, subTitleHeight);
        contentView.setImageViewBitmap(R.id.imgvNTFTitle, titleBitmap);
        contentView.setImageViewBitmap(R.id.imgvNTFSubTitle, subTitleBitmap);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setAutoCancel(AutoCancel);
        builder.setTicker(Title);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
//        builder.setStyle(new NotificationCompat.BigPictureStyle());
//        builder.setContentTitle(spnContentTitle);
        builder.setContent(contentView);
//        builder.setContentText(spnText);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (!TextUtils.isEmpty(IconPath)) {
                builder.setLargeIcon(BitmapFactory.decodeFile(IconPath));
            }
        }
        builder.setSmallIcon(IconID);
        builder.setContentIntent(intent);
//        builder.setGroup(context.getPackageName());
//        builder.setShowWhen(true);
//        builder.setOngoing(true);
//        builder.setSubText(contentText);   //API level 16

        Notification msg = builder.build();

        if (Build.VERSION.SDK_INT >= 16) {
            int imgvWidth_large = context.getResources().getDimensionPixelSize(R.dimen.ntf_image_width_large);
            int contentWidth_large = metrics.widthPixels - imgvWidth_large;// - (int) UnitHelper.ConvertDpToPixel(14, context);
            int titleHeight_large = context.getResources().getDimensionPixelSize(R.dimen.ntf_title_height_large);
            int subTitleHeight_large = context.getResources().getDimensionPixelSize(R.dimen.ntf_sub_title_height_large);
            // Inflate and set the layout for the expanded notification view
            RemoteViews contentView_expanded = new RemoteViews(context.getPackageName(), R.layout.ntf_layout_expanded);
            contentView_expanded.setImageViewBitmap(R.id.imgvNTFImage, BitmapFactory.decodeFile(IconPath));
            Bitmap titleBitmap_exp = BuildBitmapFromString(contentTitle, UIHelper.getTypeFace(context), "ffffff", (int) UnitHelper.ConvertSpToPixel(17, context), true, Layout.Alignment.ALIGN_CENTER, metrics.widthPixels, titleHeight_large);
            Bitmap subTitleBitmap_exp = BuildBitmapFromString(contentText, UIHelper.getTypeFace(context), "cccccc", (int) UnitHelper.ConvertSpToPixel(15, context), false, Layout.Alignment.ALIGN_NORMAL, contentWidth_large, subTitleHeight_large);
            contentView_expanded.setImageViewBitmap(R.id.imgvNTFTitle, titleBitmap_exp);
            contentView_expanded.setImageViewBitmap(R.id.imgvNTFSubTitle, subTitleBitmap_exp);
            msg.bigContentView = contentView_expanded;
        }

        if (Vibrate)
            msg.defaults |= Notification.DEFAULT_VIBRATE;

        if (PlayNTFSound) {
            if (!TextUtils.isEmpty(SoundPath))
                msg.sound = Uri.fromFile(new File(SoundPath));
            else
                msg.defaults |= Notification.DEFAULT_SOUND;
        }
        if (!ClearAble)
            msg.flags |= Notification.FLAG_NO_CLEAR;

        mNManager.notify(NOTIFY_ID, msg);
        lstNofifyIDs.add(NOTIFY_ID);
    }

    /////----------typeface not working-----------------
//    public static void ShowNotification(Context context, String Title, String contentTitle, String contentText, int IconID, int millisToWaitBeforeShow, int NOTIFY_ID, Intent OnClickIntent, boolean AutoCancel, boolean ClearAble, boolean Vibrate, String SoundPath, String IconPath, boolean PlayNTFSound) {
//
//        if (NOTIFY_ID < 0)
//            NOTIFY_ID = GenerateNTFID();
//
//        NotificationManager mNManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        PendingIntent intent = PendingIntent.getActivity(context, NOTIFY_ID, OnClickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        final SpannableStringBuilder spnTitle = new SpannableStringBuilder(PersianReshape.reshape(Title));
//        spnTitle.setSpan(new CustomTypefaceSpan("", UIHelper.getTypeFace(context)), 0, spnTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//
//        final SpannableStringBuilder spnContentTitle = new SpannableStringBuilder(PersianReshape.reshape(contentTitle));
//        spnContentTitle.setSpan(new CustomTypefaceSpan("", UIHelper.getTypeFace(context)), 0, spnContentTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//
//        final SpannableStringBuilder spnText = new SpannableStringBuilder(PersianReshape.reshape(contentText));
//        spnText.setSpan(new CustomTypefaceSpan("", UIHelper.getTypeFace(context)), 0, spnText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//
//
//        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.ntf_layout);
//        contentView.setImageViewBitmap(R.id.imgvNTFImage, BitmapFactory.decodeFile(IconPath));
//        contentView.setTextViewText(R.id.lblNTFTitle, spnContentTitle);
//        contentView.setTextViewText(R.id.lblNTFSubTitle, spnText);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//
//        builder.setAutoCancel(AutoCancel);
//        builder.setTicker(spnTitle);
////        builder.setStyle(new NotificationCompat.BigTextStyle());
////        builder.setContentTitle(spnContentTitle);
//        builder.setContent(contentView);
////        builder.setContentText(spnText);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            if (!TextUtils.isEmpty(IconPath)) {
//                builder.setLargeIcon(BitmapFactory.decodeFile(IconPath));
//            }
//        }
//        builder.setSmallIcon(IconID);
//        builder.setContentIntent(intent);
////        builder.setGroup(context.getPackageName());
//        builder.setShowWhen(true);
////        builder.setOngoing(true);
////        builder.setSubText(contentText);   //API level 16
//        Notification msg = builder.build();
//        if (Vibrate)
//            msg.defaults |= Notification.DEFAULT_VIBRATE;
//
//        if (PlayNTFSound) {
//            if (!TextUtils.isEmpty(SoundPath))
//                msg.sound = Uri.fromFile(new File(SoundPath));
//            else
//                msg.defaults |= Notification.DEFAULT_SOUND;
//        }
//        if (!ClearAble)
//            msg.flags |= Notification.FLAG_NO_CLEAR;
//
//        mNManager.notify(NOTIFY_ID, msg);
//        lstNofifyIDs.add(NOTIFY_ID);
//    }


    public static void CancelNotificationByID(Context context, int Notity_ID) {
        NotificationManager mNManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNManager.cancel(Notity_ID);
    }

    public static void CancelNotificationByindex(Context context, int Notity_Index) {
        NotificationManager mNManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNManager.cancel(lstNofifyIDs.get(Notity_Index));
    }

    public static void CancelAllNotifications(Context context) {
        NotificationManager mNManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNManager.cancelAll();
    }
}
