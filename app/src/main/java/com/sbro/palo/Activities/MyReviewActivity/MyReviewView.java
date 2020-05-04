package com.sbro.palo.Activities.MyReviewActivity;

import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.Quote;

import java.util.ArrayList;

public interface MyReviewView {
    void showReviewList(ArrayList<Quote> quotes);
    void showBackground(Background background);
}
