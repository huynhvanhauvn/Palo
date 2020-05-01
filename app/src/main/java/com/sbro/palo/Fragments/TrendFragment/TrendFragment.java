package com.sbro.palo.Fragments.TrendFragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sbro.palo.Activities.MovieDetail.MovieDetailActivity;
import com.sbro.palo.Activities.SearchActivity.SearchActivity;
import com.sbro.palo.Adapter.RecentAdapter;
import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.DateView;
import com.sbro.palo.Models.Movie;
import com.sbro.palo.R;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Locale;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class TrendFragment extends Fragment implements TrendView {

    private LineChart lineChart;
    private TrendPresenter presenter;
    private ConstraintLayout layout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NestedScrollView scrollView;
    private ImageView btnSearch, img1, img2, img3;
    private RecyclerView recyclerTrend;
    private CardView card1, card2, card3;

    public TrendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trend, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lineChart = view.findViewById(R.id.trend_chart);
        layout = (ConstraintLayout) view.findViewById(R.id.trend_layout);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.trend_refresh);
        scrollView = (NestedScrollView) view.findViewById(R.id.trend_scroll);
        btnSearch = (ImageView) view.findViewById(R.id.trend_btn_search);
        img1 = (ImageView) view.findViewById(R.id.trend_img1);
        img2 = (ImageView) view.findViewById(R.id.trend_img2);
        img3 = (ImageView) view.findViewById(R.id.trend_img3);
        recyclerTrend = (RecyclerView) view.findViewById(R.id.trend_recycler_trend);
        card1 = (CardView) view.findViewById(R.id.trend_card1);
        card2 = (CardView) view.findViewById(R.id.trend_card2);
        card3 = (CardView) view.findViewById(R.id.trend_card3);

        scrollView.scrollTo(0,0);
        try {
            Field f = swipeRefreshLayout.getClass().getDeclaredField("mCircleView");
            f.setAccessible(true);
            ImageView img = (ImageView)f.get(swipeRefreshLayout);
            img.setAlpha(0.0f);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                btnSearch.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                    if(i1-i3>0) {
                        btnSearch.setVisibility(View.GONE);
                    }
                }
            });
        }
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        presenter = new TrendPresenter(this, getResources());
        presenter.getBackground();
        presenter.getTrend();

        lineChart.setDescription(null);
        lineChart.setNoDataText("Updating...");
        lineChart.getAxisLeft().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setEnabled(false);
        lineChart.animateXY(3000,3000);
        lineChart.setTouchEnabled(false);
    }

    @Override
    public void showBackground(Background background) {
        Glide.with(getContext()).load(background.getImage()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                layout.setBackground(resource);
            }
        });
    }

    @Override
    public void showTrend(final ArrayList<Movie> movies) {
        if(movies.size()>=3) {
            presenter.getTopData(movies.get(0).getId(),
                    movies.get(1).getId(),
                    movies.get(2).getId());
            Glide.with(getContext()).load(movies.get(0).getPoster()).into(img1);
            Glide.with(getContext()).load(movies.get(1).getPoster()).into(img2);
            Glide.with(getContext()).load(movies.get(2).getPoster()).into(img3);

            card1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                    intent.putExtra("id",movies.get(0).getId());
                    startActivity(intent);
                }
            });
            card2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                    intent.putExtra("id",movies.get(1).getId());
                    startActivity(intent);
                }
            });
            card3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                    intent.putExtra("id",movies.get(2).getId());
                    startActivity(intent);
                }
            });

            final ArrayList<Movie> subMovies = new ArrayList<>(movies.subList(3,movies.size()));
            if(subMovies.size()>0) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerTrend.setLayoutManager(layoutManager);
                RecentAdapter adapter = new RecentAdapter(getContext(),subMovies);
                recyclerTrend.setAdapter(adapter);
                adapter.setOnItemClickListener(new RecentAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(View view, int position) {
                        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                        intent.putExtra("id",subMovies.get(position).getId());
                        startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public void showChart(LineData lineData) {
        lineChart.setData(lineData);
        lineChart.invalidate();
    }
}
