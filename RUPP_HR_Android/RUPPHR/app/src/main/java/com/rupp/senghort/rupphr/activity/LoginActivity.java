package com.rupp.senghort.rupphr.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.rupp.senghort.rupphr.application.BaseActivity;
import com.rupp.senghort.rupphr.helper.API_KEY;
import com.rupp.senghort.rupphr.helper.DataParseRequestQueue;
import com.rupp.senghort.rupphr.helper.DataParser;
import com.rupp.senghort.rupphr.helper.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ProgressView progressView;
    private LinearLayout loginLayout;
    private EditText txtUsername;
    private EditText txtPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressView = (ProgressView) findViewById(R.id.progressView);
        loginLayout = (LinearLayout) findViewById(R.id.loginLayout);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        buttonLogin = (Button) findViewById(R.id.btnLogin);

        loginLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard(view);
                return false;
            }
        });
        buttonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        checkLogin(view);
    }

    public void checkLogin(View arg0) {
        final String username = txtUsername.getText().toString();
        final String password = txtPassword.getText().toString();

        if (username.isEmpty()) {
            txtUsername.setError(getString(R.string.require_username));
            return;
        }
        if (password.isEmpty()) {
            txtPassword.setError(getString(R.string.require_password));
            return;
        }
        // Callback function
        loginUser(DataParser.LOGIN, username, password);
    }

    private void loginUser(String url, final String username, final String password) {
        progressView.start();
        loginLayout.setVisibility(View.GONE);
        progressView.setVisibility(View.VISIBLE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        Log.e("[RES]: ", response.toString());
                        try {
                            progressView.setVisibility(View.GONE);
                            loginLayout.setVisibility(View.VISIBLE);
                            jsonObject = new JSONObject(response.toString());
                            Log.e("[DATA]: ", response.toString());
                            int statusCode = jsonObject.getInt(API_KEY.CODE_SUCCESS);
                            Log.e("[statusCode]: ", statusCode+"");
                            if (statusCode == 1) {
                                JSONObject json = jsonObject.getJSONObject(API_KEY.DATA);
                                String employeeId = json.getString(API_KEY.EMPLOYEEID);
                                // Finish login activity
                                finish();
                                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                                intent.putExtra("qr_code", employeeId);
                                startActivity(intent);
                            } else {
                                progressView.setVisibility(View.GONE);
                                loginLayout.setVisibility(View.VISIBLE);
                                String message = jsonObject.getString(API_KEY.MESSAGE);
                                showMsgAlert(getString(R.string.login_title_error), message);
                            }
                        } catch (JSONException e) {
                            Log.e("[ERROR]: ", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressView.setVisibility(View.GONE);
                        loginLayout.setVisibility(View.VISIBLE);
                        showMsgAlert(getString(R.string.login_title_error), getString(R.string.server_error));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String>  params = new HashMap<String, String>();
                params.put(API_KEY.USERNAME, username);
                params.put(API_KEY.PASSWORD, password);

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

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 4) {
            return true;
        }
        return false;
    }
}
