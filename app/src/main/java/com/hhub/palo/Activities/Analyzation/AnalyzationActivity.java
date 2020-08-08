package com.hhub.palo.Activities.Analyzation;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hhub.palo.Models.DateView;
import com.hhub.palo.Models.Table;
import com.hhub.palo.R;

import java.util.ArrayList;

public class AnalyzationActivity extends AppCompatActivity implements AnalyzeView {

    private PieChart pieChartNation;
    private AnalyzePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyzation);
        getSupportActionBar().hide();

        pieChartNation = (PieChart) findViewById(R.id.analyze_pie_nation);

        presenter = new AnalyzePresenter(this);
        presenter.getNationData();
    }

    @Override
    public void showNationChart(final ArrayList<Table> tables, final float total) {
        pieChartNation.setTransparentCircleColor(Color.TRANSPARENT);
        pieChartNation.setHoleColor(Color.TRANSPARENT);
        pieChartNation.setEntryLabelColor(Color.WHITE);
        final PieDataSet pieDataSet = new PieDataSet(getEntries(tables, total), "");
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(11f);
        pieData.setValueFormatter(new PercentFormatter());
        pieChartNation.setData(pieData);
        pieChartNation.invalidate();
        pieChartNation.setEntryLabelColor(Color.GRAY);
        pieChartNation.setEntryLabelTextSize(12f);
        pieChartNation.setDescription(null);
        pieChartNation.animateXY(3000,3000);
        pieChartNation.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                PieEntry pieEntry = (PieEntry) e;
                Toast.makeText(getApplicationContext(),pieEntry.getLabel(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private ArrayList<PieEntry> getEntries(ArrayList<Table> tables, float total) {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < tables.size(); i++) {
            pieEntries.add(new PieEntry(Float.parseFloat(tables.get(i).getValue()) * 100 / total,
                    tables.get(i).getKey()));
        }
        return pieEntries;
    }
}
