package com.rupp.senghort.rupphr.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.vision.barcode.Barcode;
import com.rupp.senghort.rupphr.R;
import com.rupp.senghort.rupphr.application.BaseActivity;
import com.rupp.senghort.rupphr.helper.API_KEY;
import com.rupp.senghort.rupphr.helper.DataParseRequestQueue;
import com.rupp.senghort.rupphr.helper.DataParser;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import info.androidhive.barcode.BarcodeReader;

public class QRCodeActivity extends BaseActivity implements BarcodeReader.BarcodeReaderListener {

    private BarcodeReader barcodeReader;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        // getting barcode instance
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_fragment);

        // Check permission camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.CAMERA}, 1);
            }
        }

    }

    private void showMsgConfirm(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);

        // add the buttons
        builder.setPositiveButton(R.string.app_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                 dialog.hide();
            }
        });
        builder.setNegativeButton(R.string.app_cancel, null);

        // create and show the alert dialog
        dialog = builder.create();
        dialog.show();
    }

    private void showMsgAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        // add the buttons
        builder.setNegativeButton(R.string.app_ok, null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onScanned(final Barcode barcode) {
        barcodeReader.playBeep();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("[CD]: ", barcode.displayValue);
                asyncUser(DataParser.USER_INFO + barcode.displayValue);
            }
        });
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {
        String codes = "";
        for (Barcode barcode : barcodes) {
            codes += barcode.displayValue + ", ";
        }
        final String finalCodes = codes;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Barcodes: " + finalCodes, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(getApplicationContext(), "Camera permission denied!", Toast.LENGTH_LONG).show();
        finish();
    }

    private void asyncUser(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = response.getJSONObject(API_KEY.RECORD);
                            Log.e("JSON: ", response.toString());
                            String deactivated = checkNull(jsonObject.getString(API_KEY.DEACTIVATED));
                            String status = checkNull(jsonObject.getString(API_KEY.STATUS));

                            if (deactivated == "1") {
                                showMsgAlert(getString(R.string.title), getString(R.string.deactivated_message));
                                return;
                            }
                            if (status == "0") {
                                showMsgConfirm(getString(R.string.title), getString(R.string.status_message));
                            } else {
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showMsgAlert(getString(R.string.login_title_error), getString(R.string.server_error));
                    }
                });
        DataParseRequestQueue.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private String checkNull(String value) {
        if (value.equals("null")) {
            value = "";
        }
        return value;
    }

}
