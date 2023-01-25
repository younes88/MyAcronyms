package yst.ymodule;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import ir.yst.ymodule.R;

/**
 * Created by Kasper on 8/13/2015.
 */
public class ValidationHelper {

    public static boolean ValidateEmail(CharSequence target) {
        return target != null && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean ValidateWebSite(CharSequence target) {
        return target != null && Patterns.WEB_URL.matcher(target).matches();
    }

    public static boolean ValidatePhone(CharSequence target) {
        return target != null && Patterns.PHONE.matcher(target).matches();
    }

    public static boolean ValidateEmail2(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            boolean ret = true;
            if (!target.toString().contains("@"))
                ret = false;
            if (!target.toString().contains("."))
                ret = false;
            String[] ar1 = Split(target.toString(), '.');
            if (ar1.length < 2)
                ret = false;

            return ret;
        }
    }

    public static boolean ValidateMobile(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            boolean ret = true;
            if (target.toString().startsWith("0"))
                ret = false;
            if (!(target.length() == 10))
                ret = false;
            return ret;
        }
    }

    public static boolean ValidateMobile_WithZero(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            boolean ret = true;
            if (!target.toString().startsWith("09"))
                ret = false;
            if (!(target.length() == 11))
                ret = false;
            return ret;
        }
    }

    public static boolean ValidateNationalCode(String code) {
        try {
            int L = code.length();

            if (L < 8 || Long.parseLong(code, 10) == 0)
                return false;
            code = ("0000" + code).substring(L + 4 - 10);
            if (Long.parseLong(code.substring(3, 6), 10) == 0)
                return false;
            char[] ar = code.toCharArray();
            long c = Long.parseLong(ar[9] + "", 10);
            int s = 0;
            for (int i = 0; i < 9; i++)
                s += Long.parseLong(ar[i] + "", 10) * (10 - i);
            s = s % 11;
            return (s < 2 && c == s) || (s >= 2 && c == (11 - s));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void SetEditTextError(EditText txt, String errMsg) {
        String textColorName = "white";
        if (Build.VERSION.SDK_INT <= 10)
            textColorName = "black";
        txt.setError(Html.fromHtml("<font color='" + textColorName + "'>" + errMsg + "</font>"));
        txt.requestFocus();
    }

    private static void SetEditTextError(com.rey.material.widget.EditText txt, String errMsg) {
        String textColorName = "white";
        if (Build.VERSION.SDK_INT <= 10)
            textColorName = "black";
        txt.setError(errMsg);
//        txt.setError(Html.fromHtml("<font color='" + textColorName + "'>" + errMsg + "</font>"));
        txt.requestFocus();
    }

    public static void SetEditTextError(final EditText txt, int ErrorMsgID, Context context) {
        SetEditTextError(txt, PersianReshape.reshape(context, ErrorMsgID));
        txt.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus)
                txt.setError(null);
            }

        });

    }

    public static void SetEditTextError(final com.rey.material.widget.EditText txt, int ErrorMsgID, Context context) {
        SetEditTextError(txt, PersianReshape.reshape(context, ErrorMsgID));
        txt.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus)
                txt.setError(null);
            }

        });
    }

    public static boolean ValidatePhoneTextBox(EditText txt, Context context) {
        String str = txt.getText().toString();
        boolean ret = false;
        if (TextUtils.isEmpty(str)) {
            SetEditTextError(txt, R.string.this_field_must_not_empty, context);
        } else {
            if (ValidatePhone(str))
                ret = true;
            else
                SetEditTextError(txt, R.string.invalid_phone_number, context);
        }
        return ret;
    }


    public static boolean ValidateMobileTextBox(EditText txt, Context context) {
        String str = txt.getText().toString();
        boolean ret = false;
        if (TextUtils.isEmpty(str)) {
            SetEditTextError(txt, R.string.this_field_must_not_empty, context);
        } else if (str.startsWith("0")) {
            SetEditTextError(txt, R.string.mobile_no_without_zero, context);
        } else {
            if (ValidateMobile(str))
                ret = true;
            else
                SetEditTextError(txt, R.string.invalid_phone_number, context);
        }
        return ret;
    }

    public static boolean ValidateMobileTextBox_WithZero(EditText txt, Context context) {
        String str = txt.getText().toString();
        boolean ret = false;
        if (TextUtils.isEmpty(str)) {
            SetEditTextError(txt, R.string.this_field_must_not_empty, context);
        } else {
            if (ValidateMobile_WithZero(str))
                ret = true;
            else
                SetEditTextError(txt, R.string.invalid_phone_number, context);
        }
        return ret;
    }

    public static boolean ValidateMobileTextBox_WithZero(com.rey.material.widget.EditText txt, Context context) {
        String str = txt.getText().toString();
        boolean ret = false;
        if (TextUtils.isEmpty(str)) {
            SetEditTextError(txt, R.string.this_field_must_not_empty, context);
        } else {
            if (ValidateMobile_WithZero(str))
                ret = true;
            else
                SetEditTextError(txt, R.string.invalid_phone_number, context);
        }
        return ret;
    }

    public static boolean ValidateMailTextBox(EditText txt, Context context) {
        String str = txt.getText().toString();
        boolean ret = false;
        if (TextUtils.isEmpty(str)) {
            SetEditTextError(txt, R.string.this_field_must_not_empty, context);
        } else {
            if (ValidateEmail(str))
                ret = true;
            else
                SetEditTextError(txt, R.string.invalid_email_address, context);
        }
        return ret;
    }

    public static boolean ValidateMailTextBox(com.rey.material.widget.EditText txt, Context context) {
        String str = txt.getText().toString();
        boolean ret = false;
        if (TextUtils.isEmpty(str)) {
            SetEditTextError(txt, R.string.this_field_must_not_empty, context);
        } else {
            if (ValidateEmail(str))
                ret = true;
            else
                SetEditTextError(txt, R.string.invalid_email_address, context);
        }
        return ret;
    }

    public static boolean ValidateWebSiteTextBox(EditText txt, Context context) {
        String str = txt.getText().toString();
        boolean ret = false;
        if (TextUtils.isEmpty(str)) {
            SetEditTextError(txt, R.string.this_field_must_not_empty, context);
        } else {
            if (ValidateWebSite(str))
                ret = true;
            else
                SetEditTextError(txt, R.string.invalid_website_address, context);
        }
        return ret;
    }

    public static boolean ValidateWebSiteTextBox(com.rey.material.widget.EditText txt, Context context) {
        String str = txt.getText().toString();
        boolean ret = false;
        if (TextUtils.isEmpty(str)) {
            SetEditTextError(txt, R.string.this_field_must_not_empty, context);
        } else {
            if (ValidateWebSite(str))
                ret = true;
            else
                SetEditTextError(txt, R.string.invalid_website_address, context);
        }
        return ret;
    }

    public static boolean ValidateNationalCodeTextBox(EditText txt, Context context) {
        String str = txt.getText().toString();
        boolean ret = false;
        if (TextUtils.isEmpty(str)) {
            SetEditTextError(txt, R.string.this_field_must_not_empty, context);
        } else {
            if (ValidateNationalCode(str))
                ret = true;
            else
                SetEditTextError(txt, R.string.invalid_national_code, context);
        }
        return ret;
    }

    public static boolean ValidateEmptyTextBox(EditText txt, Context context) {
        boolean ret = false;
        if (TextUtils.isEmpty(txt.getText().toString())) {
            SetEditTextError(txt, R.string.this_field_must_not_empty, context);
        } else
            ret = true;
        return ret;
    }

    public static boolean ValidateEmptyTextBoxes(Context context, EditText... txts) {
        for (EditText txt : txts)
            if (!ValidateEmptyTextBox(txt, context))
                return false;
        return true;
    }


    public static boolean ValidateEmptyTextBox(com.rey.material.widget.EditText txt, Context context) {
        boolean ret = false;

        if (TextUtils.isEmpty(txt.getText().toString())) {
            SetEditTextError(txt, R.string.this_field_must_not_empty, context);
        } else
            ret = true;
        return ret;
    }

    public static boolean ValidateTelCodeTextBox(com.rey.material.widget.EditText txt, Context context) {
        boolean ret = false;

        if (TextUtils.isEmpty(txt.getText().toString())) {
            SetEditTextError(txt, R.string.this_field_must_not_empty, context);
        } else if (txt.getText().toString().length() < 3)
            SetEditTextError(txt, R.string.tel_code_must_be_more_than_3, context);
        else
            ret = true;
        return ret;
    }

    private static String[] Split(String original, char separator) {
        try {
            if (original.equals(""))
                return new String[]{original};
            if (original.charAt(0) == separator) {
                original = original.substring(1);
            }
            ArrayList lst = new ArrayList();
            char[] chars = original.toCharArray();
            String buff = "";
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == separator) {
                    lst.add(buff);
                    buff = "";
                } else {
                    buff += chars[i];
                }
            }
            // Add Latest Item
            if (buff != "") {
                lst.add(buff);
            }
            String[] ret = new String[lst.size()];
            for (int k = 0; k < lst.size(); k++) {
                ret[k] = lst.get(k).toString();
            }
            return ret;
        } catch (Exception e) {
            return new String[]{original};
        }
    }

}
