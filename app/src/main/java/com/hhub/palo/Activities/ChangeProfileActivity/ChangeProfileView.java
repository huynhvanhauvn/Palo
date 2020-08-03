package com.hhub.palo.Activities.ChangeProfileActivity;

import com.hhub.palo.Models.Background;
import com.hhub.palo.Models.User;

public interface ChangeProfileView {
    void showProfile(User user);
    void updateSuccess();
    void showBackground(Background background);
}
