package com.sbro.palo.Activities.SignUp;

import com.sbro.palo.Models.User;

public class SignupPresenter implements SignupInterface {

    private SignupView signupView;

    public SignupPresenter(SignupView signupView) {
        this.signupView = signupView;
    }

    @Override
    public void signup(String username, String password) {
        signupView.signup(username, password);
    }
}
