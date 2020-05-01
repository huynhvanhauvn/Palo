package com.sbro.palo.Fragments.TrendFragment;

import com.github.mikephil.charting.data.LineData;
import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.Movie;

import java.util.ArrayList;

public interface TrendView {
    void showBackground(Background background);
    void showTrend(ArrayList<Movie> movies);
    void showChart(LineData lineData);
}
