package yst.ymodule;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import ir.yst.ymodule.R;

public class DownloadHelper {
    ProgressDialog pDialog;
    String DestDir;
    String DestFileName;
    long fileSize = 0;
    boolean ShowProgress = true;
    String ProgressTitle;
    String ProgressColor1 = "#ffc600";
    String ProgressColor2 = "#ffc600";
    String ProgressColor3 = "#ffc600";
    Runnable onCompleteRunnable = null;
    Activity activity;
    private String SourceURL;
    private Thread ThStartDownload;
    private Context context;
    private boolean ThCanceled = false;

    public DownloadHelper(String sourceURL, Context Context, String destDir, String destFileName, boolean showProgress, Runnable onCompleteRunnable) {
        super();
        SourceURL = sourceURL;
        DestDir = destDir;
        DestFileName = destFileName;
        context = Context;
        ShowProgress = showProgress;
        this.onCompleteRunnable = onCompleteRunnable;
    }

    public DownloadHelper(String sourceURL, Context Context, String destDir, String destFileName, boolean showProgress, String progressTitle, Runnable onCompleteRunnable) {
        super();
        SourceURL = sourceURL;
        DestDir = destDir;
        DestFileName = destFileName;
        context = Context;
        ShowProgress = showProgress;
        ProgressTitle = progressTitle;
        this.onCompleteRunnable = onCompleteRunnable;
    }

    public DownloadHelper(String sourceURL, Context Context, String destDir, String destFileName, boolean showProgress, String progressTitle, String progressColor1, String progressColor2, String progressColor3, Runnable onCompleteRunnable) {
        super();
        SourceURL = sourceURL;
        DestDir = destDir;
        DestFileName = destFileName;
        context = Context;
        ShowProgress = showProgress;
        ProgressTitle = progressTitle;
        this.onCompleteRunnable = onCompleteRunnable;
        this.ProgressColor1 = progressColor1;
        this.ProgressColor3 = progressColor2;
        this.ProgressColor3 = progressColor3;
    }

