package com.sbro.palo.Activities.ChangeProfileActivity;

import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.User;

public interface ChangeProfileView {
    void showProfile(User user);
    void updateSuccess();
    void showBackground(Background background);
}
