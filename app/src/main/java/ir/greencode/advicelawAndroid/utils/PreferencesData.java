package ir.greencode.advicelawAndroid.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import ir.greencode.advicelawAndroid.controler.AppController;
import ir.greencode.advicelawAndroid.main.ActivityMain;


public class PreferencesData {


    public static void saveString( String key, String value,Context context) {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        sharedPrefs.edit().putString(key, value).commit();
    }







    public static boolean saveBool( String key, boolean value,Context context){
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
       return sharedPrefs.edit().putBoolean(key, value).commit();
    }
    public static boolean saveInt( String key, int value,Context context){
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
       return sharedPrefs.edit().putInt(key, value).commit();
    }




 public static String getString( String key,Context context) {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPrefs.getString(key, "");
    }



    public static boolean getBoolean( String key,Context context) {

            SharedPreferences sharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(context);
            return sharedPrefs.getBoolean(key, false);


    }


    public static int getInt(String key, Context context) {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPrefs.getInt(key, 0);
    }
}