    public static Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            LogCatHelper.ShowErrorLog(null, "Error GetBitmapFromURL: " + e.getLocalizedMessage());
            return null;
        }
    }

    public void StartDownload() {

        try {
            File outputFile = new File(DestDir + "/" + DestFileName);
            if (outputFile.exists()) {
                if (ShowProgress)
                    Toast.makeText(context, PersianReshape.reshape(context, R.string.file_already_exists), Toast.LENGTH_SHORT).show();
                if (onCompleteRunnable != null)
                    onCompleteRunnable.run();
                return;
            }
        } catch (Exception ignored) {
        }

        ThCanceled = false;
        if (ShowProgress) {
            pDialog = new ProgressDialog(context);
            if (TextUtils.isEmpty(ProgressTitle))
                pDialog.setMessage(PersianReshape.reshape(context.getResources().getString(R.string.downloading_please_wait)) + "...");
            else
                pDialog.setMessage(PersianReshape.reshape(ProgressTitle));
            pDialog.setIndeterminate(false);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.setOnCancelListener(new OnCancelListener() {

                @Override
                public void onCancel(DialogInterface arg0) {
                    StopThread();

                }
            });
            pDialog.setMax(100);
            pDialog.setProgressStyle(1);
            GradientDrawable progressGradientDrawable = new GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT, new int[]{
                    Color.parseColor(ProgressColor1), Color.parseColor(ProgressColor2), Color.parseColor(ProgressColor3)});
            ClipDrawable progressClipDrawable = new ClipDrawable(
                    progressGradientDrawable, Gravity.LEFT, ClipDrawable.HORIZONTAL);
            Drawable[] progressDrawables = {
                    new ColorDrawable(0xffffffff),
                    progressClipDrawable, progressClipDrawable};
//        LayerDrawable progressLayerDrawable = new LayerDrawable(progressDrawables);
            pDialog.setProgressDrawable(progressClipDrawable);
            pDialog.show();
        }

        ThStartDownload = new Thread(new Runnable() {

            @Override
            public void run() {
                int count;

                try {
                    fileSize = GetFileSizeInBytes(SourceURL);
                    URL url = new URL(SourceURL);
                    URLConnection conection = url.openConnection();
                    conection.connect();
                    // int lenghtOfFile = conection.getContentLength();
                    int lenghtOfFile = (int) fileSize;
                    int Max = lenghtOfFile / (1024);
                    if (pDialog != null) {
                        pDialog.setMax(Max);
                        if (lenghtOfFile < 1)
                            pDialog.setIndeterminate(true);
                    }
                    InputStream input = new BufferedInputStream(url.openStream(), 8192);
                    File dirDest = new File(DestDir);
                    dirDest.mkdirs();
                    OutputStream output = new FileOutputStream(DestDir + "/" + DestFileName);
                    byte[] data = new byte[1024];
                    long total = 0;
                    while ((count = input.read(data)) != -1) {
                        if (ThCanceled)
                            return;
                        total += count;
                        final int progress = (int) total / 1024;
                        if (ShowProgress) {
//                            if (context.getClass() == Activity.class)
                            try {
                                ((Activity) context).runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        pDialog.setProgress(progress);
                                    }
                                });
                            } catch (Exception e) {
                                try {
                                    pDialog.setProgress(progress);
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                        output.write(data, 0, count);
                    }
                    output.flush();
                    output.close();
                    input.close();
                    if (ShowProgress) {
//                        if (context.getClass() == Activity.class)
                        try {
                            ((Activity) context).runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    pDialog.dismiss();
                                    Toast.makeText(context, PersianReshape.reshape(context.getResources().getString(R.string.download_completed)), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (Exception e) {
                            try {
                                pDialog.dismiss();
                                Toast.makeText(context, PersianReshape.reshape(context.getResources().getString(R.string.download_completed)), Toast.LENGTH_SHORT).show();
                            } catch (Resources.NotFoundException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                    if (onCompleteRunnable != null)
                        onCompleteRunnable.run();
                } catch (Exception e) {
                    LogCatHelper.ShowErrorLog(null, "Error DL: " + e.getLocalizedMessage());
                    if (context.getClass() == Activity.class)
                        try {
                            ((Activity) context).runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    if (ShowProgress) {
                                        pDialog.dismiss();
                                        MessageBox.Show(context, PersianReshape.reshape(context.getResources().getString(R.string.error_download_file)), null);
                                    }

                                }
                            });
                        } catch (Exception e1) {
                            try {
                                if (ShowProgress) {
                                    pDialog.dismiss();
                                    MessageBox.Show(context, PersianReshape.reshape(context.getResources().getString(R.string.error_download_file)), null);
                                }
                            } catch (Resources.NotFoundException ignored) {

                            }
                        }

                    File mp4 = new File(DestDir + "/" + DestFileName);
                    if (mp4.exists())
                        mp4.delete();
                    File mp41 = new File(DestDir + "/" + DestFileName + "_tmp");
                    if (mp41.exists())
                        mp41.delete();
                }

                return;
            }
        });
        ThStartDownload.start();

    }

    public int GetFileSizeInBytes(String Inputurl) {
        try {
            URL url = new URL(Inputurl);
            URLConnection conection = url.openConnection();
            conection.connect();
            return conection.getContentLength();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }

    public void StopThread() {
        try {
            ThCanceled = true;
            ThStartDownload.interrupt();
            ThStartDownload = null;
            Toast.makeText(context, PersianReshape.reshape(context.getResources().getString(R.string.download_canceled)), Toast.LENGTH_LONG).show();
            File mp4 = new File(DestDir + "/" + DestFileName);
            if (mp4.exists())
                mp4.delete();
            File mp41 = new File(DestDir + "/" + DestFileName + "_tmp");
            if (mp41.exists())
                mp41.delete();
        } catch (Exception e) {
            Toast.makeText(context, PersianReshape.reshape(context, R.string.error_stopping_download), Toast.LENGTH_LONG).show();
        }
    }
}
