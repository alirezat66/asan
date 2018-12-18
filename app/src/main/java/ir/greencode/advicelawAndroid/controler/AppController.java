package ir.greencode.advicelawAndroid.controler;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;

import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.onesignal.OneSignal;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import ir.greencode.advicelawAndroid.R;


public class AppController extends MultiDexApplication {


    private static Activity CurrentActivity = null;
    private static Context CurrentContext;
    private static Context sContext;



    @Override
    public void onCreate() {
        
        // The following line triggers the initialization of ACRA
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("iransansmobilefanum.ttf").setFontAttrId(R.attr.fontPath).build());
        MultiDex.install(this);
        Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics()).debuggable(true)
                .build();
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());


        sContext = getApplicationContext();

    }
    public static Context getContext() {
        return sContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }





}