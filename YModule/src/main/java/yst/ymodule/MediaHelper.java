package yst.ymodule;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

public class MediaHelper {

    private static MediaPlayer mp;

    public static void PlayAudioFile(String path, String fileName) {
        // set up MediaPlayer
        InitPlayer();
        try {
            mp.setDataSource(path + "/" + fileName);
            mp.prepare();
            mp.start();
        } catch (Exception e) {
            LogCatHelper.ShowErrorLog(null,
                    "Error Play Music: " + e.getMessage());
        }
    }

    public static void PlayBeep(Context context, BeepTypes beepType) {
        try {
            InitPlayer();
            String FileName = "info.mp3";
            if (beepType == BeepTypes.Error)
                FileName = "error.mp3";
            else if (beepType == BeepTypes.Information)
                FileName = "info.mp3";
            else if (beepType == BeepTypes.Question)
                FileName = "ques.mp3";
            else if (beepType == BeepTypes.Warning)
                FileName = "warning.mp3";

            AssetFileDescriptor descriptor = context.getAssets().openFd(
                    FileName);
            mp.setDataSource(descriptor.getFileDescriptor(),
                    descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            mp.prepare();
            // mp.setVolume(1f, 1f);
            mp.setLooping(false);
            mp.start();
        } catch (Exception e) {
            LogCatHelper.ShowErrorLog(null, "Error Play Beep: " + e.getMessage());
        }
    }

    public static void StopPlayer() {
        if (mp != null) {
            if (mp.isPlaying()) {
                mp.stop();
                mp.release();
                mp = null;
            }
        }
    }

    public static void InitPlayer() {
        StopPlayer();
        mp = new MediaPlayer();
    }

    public enum BeepTypes {
        Error, Warning, Information, Question
    }

}
