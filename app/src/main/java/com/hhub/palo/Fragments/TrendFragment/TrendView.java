package com.hhub.palo.Fragments.TrendFragment;

import com.github.mikephil.charting.data.LineData;
import com.hhub.palo.Models.ArtistTrend;
import com.hhub.palo.Models.Background;
import com.hhub.palo.Models.Quote;
import com.hhub.palo.Models.TrendMovie;

import java.util.ArrayList;

public interface TrendView {
    void showBackground(Background background);
    void showTrend(ArrayList<TrendMovie> movies);
    void showChart(LineData lineData);
    void showHotCast(ArrayList<ArtistTrend> artistTrends);
    void showHostReview(ArrayList<Quote> quotes);
}
