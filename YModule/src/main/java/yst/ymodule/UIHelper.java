package yst.ymodule;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by norouzi on 07/08/2014, updated by hadi 04/03/2015
 */
public class UIHelper {

    public static String FontName = "IRAN Sans.ttf";
    //    public static String FontName = "IRAN.ttf";
    public static String FontName2 = "IRAN Sans Light.ttf";
    public static String EnglishFontName = "lanenar.ttf";
    public static String FontNameTitr = "BTitrBd.ttf";
    private static Typeface face;
    private static Typeface face2;
    private static Typeface faceEng;
    private static Typeface faceTitr;

    private static Typeface initTypeFace(Context context) {
        face = Typeface.createFromAsset(context.getAssets(), "font/" + FontName + "");
        return face;
    }

    private static Typeface initTypeFace2(Context context) {
        face2 = Typeface.createFromAsset(context.getAssets(), "font/" + FontName2 + "");
        return face2;
    }

    private static Typeface initTypeFaceEnglish(Context context) {
        faceEng = Typeface.createFromAsset(context.getAssets(), "font/" + EnglishFontName + "");
        return faceEng;
    }

    private static Typeface initTypeFaceTitr(Context context) {
        faceTitr = Typeface.createFromAsset(context.getAssets(), "font/" + FontNameTitr + "");
        return faceTitr;
    }

    public static Typeface getTypeFaceTitr(Context context) {
        if (faceTitr != null)
            return faceTitr;
        else
            return initTypeFaceTitr(context);
    }

    public static Typeface getTypeFace(Context context) {
        if (face != null)
            return face;
        else
            return initTypeFace(context);
    }

    public static Typeface getTypeFace2(Context context) {
        if (face2 != null)
            return face2;
        else
            return initTypeFace2(context);
    }

    public static Typeface getTypeFaceEnglish(Context context) {
        if (faceEng != null)
            return faceEng;
        else
            return initTypeFaceEnglish(context);
    }

    public static Typeface getTypeFaceWithName(Context context, String fontName) {
        return Typeface.createFromAsset(context.getAssets(), "font/" + fontName + "");
    }


    public static void setFont(Typeface typeface, View parent, int... viewIDs) {
        TextView textView;
        for (int viewID : viewIDs) {
            textView = parent.findViewById(viewID);
            textView.setTypeface(typeface);
            textView.setPaintFlags(textView.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG | Paint.DEV_KERN_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        }
    }

    public static void setTypeface(TextView textView, Context context) {
        textView.setTypeface(getTypeFace(context));
        textView.setPaintFlags(textView.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG | Paint.DEV_KERN_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    }

    public static void setTypeface(TextView textView, Typeface typeface) {
        textView.setTypeface(typeface);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG | Paint.DEV_KERN_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    }

    public static void setFont(Typeface typeface, Activity activity, int... viewIDs) {
        TextView textView;
        for (int viewID : viewIDs) {
            textView = activity.findViewById(viewID);
            textView.setTypeface(typeface);
            textView.setPaintFlags(textView.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG | Paint.DEV_KERN_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        }
    }

    public static void applyTypefaceToAll(View view, Context context) {
        ApplyTypeface applyTypeface = new ApplyTypeface(getTypeFace(context));
        applyTypeface.applyToAll(view);
    }

    public static void applyTypefaceToAll(View view, Typeface typeface) {
        ApplyTypeface applyTypeface = new ApplyTypeface(typeface);
        applyTypeface.applyToAll(view);
    }

    public static void applyTypefaceToAll(ViewGroup viewGroup, Typeface typeface) {
        ApplyTypeface applyTypeface = new ApplyTypeface(typeface);
        applyTypeface.applyToAll(viewGroup);
    }

    public static class ApplyTypeface {
        Typeface typeface;

        public ApplyTypeface(Typeface typeface) {
            this.typeface = typeface;
        }

        public void applyToAll(View view) {
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int childIndex = 0; childIndex < viewGroup.getChildCount(); childIndex++)
                    applyToAll(viewGroup.getChildAt(childIndex));
            } else {
                try {
                    TextView textView = (TextView) view;
                    if (textView != null) {
                        textView.setTypeface(typeface);
                        textView.setPaintFlags(textView.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG | Paint.DEV_KERN_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                    }
                } catch (Exception ex) {
                    return;
                }
            }
        }
    }
}