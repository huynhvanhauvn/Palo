package com.sbro.palo.Activities.LoginActivity;

import android.content.Context;
import android.content.res.Resources;

import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.User;

public interface LoginInterface {
    public void infoUser(String user, String pass, Context context, Resources resources);
    public void updateBackground(String language);
}
