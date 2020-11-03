package com.unilever.julia.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;

public class JavaUtils {

    private static final Gson mGson = new Gson();

    @NonNull
    public static String toHexString(@NonNull Context context, @ColorRes int resColor) {
        return "#"+ Integer.toHexString(ContextCompat.getColor(context, resColor));
    }

    @NonNull
    public static String removeAccents(@NonNull String text) {
        String newText = text.replaceAll("[ç]", "c");
        newText = newText.replaceAll("[Ç]", "C");
        newText = newText.replaceAll("[áàäãâ]", "a");
        newText = newText.replaceAll("[ÁÀÄÃÂ]", "A");
        newText = newText.replaceAll("[éèëê]", "e");
        newText = newText.replaceAll("[ÉÈËÊ]", "E");
        newText = newText.replaceAll("[íìïî]", "i");
        newText = newText.replaceAll("[ÍÌÏÎ]", "I");
        newText = newText.replaceAll("[óòöõô]", "o");
        newText = newText.replaceAll("[ÓÒÖÕÔ]", "O");
        newText = newText.replaceAll("[úùüû]", "u");
        newText = newText.replaceAll("[ÚÙÜÛ]", "U");
        return newText;
    }

    @NonNull
    public static String parseStringHexa(String valHexStr) {
        long valLong = Long.parseLong(valHexStr, 16);
        return new String(new char[]{ (char)valLong });
    }

    @NonNull
    public static Map<String, String> parseJsonToMap(String jsonString) {
        try {
            Type type = new TypeToken<Map<String, String>>(){}.getType();
            return mGson.fromJson(jsonString, type);
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    @NonNull
    public static Map<String, Object> parseJsonToMapObject(@NonNull String jsonString) {
        try {
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            return mGson.fromJson(jsonString, type);
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    @NonNull
    public static String toJson(@NonNull Map<String, Object> object) {
        try {
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            return mGson.toJson(object, type);
        } catch (Exception e) {
            return "";
        }
    }

    @NonNull
    public static List<Map<String, String>> parseJsonToListOfMap(String jsonString) {
        Type type = new TypeToken<List<Map<String, String>>>(){}.getType();
        return mGson.fromJson(jsonString, type);
    }

    public static boolean dateInPeriod(Date date, Date startDate, Date endDate) {
        return (date.after(startDate) && date.before(endDate)) || (date.equals(startDate) || date.equals(endDate));
    }

    @Nullable
    public static Date toDate(@NonNull String data, @NonNull String format) {
        try {
            DateFormat parser = new SimpleDateFormat(format, Locale.getDefault());
            return parser.parse(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @NonNull
    public static Date removeTime(@NonNull Date date) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            calendar.set(year, month, day, 0,0,0);

            return calendar.getTime();
        } catch (Exception e) {
            return date;
        }
    }

    private enum FieldDateEnum {
        DAY, MONTH, YEAR, HOUR, MINUTE, SECOND, MILLIS
    }

    private static String getStringByDateAndFormat(final Date data) {
        return getStringByDateAndFormat(data, "dd-MM-yyyy-HH-mm-ss-sss");
    }

    public static String getStringByDateAndFormat(final Date data, final String pattern) {
        DateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format.format(data);
    }

    private static int getRealFieldInDate(Date date, FieldDateEnum fieldDateEnum) {
        String strDate = getStringByDateAndFormat(date);
        String[] split = strDate.split("[-]");

        if (fieldDateEnum.equals(FieldDateEnum.DAY)) {
            return Integer.valueOf(split[0]);
        } else if (fieldDateEnum.equals(FieldDateEnum.MONTH)) {
            return Integer.valueOf(split[1]);
        } else if (fieldDateEnum.equals(FieldDateEnum.YEAR)) {
            return Integer.valueOf(split[2]);
        } else if (fieldDateEnum.equals(FieldDateEnum.HOUR)) {
            return Integer.valueOf(split[3]);
        } else if (fieldDateEnum.equals(FieldDateEnum.MINUTE)) {
            return Integer.valueOf(split[4]);
        } else if (fieldDateEnum.equals(FieldDateEnum.SECOND)) {
            return Integer.valueOf(split[5]);
        } else if (fieldDateEnum.equals(FieldDateEnum.MILLIS)) {
            return Integer.valueOf(split[6]);
        }
        return 0;
    }

    public static int getDayInMonth(Date date) {
        return getRealFieldInDate(date, FieldDateEnum.DAY);
    }

    public static int getMonthInYear(Date date) {
        return getRealFieldInDate(date, FieldDateEnum.MONTH);
    }

    public static int getYearInDate(Date date) {
        return getRealFieldInDate(date, FieldDateEnum.YEAR);
    }

    public static String getMonthAbreviate(Date date) {
        int month = getMonthInYear(date);
        if (month == 1) {
            return "JAN";
        } else if (month == 2) {
            return "FEV";
        } else if (month == 3) {
            return "MAR";
        } else if (month == 4) {
            return "ABR";
        } else if (month == 5) {
            return "MAI";
        } else if (month == 6) {
            return "JUN";
        } else if (month == 7) {
            return "JUL";
        } else if (month == 8) {
            return "AGO";
        } else if (month == 9) {
            return "SET";
        } else if (month == 10) {
            return "OUT";
        } else if (month == 11) {
            return "NOV";
        } else if (month == 12) {
            return "DEZ";
        }
        return "";
    }

    /**
     * @param key string like: SHA1, SHA256, MD5.
     */
    @SuppressLint("PackageManagerGetSignatures")
    public static String getKeyHash(@NonNull Context context, @NonNull String packageName, @NonNull String key) {
        try {
            final PackageInfo info = context.getPackageManager()
                    .getPackageInfo(packageName, PackageManager.GET_SIGNATURES);

            StringBuilder toRet = new StringBuilder();

            for (Signature signature : info.signatures) {
                final MessageDigest md = MessageDigest.getInstance(key);
                md.update(signature.toByteArray());

                final byte[] digest = md.digest();
                for (int i = 0; i < digest.length; i++) {
                    if (i != 0) toRet.append(":");
                    int b = digest[i] & 0xff;
                    String hex = Integer.toHexString(b);
                    if (hex.length() == 1) toRet.append("0");
                    toRet.append(hex);
                }
                //Log.d("FINGERPRINT", key + " " + toRet.toString());
            }

            return StringUtils.upperCase(toRet.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
            throw new RuntimeException("Key Certificate Empty");
        }
    }
}
