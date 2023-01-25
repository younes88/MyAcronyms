package yst.ymodule;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import es.dmoral.toasty.Toasty;
import ir.yst.ymodule.R;
import yst.ymodule.MediaHelper.BeepTypes;


public class MessageBox {

//    private static AlertDialog prgDlg;

    public static boolean DontHideLoading = false;
    public static boolean DontHideProgressToast = false;
    static String TAG = "HADI";
    private static Dialog prgDlg;
    public static boolean PlayAudioAlerts = false;

    public static void ShowLoading(final Context context, final String Title, final String Msg, final boolean Indeterminate) {
        try {
            try {
                if (prgDlg.isShowing()) {
                    return;
                }
            } catch (Exception e) {
            }
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View v = inflater.inflate(R.layout.loading_dialog, null);
            prgDlg = new Dialog(context, R.style.CustomAlertDialog);
            prgDlg.setContentView(v);
            prgDlg.setCanceledOnTouchOutside(false);
            prgDlg.show();
        } catch (Exception e) {
            ((Activity) context).runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    try {
                        if (prgDlg.isShowing()) {
                            return;
                        }
                    } catch (Exception e) {
                    }
                    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                    View v = inflater.inflate(R.layout.loading_dialog, null);
                    prgDlg = new Dialog(context, R.style.CustomAlertDialog);
                    prgDlg.setContentView(v);
                    prgDlg.setCanceledOnTouchOutside(false);
                    try {
                        prgDlg.show();
                    } catch (Exception e) {

                    }

                }
            });
        }
    }

    public static void HideLoading(Context context) {
        try {
            if (prgDlg != null && !DontHideLoading) {
                prgDlg.dismiss();
            }
        } catch (Exception e) {
            try {
                ((Activity) context).runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (prgDlg != null && !DontHideLoading) {
                            prgDlg.dismiss();

                        }
                    }
                });


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void HideLoading(Context context, final boolean isForce) {
        try {
            if (prgDlg != null && (!DontHideLoading || isForce)) {
                DontHideLoading = false;
                prgDlg.dismiss();
            }

        } catch (Exception e) {
            try {
                ((Activity) context).runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (prgDlg != null && (!DontHideLoading || isForce)) {
                            prgDlg.dismiss();
                        }

                    }
                });

            } catch (Exception ex) {
            }
        }
    }

    public static void ShowTimedOut(final Context context, final String Caption, final String Text, final int TimeOut) {
        if (context == null)
            return;
        ((Activity) context).runOnUiThread(new Runnable() {

            @Override
            public void run() {
                final AlertDialog dialog = new AlertDialog.Builder(context).create();
                dialog.setMessage(Text);
                dialog.setTitle(Caption);
                dialog.setCanceledOnTouchOutside(false);
                final Timer t = new Timer();
                t.schedule(new TimerTask() {
                    public void run() {
                        dialog.dismiss();
                        t.cancel();
                    }
                }, TimeOut);

            }
        });

    }

    public static void ShowToastId(final Context context, final int TextId, final int duration) {
        ShowToast(context, context.getString(TextId), duration, false);
    }

    public static void ShowToast(final Context context, final int TextId, final int duration, boolean isPositive) {
        ShowToast(context, context.getString(TextId), duration, isPositive);
    }

    public static void ShowToast(final Context context, final String Text, final int duration) {
        ShowToast(context, Text, duration, false);
    }

    public static void ShowToast(final Context context, String Text, final int duration, boolean isPositive) {
        if (context == null)
            return;
        if (TextUtils.isEmpty(Text))
            Text = "";
        Typeface face = Typeface.createFromAsset(context.getAssets(), "font/IRANSansMobileFaNum.ttf");
        final SpannableStringBuilder spnStr = new SpannableStringBuilder(PersianReshape.reshape(Text));
        spnStr.setSpan(new CustomTypefaceSpan("", face), 0, spnStr.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        Toasty.Config.getInstance()
                .setToastTypeface(face) // optional
                .setTextSize(13).apply();
        try {
//            Toast.makeText(context, spnStr, duration).show();
            Toasty.normal(context, spnStr, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
        }

    }

    public static void Show(Context context, String Text, Runnable Button1Func, MessageBoxIcon icon) {
        if (context == null)
            return;
        Show(context, "", Text, MessageBoxButtons.OK, Button1Func, null, null, icon);
    }

    public static void Show(Context context, String Text, Runnable Button1Func) {
        if (context == null)
            return;
        Show(context, "", Text, MessageBoxButtons.OK, Button1Func, null, null, MessageBoxIcon.OK);
    }

    public static void Show(Context context, String Caption, String Text, Runnable Button1Func) {
        if (context == null)
            return;
        Show(context, Caption, Text, MessageBoxButtons.OK, Button1Func, null, null, MessageBoxIcon.OK);
    }

    public static void Show(Context context, String Caption, String Text, Runnable Button1Func, MessageBoxIcon icon) {
        if (context == null)
            return;
        Show(context, Caption, Text, MessageBoxButtons.OK, Button1Func, null, null, icon);
    }

    public static void Show(Context context, String Caption, String Text, MessageBoxButtons buttons, Runnable Button1Func, Runnable Button2Func, Runnable Button3Func) {
        if (context == null)
            return;
        Show(context, Caption, Text, buttons, Button1Func, Button2Func, Button3Func, MessageBoxIcon.OK);
    }

    public static void Show(final Context context, final String Caption, final String Text, final MessageBoxButtons buttons, final Runnable Button1Func, final Runnable Button2Func,
                            final Runnable Button3Func, final MessageBoxIcon icon) {
        Show(context, Caption, Text, buttons, Button1Func, Button2Func, Button3Func, "", "", "", icon);
    }

    public static void ShowCustom1Button(final Context context, final int CaptionResId, final int TextResId, final Runnable Button1Func, final String customButton1Text, final MessageBoxIcon icon) {
        Show(context, PersianReshape.reshape(context, CaptionResId), PersianReshape.reshape(context, TextResId), MessageBoxButtons.Custom1Buton, Button1Func, null, null, customButton1Text, "", "", icon);
    }

    public static void ShowCustom1Button(final Context context, final String Caption, final String Text, final Runnable Button1Func, final String customButton1Text, final MessageBoxIcon icon) {
        Show(context, Caption, Text, MessageBoxButtons.Custom1Buton, Button1Func, null, null, customButton1Text, "", "", icon);
    }

    public static void ShowCustom2Button(final Context context, final int CaptionResId, final int TextResId, final Runnable Button1Func, final Runnable Button2Func, final String customButton1Text, final String customButton2Text, final MessageBoxIcon icon) {
        Show(context, PersianReshape.reshape(context, CaptionResId), PersianReshape.reshape(context, TextResId), MessageBoxButtons.Custom2Buton, Button1Func, Button2Func, null, customButton1Text, customButton2Text, "", icon);
    }

    public static void ShowCustom2Button(final Context context, final String Caption, final String Text, final Runnable Button1Func, final Runnable Button2Func, final String customButton1Text, final String customButton2Text, final MessageBoxIcon icon) {
        Show(context, Caption, Text, MessageBoxButtons.Custom2Buton, Button1Func, Button2Func, null, customButton1Text, customButton2Text, "", icon);
    }

    public static void ShowCustom3Button(final Context context, final int CaptionResId, final int TextResId, final Runnable Button1Func, final Runnable Button2Func, final Runnable Button3Func, final String customButton1Text, final String customButton2Text, final String customButton3Text, final MessageBoxIcon icon) {
        Show(context, PersianReshape.reshape(context, CaptionResId), PersianReshape.reshape(context, TextResId), MessageBoxButtons.Custom3Buton, Button1Func, Button2Func, Button3Func, customButton1Text, customButton2Text, customButton3Text, icon);
    }

    public static void ShowCustom3Button(final Context context, final String Caption, final String Text, final Runnable Button1Func, final Runnable Button2Func, final Runnable Button3Func, final String customButton1Text, final String customButton2Text, final String customButton3Text, final MessageBoxIcon icon) {
        Show(context, Caption, Text, MessageBoxButtons.Custom3Buton, Button1Func, Button2Func, Button3Func, customButton1Text, customButton2Text, customButton3Text, icon);
    }

    public static void Show(final Context context, final String Caption, final String Text, final MessageBoxButtons buttons, final Runnable Button1Func, final Runnable Button2Func,
                            final Runnable Button3Func, final String customButton1Text, final String customButton2Text,
                            final String customButton3Text, final MessageBoxIcon icon) {

        try {
            if (context == null)
                return;
            HideLoading(context, true);

            Activity act = (Activity) context;
            act.runOnUiThread(new Runnable() {
                @SuppressWarnings("deprecation")
                public void run() {
                    final AlertDialog dialog = new AlertDialog.Builder(context).create();

                    final SpannableStringBuilder spnText = new SpannableStringBuilder(PersianReshape.reshape(Text));
                    spnText.setSpan(new CustomTypefaceSpan("", UIHelper.getTypeFace(context)), 0, spnText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                    final SpannableStringBuilder spnCaption = new SpannableStringBuilder(PersianReshape.reshape(Caption));
                    spnCaption.setSpan(new CustomTypefaceSpan("", UIHelper.getTypeFace(context)), 0, spnCaption.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                    final SpannableStringBuilder spnOk = new SpannableStringBuilder(PersianReshape.reshape(context, R.string.ok));
                    spnOk.setSpan(new CustomTypefaceSpan("", UIHelper.getTypeFace(context)), 0, spnOk.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                    final SpannableStringBuilder spnCancel = new SpannableStringBuilder(PersianReshape.reshape(context, R.string.cancel));
                    spnCancel.setSpan(new CustomTypefaceSpan("", UIHelper.getTypeFace(context)), 0, spnCancel.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                    final SpannableStringBuilder spnEdit = new SpannableStringBuilder(PersianReshape.reshape(context, R.string.edit));
                    spnEdit.setSpan(new CustomTypefaceSpan("", UIHelper.getTypeFace(context)), 0, spnEdit.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                    final SpannableStringBuilder spnYes = new SpannableStringBuilder(PersianReshape.reshape(context, R.string.yes));
                    spnYes.setSpan(new CustomTypefaceSpan("", UIHelper.getTypeFace(context)), 0, spnYes.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                    final SpannableStringBuilder spnNo = new SpannableStringBuilder(PersianReshape.reshape(context, R.string.no));
                    spnNo.setSpan(new CustomTypefaceSpan("", UIHelper.getTypeFace(context)), 0, spnNo.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                    final SpannableStringBuilder spnRetry = new SpannableStringBuilder(PersianReshape.reshape(context, R.string.retry));
                    spnRetry.setSpan(new CustomTypefaceSpan("", UIHelper.getTypeFace(context)), 0, spnRetry.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                    final SpannableStringBuilder spnIgnore = new SpannableStringBuilder(PersianReshape.reshape(context, R.string.ignore));
                    spnIgnore.setSpan(new CustomTypefaceSpan("", UIHelper.getTypeFace(context)), 0, spnIgnore.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);


                    dialog.setMessage(spnText);
                    dialog.setTitle(spnCaption);
//                    TextView textView = (TextView) dialog.findViewById(android.R.id.message);
//                    textView.setTextSize(15);
                    dialog.setCanceledOnTouchOutside(false);
                    BeepTypes beepType = BeepTypes.Information;
                    Log.d(TAG, "1");
                    switch (buttons) {
                        case OK:
                            dialog.setButton(PersianReshape.reshape(context, R.string.ok), new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (Button1Func != null)
                                        Button1Func.run();
                                    else
                                        dialog.dismiss();
                                }
                            });
                            break;
                        case OKCancel:
                            dialog.setButton(spnOk, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (Button1Func != null)
                                        Button1Func.run();
                                    else
                                        dialog.dismiss();
                                }
                            });
                            dialog.setButton2(spnCancel, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (Button2Func != null)
                                        Button2Func.run();
                                    else
                                        dialog.dismiss();
                                }
                            });
                            break;
                        case OkEdit:
                            dialog.setButton(spnOk, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (Button1Func != null)
                                        Button1Func.run();
                                    else
                                        dialog.dismiss();
                                }
                            });
                            dialog.setButton2(spnEdit, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (Button2Func != null)
                                        Button2Func.run();
                                    else
                                        dialog.dismiss();
                                }
                            });
                            break;
                        case YesNo:
                            dialog.setButton(spnYes, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (Button1Func != null)
                                        Button1Func.run();
                                    else
                                        dialog.dismiss();
                                }
                            });
                            dialog.setButton2(spnNo, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (Button2Func != null)
                                        Button2Func.run();
                                    else
                                        dialog.dismiss();
                                }
                            });
                            break;
                        case YesNoCancel:
                            dialog.setButton(spnYes, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (Button1Func != null)
                                        Button1Func.run();
                                    else
                                        dialog.dismiss();
                                }
                            });
                            dialog.setButton2(spnNo, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (Button2Func != null)
                                        Button2Func.run();
                                    else
                                        dialog.dismiss();
                                }
                            });
                            dialog.setButton3(spnCancel, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (Button3Func != null)
                                        Button3Func.run();
                                    else
                                        dialog.dismiss();
                                }
                            });
                            break;
                        case RetryCancel:
                            dialog.setButton(spnRetry, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (Button1Func != null)
                                        Button1Func.run();
                                    else
                                        dialog.dismiss();
                                }
                            });
                            dialog.setButton2(spnCancel, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (Button2Func != null)
                                        Button2Func.run();
                                    else
                                        dialog.dismiss();
                                }
                            });
                            break;
                        case AbortRetryIgnore:
                            dialog.setButton(spnCancel, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (Button1Func != null)
                                        Button1Func.run();
                                    else
                                        dialog.dismiss();
                                }
                            });
                            dialog.setButton2(spnRetry, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (Button2Func != null)
                                        Button2Func.run();
                                    else
                                        dialog.dismiss();
                                }
                            });
                            dialog.setButton3(spnIgnore, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (Button3Func != null)
                                        Button3Func.run();
                                    else
                                        dialog.dismiss();
                                }
                            });
                            break;

                        case Custom3Buton:
                            dialog.setButton(getSpannableFromText(context, customButton1Text), new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (Button1Func != null)
                                        Button1Func.run();
                                    else
                                        dialog.dismiss();
                                }
                            });
                            dialog.setButton2(getSpannableFromText(context, customButton2Text), new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (Button2Func != null)
                                        Button2Func.run();
                                    else
                                        dialog.dismiss();
                                }
                            });
                            dialog.setButton3(getSpannableFromText(context, customButton3Text), new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (Button3Func != null)
                                        Button3Func.run();
                                    else
                                        dialog.dismiss();
                                }
                            });
                            break;
                        case Custom2Buton:
                            dialog.setButton(getSpannableFromText(context, customButton1Text), new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (Button1Func != null)
                                        Button1Func.run();
                                    else
                                        dialog.dismiss();
                                }
                            });
                            dialog.setButton2(getSpannableFromText(context, customButton2Text), new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (Button2Func != null)
                                        Button2Func.run();
                                    else
                                        dialog.dismiss();
                                }
                            });
                            break;
                        case Custom1Buton:
                            dialog.setButton(getSpannableFromText(context, customButton1Text), new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (Button1Func != null)
                                        Button1Func.run();
                                    else
                                        dialog.dismiss();
                                }
                            });
                            break;


                        default:
                            break;
                    }

                    switch (icon) {
                        case Error:
                            dialog.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
                            beepType = BeepTypes.Error;
                            break;
                        case OK:
                            dialog.setIcon(android.R.drawable.ic_menu_info_details);
                            beepType = BeepTypes.Information;
                            break;
                        case Question:
                            dialog.setIcon(android.R.drawable.ic_menu_help);
                            beepType = BeepTypes.Question;
                            break;
                        case Stop:
                            dialog.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
                            beepType = BeepTypes.Error;
                            break;
                        case Warning:
                            dialog.setIcon(android.R.drawable.ic_menu_report_image);
                            beepType = BeepTypes.Warning;
                            break;
                        default:
                            dialog.setIcon(android.R.drawable.ic_menu_info_details);
                            break;
                    }

                    dialog.show();
                    if (MessageBox.PlayAudioAlerts)
                        MediaHelper.PlayBeep(context, beepType);
                    // return dialog;
                }
            });
        } catch (Exception ignored) {
        }

    }

    private static SpannableStringBuilder getSpannableFromText(Context context, String text) {
        final SpannableStringBuilder spnText1 = new SpannableStringBuilder(PersianReshape.reshape(text));
        spnText1.setSpan(new CustomTypefaceSpan("", UIHelper.getTypeFace(context)), 0, spnText1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return spnText1;
    }

    public static void Show(Context context, int Text_id, Runnable Button1Func, MessageBoxIcon icon) {
        if (context == null)
            return;
        Show(context, "", context.getString(Text_id), MessageBoxButtons.OK, Button1Func, null, null, icon);
    }

    public static void Show(Context context, int Text_id, Runnable Button1Func) {
        if (context == null)
            return;
        Show(context, "", context.getString(Text_id), MessageBoxButtons.OK, Button1Func, null, null, MessageBoxIcon.OK);
    }

    public static void Show(Context context, int Caption_id, int Text_id, Runnable Button1Func) {
        if (context == null)
            return;
        Show(context, context.getString(Caption_id), context.getString(Text_id), MessageBoxButtons.OK, Button1Func, null, null, MessageBoxIcon.OK);
    }

//    public static void ShowLoading(final Context context, final String Title, final String Msg, final boolean Indeterminate) {
//        ((Activity) context).runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//                try {
//                    if (prgDlg.isShowing()) {
//                        return;
//                    }
//                } catch (Exception e) {
//                }
//
//                String msgTitle = Title;
//                String msgText = Msg;
//                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//                View v = inflater.inflate(R.layout.loading_dialog, null);
//                TextView lblLoadingTitle = (TextView) v.findViewById(R.id.lblLoadingTitle);
//                TextView lblLoadingText = (TextView) v.findViewById(R.id.lblLoadingText);
//                lblLoadingTitle.setTypeface(UIHelper.getTypeFace(context));
//                lblLoadingText.setTypeface(UIHelper.getTypeFace(context));
//
//                if (msgTitle == null)
//                    msgTitle = context.getString(R.string.PleaseWait);
//                if (msgTitle.equals(""))
//                    msgTitle = context.getString(R.string.PleaseWait);
//
//                if (msgText == null)
//                    lblLoadingText.setVisibility(View.INVISIBLE);
//
//                if (msgText == null)
//                    msgText = context.getString(R.string.ConnectingToServer);
//                if (msgText.equals(""))
//                    msgText = context.getString(R.string.ConnectingToServer);
//
//                lblLoadingTitle.setText(PersianReshape.reshape(msgTitle));
//                lblLoadingText.setText(PersianReshape.reshape(msgText));
//
//                //ImageView imgLoading = (ImageView) v.findViewById(R.id.imgLoading);
//                GifAnimationDrawable drawable;
//                try {
//                    drawable = new GifAnimationDrawable(context.getResources().openRawResource(R.raw.preloader), true);
//
//                    //imgLoading.setImageDrawable(drawable);
//
//                    drawable.setVisible(true, true);
//                } catch (Exception e) {
//                }
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setView(v);
//                prgDlg = builder.show();
//                prgDlg.setCanceledOnTouchOutside(false);
//
//            }
//        });
//    }

//    public static void HideLoading(Context context) {
//        try {
//            ((Activity) context).runOnUiThread(new Runnable() {
//
//                @Override
//                public void run() {
//                    if (prgDlg != null && !ValueKeeper.DontHideLoading) {
//                        prgDlg.dismiss();
//
//                    }
//
//                    if (ValueKeeper.currentPullToRefresh != null)
//                        ValueKeeper.currentPullToRefresh.setRefreshComplete();
//
//                }
//            });
//
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }

