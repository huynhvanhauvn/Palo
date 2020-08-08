package com.hhub.palo.Activities.MainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hhub.palo.Adapter.ViewPagerAdapter;
import com.hhub.palo.Fragments.HomeFragment.HomeFragment;
import com.hhub.palo.Fragments.ProfileFragment.ProfileFragment;
import com.hhub.palo.Fragments.TrendFragment.TrendFragment;
import com.hhub.palo.R;
import com.hhub.palo.Utils.NetworkStateReceiver;

public class MainActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    private HomeFragment homeFragment = new HomeFragment();
    private TrendFragment trendFragment = new TrendFragment();
    private ProfileFragment profileFragment = new ProfileFragment();
    private ViewPager viewPager;
    private NetworkStateReceiver networkStateReceiver;
    private int currentPage = 1;

    private BottomNavigationView bottomNavigationView;
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            viewPager.setCurrentItem(1);
                            return true;
                        case R.id.navigation_trend:
                            viewPager.setCurrentItem(0);
                            return true;
                        case R.id.navigation_profile:
                            viewPager.setCurrentItem(2);
                            return true;
                    }
                    return false;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("hvhau","onCreate");
        getSupportActionBar().hide();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        viewPager = (ViewPager) findViewById(R.id.main_pager);
        viewPager.setOffscreenPageLimit(3);

//        MobileAds.initialize(this, getString(R.string.native_ads_id));

        startNetworkBroadcastReceiver(this);
    }

    @Override
    protected void onPause() {
        Log.d("hvhau","onPause");
        unregisterNetworkBroadcastReceiver(this);
        currentPage = viewPager.getCurrentItem();
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d("hvhau","onResume");
        registerNetworkBroadcastReceiver(this);
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void networkAvailable() {
        Log.d("hvhau","onNet");
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(trendFragment);
        adapter.addFragment(homeFragment);
        adapter.addFragment(profileFragment);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentPage);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (viewPager.getCurrentItem()) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.navigation_trend);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
                        break;
                    default:
                        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void networkUnavailable() {
        Toast.makeText(getApplicationContext(),"No Connection",Toast.LENGTH_SHORT).show();
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
