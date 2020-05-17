package com.sbro.palo.Fragments.TrendFragment;

import com.github.mikephil.charting.data.LineData;
import com.sbro.palo.Models.ArtistTrend;
import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.Movie;
import com.sbro.palo.Models.Quote;
import com.sbro.palo.Models.TrendMovie;

import java.util.ArrayList;

public interface TrendView {
    void showBackground(Background background);
    void showTrend(ArrayList<TrendMovie> movies);
    void showChart(LineData lineData);
    void showHotCast(ArrayList<ArtistTrend> artistTrends);
    void showHostReview(ArrayList<Quote> quotes);
}
