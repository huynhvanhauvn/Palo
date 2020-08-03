package com.hhub.palo.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

public class MyConnection {
    public static boolean isNetworkConnected(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
