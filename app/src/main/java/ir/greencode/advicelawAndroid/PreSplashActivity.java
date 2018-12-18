package ir.greencode.advicelawAndroid;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import java.util.List;

import ir.greencode.advicelawAndroid.main.ActivityMain;
import ir.greencode.advicelawAndroid.signin.SignInActivity;
import ir.greencode.advicelawAndroid.utils.BaseActivity;
import ir.greencode.advicelawAndroid.utils.Constants;
import ir.greencode.advicelawAndroid.utils.PreferencesData;

public class PreSplashActivity extends BaseActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        welcomeScreen();
    }


    private void welcomeScreen() {
       /* PillUsage pillUsage = appDatabase.pillUsageDao().getNearestUsed(System.currentTimeMillis());
        if(pillUsage!=null){
            startAlarmPillReminder(pillUsage);
        }*/



        if(!PreferencesData.getBoolean(Constants.PREF_LOGIN,this)){


            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(this, ActivityMain.class);
            startActivity(intent);
            finish();
        }

    }


}
