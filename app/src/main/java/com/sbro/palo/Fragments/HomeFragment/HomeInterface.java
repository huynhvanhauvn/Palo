package com.sbro.palo.Fragments.HomeFragment;

import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.Movie;
import com.sbro.palo.Models.Promotion;

import java.util.ArrayList;

public interface HomeInterface {
    void showHeader();
    void showBackground(String language);
    void showRecentMovie();
    void getBestMovie();
}
