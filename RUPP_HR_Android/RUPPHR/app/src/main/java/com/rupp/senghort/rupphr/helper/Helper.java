package com.rupp.senghort.rupphr.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.rupp.senghort.rupphr.R;
import com.rupp.senghort.rupphr.activity.LoginActivity;

/**
 * Created by ADMIN on 5/6/2018.
 */

public class Helper {

    public static void showMsgAlert(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setNegativeButton(R.string.app_cancel, null);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static String checkNull(String value) {
        if (value.equals("null")) {
            value = "";
        }
        return value;
    }
}
