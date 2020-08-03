package com.hhub.palo.Activities.MyReviewActivity;

import com.hhub.palo.Models.Background;
import com.hhub.palo.Models.Quote;

import java.util.ArrayList;

public interface MyReviewView {
    void showReviewList(ArrayList<Quote> quotes);
    void showBackground(Background background);
}
