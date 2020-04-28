package com.sbro.palo.Fragments.HomeFragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sbro.palo.Activities.MovieDetail.MovieDetailActivity;
import com.sbro.palo.Activities.MovieList.MovieListActivity;
import com.sbro.palo.Adapter.RecentAdapter;
import com.sbro.palo.Adapter.SlideAdapter;
import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.Movie;
import com.sbro.palo.Models.Promotion;
import com.sbro.palo.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements HomeView {

    private ViewPager2 viewPager;
    private Handler slideHandler = new Handler();
    private RecyclerView recyclerView;
    private ConstraintLayout imgLayout;
    private HomePresenter presenter;
    private ImageView imageView;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = (ViewPager2) view.findViewById(R.id.home_viewPager);
        recyclerView = (RecyclerView) view.findViewById(R.id.home_recycler_recent);
        imgLayout = (ConstraintLayout) view.findViewById(R.id.home_layout);
        imageView = (ImageView) view.findViewById(R.id.home_img_more_recent);

        presenter = new HomePresenter(this);
        presenter.showBackground();

        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(3);
        viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewPager.setPageTransformer(transformer);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(slideRunnable);
                slideHandler.postDelayed(slideRunnable,5000);
            }
        });

        presenter.showHeader();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        presenter.showRecentMovie();
    }

    @Override
    public void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(slideRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        slideHandler.postDelayed(slideRunnable,5000);
    }

    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
        }
    };

    @Override
    public void showHeader(final ArrayList<Promotion> promotions) {
        SlideAdapter adapter = new SlideAdapter(getContext(),promotions,viewPager);
        viewPager.setAdapter(adapter);
        adapter.setOnItemClickListener(new SlideAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                intent.putExtra("id",promotions.get(position).getIdMovie());
                startActivity(intent);
            }
        });
    }

    @Override
    public void showBackground(Background background) {
        Glide.with(getContext()).load(background.getImage()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                imgLayout.setBackground(resource);
            }
        });
    }

    @Override
    public void showRecentMovie(final ArrayList<Movie> movies) {
        RecentAdapter adapter = new RecentAdapter(getContext(),movies);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecentAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                intent.putExtra("id",movies.get(position).getId());
                startActivity(intent);
            }
        });
        imageView.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MovieListActivity.class);
                intent.putExtra(MovieListActivity.TYPE,MovieListActivity.TYPE_RECENT);
                startActivity(intent);
            }
        });
    }
}
