package com.rupp.senghort.rupphr.helper;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by KHEANG SENGHORT on 4/26/2018.
 */

public class DataParseRequestQueue {

    private static DataParseRequestQueue dataParseRequestQueue;
    private RequestQueue requestQueue;
    private static Context context;

    private DataParseRequestQueue(Context ctx) {
        context = ctx;
        requestQueue = getRequestQueue();
    }
    public static synchronized DataParseRequestQueue getInstance(Context context) {
        if (dataParseRequestQueue == null) {
            dataParseRequestQueue = new DataParseRequestQueue(context);
        }
        return dataParseRequestQueue;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
