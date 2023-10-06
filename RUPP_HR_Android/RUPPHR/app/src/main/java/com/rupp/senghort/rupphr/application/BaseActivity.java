package com.rupp.senghort.rupphr.application;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import com.rupp.senghort.rupphr.utils.LanguageHelper;

/**
 * Created by ADMIN on 8/14/2018.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageHelper.onAttach(base));
    }
}
