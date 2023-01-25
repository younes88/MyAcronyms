package yst.ymodule;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Kasper on 4/4/2015.
 */
public class CodeHelper {

    public static final String getMd5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getAppVersion(Context context) {
        String ver = "1.0";
        PackageInfo pInfo;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            ver = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return ver;
    }

    final static String alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    final static String digits = "0123456789";

    static Random secRand = new SecureRandom();


    public static String randomString(int len) {

        if (len < 2) {
            throw new IllegalArgumentException("randomString - length too short: " + len);
        }

        // Pool of characters to select from.
        String pool = "";

        // Array to hold random characters.
        Character[] result = new Character[len];

        // Index into result.
        int index = 0;

//        if (switchLetters.isChecked()) {
            // Put letters in pool.
//            pool = pool + alpha;

            // Ensure at least one letter.
//            result[index] = alpha.charAt(secRand.nextInt(alpha.length()));
//            index++;
//        }

//        if (switchDigits.isChecked()) {
////             Put digits in pool.
            pool = pool + digits;
//
////             Ensure at least one digit.
            result[index] = digits.charAt(secRand.nextInt(digits.length()));
            index++;
//        }

        // Fill rest of result array from pool.
        for (; index < len; index++) {
            result[index] = pool.charAt(secRand.nextInt(pool.length()));
        }

        // Shuffle result array with secRand to hide ordering.
        Collections.shuffle(Arrays.asList(result), secRand);

        // Assemble return string.
        StringBuilder sb = new StringBuilder(len);
        for (Character c : result) {
            sb.append(c);
        }
        return sb.toString();

    }  // end randomString()
}
