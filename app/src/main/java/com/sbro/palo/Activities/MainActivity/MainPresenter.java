package com.sbro.palo.Activities.MainActivity;

import com.sbro.palo.Models.Background;

import rx.Observable;

public class MainPresenter {
    private MainView view;

    public MainPresenter(MainView view) {
        this.view = view;
    }
}