//    public static void HideLoading(Context context, final boolean isForce) {
//        try {
//            ((Activity) context).runOnUiThread(new Runnable() {
//
//                @Override
//                public void run() {
//                    if (prgDlg != null && (!ValueKeeper.DontHideLoading || isForce)) {
//                        prgDlg.dismiss();
//                    }
//
//                    if (ValueKeeper.currentPullToRefresh != null)
//                        ValueKeeper.currentPullToRefresh.setRefreshComplete();
//
//                }
//            });
//
//        } catch (Exception ex) {
//        }
//    }

    public static void Show(Context context, int Caption_id, int Text_id, Runnable Button1Func, MessageBoxIcon icon) {
        if (context == null)
            return;
        Show(context, context.getString(Caption_id), context.getString(Text_id), MessageBoxButtons.OK, Button1Func, null, null, icon);
    }

    public static void Show(Context context, int Caption_id, int Text_id, MessageBoxButtons buttons, Runnable Button1Func, Runnable Button2Func, Runnable Button3Func) {
        if (context == null)
            return;
        Show(context, context.getString(Caption_id), context.getString(Text_id), buttons, Button1Func, Button2Func, Button3Func, MessageBoxIcon.OK);
    }

    public static void Show(final Context context, int Caption_id, int Text_id, final MessageBoxButtons buttons, final Runnable Button1Func, final Runnable Button2Func, final Runnable Button3Func,
                            final MessageBoxIcon icon) {

        if (context == null)
            return;
        Show(context, context.getString(Caption_id), context.getString(Text_id), buttons, Button1Func, Button2Func, Button3Func, MessageBoxIcon.OK);
    }

    public static void Show(Context context, int Caption_id, String text, Runnable Button1Func) {
        if (context == null)
            return;
        Show(context, context.getString(Caption_id), text, MessageBoxButtons.OK, Button1Func, null, null, MessageBoxIcon.OK);
    }

    public static void Show(Context context, int Caption_id, String text, Runnable Button1Func, MessageBoxIcon icon) {
        if (context == null)
            return;
        Show(context, context.getString(Caption_id), text, MessageBoxButtons.OK, Button1Func, null, null, icon);
    }

    public static void Show(Context context, int Caption_id, String text, MessageBoxButtons buttons, Runnable Button1Func, Runnable Button2Func, Runnable Button3Func) {
        if (context == null)
            return;
        Show(context, context.getString(Caption_id), text, buttons, Button1Func, Button2Func, Button3Func, MessageBoxIcon.OK);
    }

    public static void Show(final Context context, int Caption_id, String text, final MessageBoxButtons buttons, final Runnable Button1Func, final Runnable Button2Func, final Runnable Button3Func,
                            final MessageBoxIcon icon) {

        if (context == null)
            return;
        Show(context, context.getString(Caption_id), text, buttons, Button1Func, Button2Func, Button3Func, MessageBoxIcon.OK);
    }

    public static void Show(Context context, String Caption, int Text_id, Runnable Button1Func) {
        if (context == null)
            return;
        Show(context, Caption, context.getString(Text_id), MessageBoxButtons.OK, Button1Func, null, null, MessageBoxIcon.OK);
    }

    public static void Show(Context context, String Caption, int Text_id, Runnable Button1Func, MessageBoxIcon icon) {
        if (context == null)
            return;
        Show(context, Caption, context.getString(Text_id), MessageBoxButtons.OK, Button1Func, null, null, icon);
    }

    public static void Show(Context context, String Caption, int Text_id, MessageBoxButtons buttons, Runnable Button1Func, Runnable Button2Func, Runnable Button3Func) {
        if (context == null)
            return;
        Show(context, Caption, context.getString(Text_id), buttons, Button1Func, Button2Func, Button3Func, MessageBoxIcon.OK);
    }

    public static void Show(final Context context, String Caption, int Text_id, final MessageBoxButtons buttons, final Runnable Button1Func, final Runnable Button2Func, final Runnable Button3Func,
                            final MessageBoxIcon icon) {

        if (context == null)
            return;
        Show(context, Caption, context.getString(Text_id), buttons, Button1Func, Button2Func, Button3Func, MessageBoxIcon.OK);
    }

    public enum MessageBoxButtons {
        AbortRetryIgnore, OK, OKCancel, RetryCancel, YesNo, YesNoCancel, OkEdit, Custom1Buton, Custom2Buton, Custom3Buton
    }

    public enum MessageBoxIcon {
        Error, OK, Question, Warning, Stop
    }

    public enum MessageBoxDefaultButton {
        Button1, Button2, Button3
    }

}
