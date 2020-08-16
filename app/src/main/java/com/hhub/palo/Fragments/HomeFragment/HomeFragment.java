package com.hhub.palo.Fragments.HomeFragment;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.hhub.palo.Activities.ArtistActivity.ArtistActivity;
import com.hhub.palo.Activities.MovieDetail.MovieDetailActivity;
import com.hhub.palo.Activities.MovieList.MovieListActivity;
import com.hhub.palo.Activities.SearchActivity.SearchActivity;
import com.hhub.palo.Adapter.RecentAdapter;
import com.hhub.palo.Adapter.SlideAdapter;
import com.hhub.palo.Adapter.SlideArtistAdapter;
import com.hhub.palo.Models.Artist;
import com.hhub.palo.Models.Background;
import com.hhub.palo.Models.Movie;
import com.hhub.palo.Models.Promotion;
import com.hhub.palo.R;
import com.hhub.palo.Utils.NetworkStateReceiver;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Locale;

public class HomeFragment extends Fragment implements HomeView, NetworkStateReceiver.NetworkStateReceiverListener {

    private ViewPager2 viewPager, pagerArtist;
    private Handler slideHandler = new Handler();
    private Handler slideHandlerArtist = new Handler();
    private RecyclerView recyclerView, recyclerBest;
    private ConstraintLayout imgLayout, layoutBirthday;
    private HomePresenter presenter;
    private ImageView btnMoreRecent, btnMoreBest;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView btnSearch;
    private NestedScrollView scrollView;
    private NetworkStateReceiver networkStateReceiver;
    private TextView txtRecent, txtBest;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = (ViewPager2) view.findViewById(R.id.home_viewPager);
        pagerArtist = (ViewPager2) view.findViewById(R.id.home_pager_artist);
        recyclerView = (RecyclerView) view.findViewById(R.id.home_recycler_recent);
        recyclerBest = (RecyclerView) view.findViewById(R.id.home_recycler_best);
        imgLayout = (ConstraintLayout) view.findViewById(R.id.home_layout);
        layoutBirthday = (ConstraintLayout) view.findViewById(R.id.home_birthday);
        btnMoreRecent = (ImageView) view.findViewById(R.id.home_img_more_recent);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_scroll);
        btnSearch = (ImageView) view.findViewById(R.id.home_btn_search);
        btnMoreBest = (ImageView) view.findViewById(R.id.home_img_more_best);
        scrollView = (NestedScrollView) view.findViewById(R.id.home_nested);
        txtRecent = (TextView) view.findViewById(R.id.home_txt_recent);
        txtBest = (TextView) view.findViewById(R.id.home_txt_best);

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

        presenter = new HomePresenter(this);
        presenter.showBackground(Locale.getDefault().getLanguage());

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

        pagerArtist.setClipToPadding(false);
        pagerArtist.setClipChildren(false);
        pagerArtist.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer transformerArtist = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.6f + r * 0.4f);
            }
        });
        pagerArtist.setPageTransformer(transformerArtist);
        pagerArtist.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandlerArtist.removeCallbacks(slideRunnableArtist);
                slideHandlerArtist.postDelayed(slideRunnableArtist,3000);
            }
        });

        presenter.showHeader();
        presenter.getArtistBirthday();
        presenter.getBestMovie();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        presenter.showRecentMovie();

        VideoOptions videoOptions = new VideoOptions.Builder()
                .setStartMuted(false)
                .build();

        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();

        AdLoader.Builder builder = new AdLoader.Builder(getContext(), getString(R.string.native_ads_id))
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        Log.d("hvhau","ad");
                        UnifiedNativeAdView adView = (UnifiedNativeAdView) view.findViewById(R.id.home_ads_recent);
                        TextView txtHeadline = (TextView) view.findViewById(R.id.home_ads_recent_title);
                        MediaView mediaContent = (MediaView) view.findViewById(R.id.home_ads_recent_media);
                        txtHeadline.setText(unifiedNativeAd.getHeadline());
                        adView.setHeadlineView(txtHeadline);
                        mediaContent.setMediaContent(unifiedNativeAd.getMediaContent());
                        adView.setMediaView(mediaContent);
                    }
                }).withNativeAdOptions(adOptions);
        AdLoader adLoader = builder.build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(slideRunnable);
        slideHandlerArtist.removeCallbacks(slideRunnableArtist);
    }

    @Override
    public void onResume() {
        super.onResume();
        slideHandler.postDelayed(slideRunnable,5000);
        slideHandlerArtist.postDelayed(slideRunnableArtist,3000);
    }

    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
        }
    };

    private Runnable slideRunnableArtist = new Runnable() {
        @Override
        public void run() {
            pagerArtist.setCurrentItem(pagerArtist.getCurrentItem()+1);
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
        txtRecent.setVisibility(View.VISIBLE);
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
        btnMoreRecent.setVisibility(View.VISIBLE);
        btnMoreRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MovieListActivity.class);
                intent.putExtra(MovieListActivity.TYPE,MovieListActivity.TYPE_RECENT);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showBestMovie(final ArrayList<Movie> movies) {
        txtBest.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerBest.setLayoutManager(layoutManager);
        RecentAdapter adapter = new RecentAdapter(getContext(),movies);
        recyclerBest.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecentAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                intent.putExtra("id",movies.get(position).getId());
                startActivity(intent);
            }
        });
        btnMoreBest.setVisibility(View.VISIBLE);
        btnMoreBest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MovieListActivity.class);
                intent.putExtra(MovieListActivity.TYPE,MovieListActivity.TYPE_BEST);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showArtistBirthday(final ArrayList<Artist> artists) {
        if(artists.size()>0) {
            layoutBirthday.setVisibility(View.VISIBLE);
            SlideArtistAdapter adapter = new SlideArtistAdapter(getContext(), artists, pagerArtist);
            pagerArtist.setAdapter(adapter);
            adapter.setOnItemClickListener(new SlideArtistAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, int position) {
                    Intent intent = new Intent(getContext(), ArtistActivity.class);
                    intent.putExtra(ArtistActivity.ID,artists.get(position).getId());
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void networkAvailable() {

    }

    @Override
    public void networkUnavailable() {
        Toast.makeText(getContext(),"No Connection",Toast.LENGTH_SHORT).show();
    }

    public void startNetworkBroadcastReceiver(Context currentContext) {
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener((NetworkStateReceiver.NetworkStateReceiverListener) currentContext);
        registerNetworkBroadcastReceiver(currentContext);
    }

    public void registerNetworkBroadcastReceiver(Context currentContext) {
        currentContext.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public void unregisterNetworkBroadcastReceiver(Context currentContext) {
        currentContext.unregisterReceiver(networkStateReceiver);
    }
}
