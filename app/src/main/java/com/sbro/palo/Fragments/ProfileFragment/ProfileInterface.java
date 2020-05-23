package com.sbro.palo.Fragments.ProfileFragment;

import okhttp3.MultipartBody;

public interface ProfileInterface {
    void getBackground();
    void getProfile(String id);
    void updateAvatar(MultipartBody.Part body, String id, String oldFile);
}
