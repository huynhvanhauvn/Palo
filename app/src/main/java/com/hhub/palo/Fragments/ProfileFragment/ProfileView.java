package com.hhub.palo.Fragments.ProfileFragment;

import com.hhub.palo.Models.Background;
import com.hhub.palo.Models.User;

public interface ProfileView {
    void showBackground(Background background);
    void showProfile(User user);
    void updatedAvatar(String result);
}
