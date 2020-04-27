package com.sbro.palo.Activities.LoginActivity;

import com.sbro.palo.Models.User;

import dagger.Component;

@Component
public interface LoginComponent {
    User initUser();
}
