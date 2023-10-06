package com.rupp.senghort.rupphr.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rey.material.widget.ProgressView;
import com.rupp.senghort.rupphr.R;
import com.rupp.senghort.rupphr.helper.API_KEY;
import com.rupp.senghort.rupphr.helper.DataParseRequestQueue;
import com.rupp.senghort.rupphr.helper.DataParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout linearLayout;
    private ProgressView progressView;
    private EditText txtCurrentPassword;
    private EditText txtNewPassword;
    private EditText txtReTypePassword;
    private Button buttonSaveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor((Color.parseColor("#FFFFFF")));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        progressView = (ProgressView)findViewById(R.id.progressView);
        txtNewPassword = (EditText)findViewById(R.id.txtNewPassword);
        buttonSaveChanges = (Button)findViewById(R.id.btnChangePassword);
        linearLayout = (LinearLayout)findViewById(R.id.changePasswordLayout);
        txtCurrentPassword = (EditText)findViewById(R.id.txtCurrentPassword);
        txtReTypePassword = (EditText)findViewById(R.id.txtReTypeNewPassword);

        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard(view);
                return false;
            }
        });
        buttonSaveChanges.setOnClickListener(this);
    }

    private void saveChanges(View view) {
        final String newPassword = txtNewPassword.getText().toString();
        final String reTypePassword = txtReTypePassword.getText().toString();
        final String currentPassword = txtCurrentPassword.getText().toString();

        if (currentPassword.isEmpty()) {
            txtCurrentPassword.setError(getString(R.string.require_current_password));
            return;
        }
        if (newPassword.isEmpty()) {
            txtNewPassword.setError(getString(R.string.require_new_password));
            return;
        }
        if (reTypePassword.isEmpty()) {
            txtReTypePassword.setError(getString(R.string.require_re_type_new_password));
            return;
        }

//        Bundle extras = getIntent().getExtras();
//        String employeeId = extras.getString("employeeId");
        syncUpdateUser(DataParser.CHANGE_PASSWORD, "68",
                currentPassword, newPassword, reTypePassword);
    }

    @Override
    public void onClick(View view) {
        saveChanges(view);
    }

    private void syncUpdateUser(String url, final String idx, final String currentPassword,
                                final String newPassword, final String retypePassword) {
        progressView.start();
        linearLayout.setVisibility(View.GONE);
        progressView.setVisibility(View.VISIBLE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            progressView.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                            jsonObject = new JSONObject(response.toString());
                            Log.e("[DATA]: ", response.toString());
                            int statusCode = jsonObject.getInt(API_KEY.CODE_SUCCESS);
                            if (statusCode == 1) {
                                finish();
                            } else {
                                progressView.setVisibility(View.GONE);
                                linearLayout.setVisibility(View.VISIBLE);
                                String message = jsonObject.getString(API_KEY.MESSAGE);
                                showMsgAlert(getString(R.string.login_title_error), message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressView.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                        showMsgAlert(getString(R.string.login_title_error), getString(R.string.server_error));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String>  params = new HashMap<String, String>();
                params.put(API_KEY.IDX, idx);
                params.put(API_KEY.CURRENT_PASSWORD, currentPassword);
                params.put(API_KEY.NEW_PASSWORD, newPassword);
                params.put(API_KEY.RETYPE_PASSWORD, retypePassword);

                return params;
            }
        };
        DataParseRequestQueue.getInstance(this).addToRequestQueue(postRequest);
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

    protected void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return  true;
        }
        return  super.onOptionsItemSelected(item);
    }
}
