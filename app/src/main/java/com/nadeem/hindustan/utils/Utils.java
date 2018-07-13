package com.nadeem.hindustan.utils;

import android.app.Activity;
import android.os.Build;
import android.text.format.DateFormat;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.nadeem.hindustan.HindustanApplication;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ikram on 12/3/18.
 */

public class Utils {
    public static boolean isValidEmail(String email) {
        if (email == null || email.equals(""))
            return false;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void showToast(String message) {
        Toast.makeText(HindustanApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    public static String parseDate(String time, int type) {
        String inputPattern = "yyyyMMdd";
        String outputPattern = "yyyy-MM-dd HH:mm:ss";

        switch (type) {
            case 1:
                outputPattern = "EE, dd MMM yyyy hh:mm a";
                break;
            case 2:
                outputPattern = "EE, dd MMM yyyy hh:mm"; //EE=> weekday name
                break;
            case 3:
                outputPattern = "EE, dd MMM yyyy hh:mm"; //EE=> weekday name
                break;
            case 4:
                outputPattern = "dd/MMM/yyyy";
                break;
            case 5:
                outputPattern = "MMM dd, yyyy";
                break;
            case 6:
                outputPattern = "EEE,dd MMM";
                break;
            case 7:
                outputPattern = "dd MMM";
                break;
            case 8:
                outputPattern = "dd MMMM";
                break;
            case 9:
                outputPattern = "hh:mm a";
                break;
            case 10:
                outputPattern = "EE,MMM dd";
                break;
            case 11:
                outputPattern = "dd MMM, yyyy";
                break;
            case 12:
                Calendar smsTime = Calendar.getInstance();
                Calendar now = Calendar.getInstance();

                inputPattern = "yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.getDefault());
                try {
                    smsTime.setTimeInMillis(inputFormat.parse(time).getTime());


                } catch (Exception e) {
                }
                final String timeFormatString = "h:mm aa";
                final String dateTimeFormatString = "MMM dd, h:mm aa";
                final long HOURS = 60 * 60 * 60;
                if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE)) {
                    return "Today " + DateFormat.format(timeFormatString, smsTime);
                } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1) {
                    return "Yesterday " + DateFormat.format(timeFormatString, smsTime);
                } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
                    return DateFormat.format(dateTimeFormatString, smsTime).toString();
                } else {
                    return DateFormat.format("MMMM dd, h:mm aa", smsTime).toString();
                }
        }
        //String outputPattern = type == 1 ? "EE, dd MMM yyyy hh:mm a" : "EE, dd/MMM/yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, Locale.getDefault());
        Date date;
        String str = null;
        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            LogReport.e(Utils.class.getSimpleName(), e.getMessage(), e);
            str = time;
        }
        return str;
    }

    public static void hideSoftKeybord(Activity activity) {
        if (activity == null || activity.getCurrentFocus() == null)
            return;
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
/*
    public static SpannableStringBuilder getSpannable(SpannableStringBuilder spanRange, int startSpan, int endSpan, CharacterStyle style){
        //TextAppearanceSpan tas = new TextAppearanceSpan(context, android.R.style.TextAppearance_Large);
        spanRange.setSpan(style, startSpan, endSpan, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanRange;
    }*/

    public static String getRemainingTime(double timeRemaining) {
        if (timeRemaining > (60 * 60)) {
            return " " + ((int) timeRemaining / (60 * 60)) + " hours";
        } else if (timeRemaining > 60) {
            return " " + ((int) timeRemaining / (60)) + " min";
        } else {
            return " " + timeRemaining + " sec";
        }

    }

    public static double round(double d/*, int decimalPlace*/) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(2/*decimalPlace*/, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    public static boolean isGreaterJellyBean() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            return true;
        }
        return false;
    }
}
