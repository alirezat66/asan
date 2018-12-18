package ir.greencode.advicelawAndroid.utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.kaopiz.kprogresshud.KProgressHUD;

import ir.greencode.advicelawAndroid.R;
import ir.greencode.advicelawAndroid.controler.AppController;
import saman.zamani.persiandate.PersianDate;


/**
 * Created by alireza on 5/5/18.
 */

public class Utility {

    public static KProgressHUD makeWaiting(String title, String desc, Context context)
    {
        KProgressHUD kProgressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(title)
                .setDetailsLabel(desc)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.8f)
                .setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
        return kProgressHUD;
    }

    public static KProgressHUD makeWaiting(Context context) {
        KProgressHUD kProgressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("لطفا منتظر بمانید")
                .setDetailsLabel("در حال دریافت اطلاعات...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.8f)
                .setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
        return kProgressHUD;
    }

    public static void dissMissWaiting(KProgressHUD kProgressHUD) {
        kProgressHUD.dismiss();
    }

    public static String getDoubleStringValue(double value) {

        int count = (int) value;

        if (value - count == 0) {
            return count + "";
        } else {
            return value + "";
        }
    }

    public static void freeMemory() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static Drawable getDrawable(Context context, int id) {
        Drawable drawable;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            drawable = context.getResources().getDrawable(id, context.getTheme());
        } else {
            drawable = VectorDrawableCompat.create(context.getResources(), id, context.getTheme());
        }
        return drawable;
    }

    public static Bitmap resizeBitmap(final Bitmap temp, final int size) {
        if (size > 0) {
            int width = temp.getWidth();
            int height = temp.getHeight();
            float ratioBitmap = (float) width / (float) height;
            int finalWidth = size;
            int finalHeight = size;
            if (ratioBitmap < 1) {
                finalWidth = (int) ((float) size * ratioBitmap);
            } else {
                finalHeight = (int) ((float) size / ratioBitmap);
            }
            return Bitmap.createScaledBitmap(temp, finalWidth, finalHeight, true);
        } else {
            return temp;
        }
    }


    public static void setKeyboardFocus(final EditText primaryTextField) {
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                primaryTextField.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
                primaryTextField.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
            }
        }, 100);
    }

    public static Typeface getRegularTypeFace(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "iransansmobilefanum.ttf");
    }
    public static Typeface getEnglishModel(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "isans.ttf");
    }
    public static String convertToPersianDigits(String value) {
        return value.replace("1", "١").replace("2", "٢").replace("3", "٣").replace("4", "۴").replace("5", "۵")
                .replace("6", "۶").replace("7", "٧").replace("8", "٨").replace("9", "٩").replace("0", "٠");
    }

    public static String getVersionName(Context context){
        String versionName= "";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
         versionName  = pInfo.versionName;


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
    public static Bitmap getBitmapFromBase(String b64) {
        byte[] decodedString = Base64.decode(b64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public static String getToken() {
        return PreferencesData.getString(Constants.PREF_TOKEN, AppController.getContext());
    }

    public static int getId() {
        return PreferencesData.getInt(Constants.Pref_USER_ID, AppController.getContext());
    }

    public static String getPersianDayName(String dayOfWeek) {
        String pName = "";
        switch (dayOfWeek) {
            case "Saturday":
                pName = "شنبه";
                break;
            case "Sunday":
                pName = "یک\u200Cشنبه";
                break;
            case "Monday":
                pName = "دوشنبه";
                break;
            case "Tuesday":
                pName = "سه\u200Cشنبه";
                break;
            case "Wednesday":
                pName = "چهارشنبه";
                break;
            case "Thursday":
                pName = "پنج\u200Cشنبه";
                break;
            case "Friday":
                pName = "جمعه";
                break;


        }
        return pName;
    }

    public static String cutLastChar(String str) {
        String a = str.substring(0,str.length()-1);
        return a;
    }

    public static String getDefrence(long time) {
        PersianDate p1 = new PersianDate(time);
        PersianDate nowTime = new PersianDate(System.currentTimeMillis());
        if(nowTime.getShYear()==p1.getShYear()
                && nowTime.getShMonth() == p1.getShMonth()){
            // dar yek mah hastan
            if(p1.getShDay() == nowTime.getShDay()){
                return "امروز";
            }else {
                if(nowTime.getShDay()<p1.getShDay()){
                    return "تاریخ گوشی تنظیم نیست";
                }else {
                    int defrence = nowTime.getShDay()- p1.getShDay();
                    if(defrence==1){
                        return "دیروز";
                    }if(defrence==7){
                        return "هفته پیش";
                    }else if(defrence ==14){
                        return "دو هفته پیش";
                    }else if(defrence==21){
                        return "سه هفته پیش";
                    }else if(defrence==28){
                        return "چهار هفته پیش";
                    }else {
                        return defrence +" روز پیش";
                    }
                }
            }
        }else {
            if(p1.getShYear()==nowTime.getShYear()){
                return nowTime.getShMonth()-p1.getShMonth() +" ماه پیش";
            }else {
                return nowTime.getShYear()-p1.getShYear()+" سال پیش";
            }
        }
    }

    public static String makeCancelTitle(long adviceTime,int price) {
       long defrence = System.currentTimeMillis() -adviceTime;

       long eachDay = 24*60*60000;
       long eachHour = 60*60000;
        int finalPrice = 0;
       if(defrence>eachDay){
           finalPrice = price*20/100;
           return "در صورت لغو مشاوره، مبلغ " +finalPrice +" تومان از پرداختی شما کسر می شود و باقی مانده به شما برگردانده می شود. از لغو مشاوره اطمینان دارید؟";
       }else if(defrence>=eachHour){
           finalPrice = price*50/100;
           return "در صورت لغو مشاوره، مبلغ "+finalPrice+" تومان از پرداختی شما کسر می شود و باقی مانده به شما برگردانده می شود. از لغو مشاوره اطمینان دارید؟";
       }else {
           return "در صورت لغو مشاوره کل مبلغ پرداختی شما کسر می شود. آیا از لغو مشاوره اطمینان دارید.";
       }
    }
}
