package com.rupp.senghort.rupphr.helper;

import android.util.Log;

import com.rupp.senghort.rupphr.json.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KHEANG SENGHORT on 4/4/2018.
 */

public class DataParser {

    private JSONParser jsonParser;
    private static final String TOKEN_KEY = "token_key";
    private static final String TOKEN_VALUE = "aNeN2jFTvkKS2QBXKY";

    public static String BASE_URL = "https://rupphr.000webhostapp.com/human_resource/rest/";
    public static String PROFILE_INFO_LIST_URL = BASE_URL + "employee_api.php";
    public static String PROFILE_INFO_URL = BASE_URL + "profile_info.php?idx=";
    public static String PROFILE_URL = BASE_URL + "profile_api.php";
    public static String USER_INFO = BASE_URL + "user_info.php?idx=";
    public static String CHANGE_PASSWORD = BASE_URL + "change_password.php";
    public static String LOGIN = BASE_URL + "login.php";

    public DataParser() {
        jsonParser = new JSONParser();
    }

    /**
     * @return JSONObject
     */
    public JSONObject getProfileLists() {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        // params.add(new BasicNameValuePair(TOKEN_KEY, TOKEN_VALUE));
        String urlString = PROFILE_INFO_LIST_URL;
        Log.e("URL-getProfileLists->", urlString);
        JSONObject json = jsonParser.getJSONFromUrl(urlString, params);

        return json;
    }

    /**
     *
     * @param code
     * @return JSONObject
     */
    public JSONObject getProfile(String code) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(TOKEN_KEY, TOKEN_VALUE));
        params.add(new BasicNameValuePair(API_KEY.IDX, code));
        String urlString = PROFILE_URL;
        Log.e("URL-getProfile->", urlString);
        JSONObject json = jsonParser.getJSONFromUrl(urlString, params);

        return json;
    }

    /**
     *
     * @param code
     * @return JSONObject
     */
    public  JSONObject getEmployeeInfo(String code) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(TOKEN_KEY, TOKEN_VALUE));
        String urlString = PROFILE_URL + code;
        Log.e("URL-getProfile->", urlString);
        JSONObject json = jsonParser.getJSONFromUrl(urlString, params);

        return json;
    }

    /**
     *
     * @param username
     * @param password
     * @return JSONObject
     */

    public JSONObject login(String username, String password) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(API_KEY.USERNAME, username));
        params.add(new BasicNameValuePair(API_KEY.PASSWORD, password));

        JSONObject json = jsonParser.getJSONFromUrl(LOGIN, params);

        return json;
    }

    /**
     *
     * @param idx
     * @return JSONObject
     */
    public JSONObject getUserByUserId(String idx) {
        String urlString = USER_INFO + idx;
        Log.e("URL-getProfile->", urlString);
        JSONObject json = jsonParser.getJSONFromUrl(urlString);

        return json;
    }
}

// Implementation
/*
private class AsyncEmployee extends AsyncTask<String, String, JSONObject> {
        JSONObject json;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressView.start();
            progressView.setVisibility(View.VISIBLE);
            coordinatorLayout.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            progressView.start();
            progressView.setVisibility(View.GONE);
            coordinatorLayout.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            DataParser dataParser = new DataParser();
//            Bundle extras = getIntent().getExtras();
//            String code = extras.getString("qr_code"); // 1792100331, 1792100331
            json = dataParser.getEmployeeInfo("1792100331");
            if (json != null) {
                 Log.e("JSON: ", json.toString());
                try {
                    JSONObject jsonObject = json.getJSONObject(API_KEY.RECORD);
                    // Format date : 1979-10-25 -> 25 Oct, 1979
                    String dob = formatDateFromString("yyyy-dd-MM","dd MMM, yyyy",checkNull(jsonObject.getString(API_KEY.DOB)));
                    String hiredDate = formatDateFromString("yyyy-dd-MM","dd MMM, yyyy",checkNull(jsonObject.getString(API_KEY.HIREDDATE)));
                    String calculateWorkOfYear = calculateWorkOfYear(checkNull(jsonObject.getString(API_KEY.HIREDDATE)));
                    String contract = checkNull(jsonObject.getString(API_KEY.CONTRACT));
                    String employee = "";
                    if (contract == "0") {
                        employee = "ក្របខ័ណ្ឌ";
                    } else {
                        employee = "កិច្ចសន្យា";
                    }
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return json;
        }
    }
 */

