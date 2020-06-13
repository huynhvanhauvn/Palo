package com.sbro.palo.Fragments.ProfileFragment;

import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.User;

import okhttp3.MultipartBody;

public interface ProfileView {
    void showBackground(Background background);
    void showProfile(User user);
    void updatedAvatar(String result);
}
