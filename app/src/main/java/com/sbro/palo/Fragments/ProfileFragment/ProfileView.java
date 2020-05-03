package com.sbro.palo.Fragments.ProfileFragment;

import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.User;

public interface ProfileView {
    void showBackdround(Background background);
    void showProfile(User user);
}
