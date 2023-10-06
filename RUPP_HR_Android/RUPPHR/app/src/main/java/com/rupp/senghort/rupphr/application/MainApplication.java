package com.rupp.senghort.rupphr.application;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import com.rupp.senghort.rupphr.utils.LanguageHelper;

/**
 * Created by KHEANG SENGHORT on 4/10/2018.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    // override the base context of application to update default locale for the application
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageHelper.onAttach(base, LanguageHelper.getLanguage(base)));
    }

//    LocalizationApplicationDelegate localizationDelegate = new LocalizationApplicationDelegate(this);
//
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(localizationDelegate.attachBaseContext(base));
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        localizationDelegate.onConfigurationChanged(this);
//    }
//
//    @Override
//    public Context getApplicationContext() {
//        return localizationDelegate.getApplicationContext(super.getApplicationContext());
//    }

}
