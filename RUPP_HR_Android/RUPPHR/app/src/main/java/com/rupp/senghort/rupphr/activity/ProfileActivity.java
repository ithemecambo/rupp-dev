package com.rupp.senghort.rupphr.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.rey.material.widget.ProgressView;
import com.rupp.senghort.rupphr.R;
import com.rupp.senghort.rupphr.application.BaseActivity;
import com.rupp.senghort.rupphr.helper.API_KEY;
import com.rupp.senghort.rupphr.helper.DataParseRequestQueue;
import com.rupp.senghort.rupphr.helper.DataParser;
import com.rupp.senghort.rupphr.helper.LanguageHelper;
import com.rupp.senghort.rupphr.helper.LocaleHelper;
import com.rupp.senghort.rupphr.utils.DividerItemDecoration;
import com.rupp.senghort.rupphr.adapter.ProfileAdapter;
import com.rupp.senghort.rupphr.model.User;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Key;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    private CoordinatorLayout coordinatorLayout = null;
    private List<User> profiles = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProfileAdapter adapter;
    private boolean isChicked = false;
    private ProgressView progressView;
    private CircleImageView thumbNail;
    private String employeeId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.layoutProfile);
        thumbNail = (CircleImageView)findViewById(R.id.thumbNailProfile);
        progressView = (ProgressView)findViewById(R.id.progressView);
        recyclerView = (RecyclerView)findViewById(R.id.rvProfile);

        toolbarTextAppernce();
        // new AsyncEmployee().execute();
        Bundle extras = getIntent().getExtras();
        String code = extras.getString("qr_code"); // 1792100331
        String url = DataParser.PROFILE_INFO_URL + code;
        asyncEmployee(url);

//        LanguageHelper.setAppLocale("km", this);
        LocaleHelper.setLocale(ProfileActivity.this, "km");

        //It is required to recreate the activity to reflect the change in UI.
//        recreate();

        adapter = new ProfileAdapter(profiles);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.item_seperator)));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_khmer) {
            invalidateOptionsMenu();
        }
//        if (item.getItemId() == R.id.action_edit) {
//            Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
//            intent.putExtra("employeeId", employeeId);
//            startActivity(intent);
//        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            return  true;
        }
        return  super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(isChicked){
            menu.getItem(0).setIcon(R.mipmap.flag_english);
            MenuItem mi = menu.add("");
            mi.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            isChicked = false;
            LocaleHelper.setLocale(ProfileActivity.this, "en");
//            recreate();
//                 adapter.notifyDataSetChanged();
//                LanguageHelper.setAppLocale("en", this);
//                Toast.makeText(this, "Do you want to change language?", Toast.LENGTH_SHORT).show();
        } else{
            menu.getItem(0).setIcon(R.mipmap.flag_khmer);
            isChicked = true;
            LocaleHelper.setLocale(ProfileActivity.this, "km");
//            recreate();
//                LanguageHelper.setAppLocale("km", this);
//                 adapter.notifyDataSetChanged();
//                Toast.makeText(this, "តើអ្នកចង់ផ្លាស់ប្តូរភាសាមែនឬ?", Toast.LENGTH_SHORT).show();
        }
        return super.onPrepareOptionsMenu(menu);
    }
    // https://github.com/vinaykashyapts/cl/tree/master/cllanguagetranslation
    private void asyncEmployee(String url) {
        progressView.start();
        progressView.setVisibility(View.VISIBLE);
        coordinatorLayout.setVisibility(View.GONE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressView.setVisibility(View.GONE);
                        coordinatorLayout.setVisibility(View.VISIBLE);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = response.getJSONObject(API_KEY.RECORD);
                            Log.e("JSON: ", response.toString());
                            String dob = formatDateFromString("yyyy-MM-dd", "dd MMM, yyyy", checkNull(jsonObject.getString(API_KEY.DOB)));
                            String hiredDate = formatDateFromString("yyyy-MM-dd", "dd MMM, yyyy", checkNull(jsonObject.getString(API_KEY.HIREDDATE)));
                            String calculateWorkOfYear = calculateWorkOfYear(checkNull(jsonObject.getString(API_KEY.HIREDDATE)));
                            String contract = checkNull(jsonObject.getString(API_KEY.CONTRACT));
                            String employee = "";
                            if (contract == "0") {
                                employee = "ក្របខ័ណ្ឌ";
                            } else {
                                employee = "កិច្ចសន្យា";
                            }
                            employeeId = checkNull(jsonObject.getString(API_KEY.ID));
                            profiles.add(new User("", getStringResourceByName("header_code"), checkNull(jsonObject.getString(API_KEY.CODE))));
                            profiles.add(new User("", getStringResourceByName("header_name"), checkNull(jsonObject.getString(API_KEY.NAMEKH))));
                            profiles.add(new User("", getStringResourceByName("header_jobTitle"), checkNull(jsonObject.getString(API_KEY.JOBTITLE))));
                            profiles.add(new User("", getStringResourceByName("header_deptNo"), checkNull(jsonObject.getString(API_KEY.DEPTNO))));
                            profiles.add(new User("", getStringResourceByName("header_dob"), dob));
                            profiles.add(new User("", getStringResourceByName("header_sex"), checkNull(jsonObject.getString(API_KEY.SEX))));
                            profiles.add(new User("", getStringResourceByName("header_phone"), checkNull(jsonObject.getString(API_KEY.PHONE))));
                            profiles.add(new User("", getStringResourceByName("header_kamPrak"), checkNull(jsonObject.getString(API_KEY.KAMPRAK))));
                            profiles.add(new User("", getStringResourceByName("header_spouses"), checkNull(jsonObject.getString(API_KEY.SPOUSES))));
                            profiles.add(new User("", getStringResourceByName("header_children"), checkNull(jsonObject.getString(API_KEY.CHILDREN))));
                            profiles.add(new User("", getStringResourceByName("header_hiredDate"), hiredDate));
                            profiles.add(new User("", getStringResourceByName("header_contract"), employee));
                            profiles.add(new User("", getStringResourceByName("header_deactivated"), calculateWorkOfYear));

                            Glide.with(getApplicationContext())
                                    .load(checkNull(jsonObject.getString(API_KEY.PHOTO)))
                                    .placeholder(R.mipmap.avatar)
                                    .error(R.mipmap.avatar)
                                    .into(thumbNail);

                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        progressView.setVisibility(View.VISIBLE);
                        coordinatorLayout.setVisibility(View.GONE);
                    }
                });
        DataParseRequestQueue.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private void toolbarTextAppernce() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }

    private String getStringResourceByName(String resourceName) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(resourceName, "string", packageName);
        return getString(resId);
    }

    private String calculateWorkOfYear(String inputDate) {
        String result = "";

        String oldYear = formatDateFromString("yyyy-dd-MM", "yyyy", inputDate);
        DateFormat df = new SimpleDateFormat("yyyy");
        String newYear = df.format(new Date(System.currentTimeMillis()));

        int oldYearPaseInt = Integer.parseInt(oldYear);
        int newYearPaseInt = Integer.parseInt(newYear);
        int currentYear = newYearPaseInt - oldYearPaseInt;

        result = ""+ currentYear;

        return result;
    }

    private String formatDateFromString(String inputFormat, String outputFormat, String inputDate){
        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);
        } catch (ParseException e) {
            Log.e("[ERROR]: ", "ParseException - dateFormat");
        }
        return outputDate;
    }

    private String checkNull(String value) {
        if (value.equals("null")) {
            value = "";
        }
        return value;
    }

}
