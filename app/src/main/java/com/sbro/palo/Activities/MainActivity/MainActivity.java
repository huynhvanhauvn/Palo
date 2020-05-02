package com.sbro.palo.Activities.MainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sbro.palo.Adapter.ViewPagerAdapter;
import com.sbro.palo.Fragments.HomeFragment.HomeFragment;
import com.sbro.palo.Fragments.ProfileFragment.ProfileFragment;
import com.sbro.palo.Fragments.TrendFragment.TrendFragment;
import com.sbro.palo.R;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment = new HomeFragment();
    private TrendFragment trendFragment = new TrendFragment();
    private ProfileFragment profileFragment = new ProfileFragment();
    private ViewPager viewPager;

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

        getSupportActionBar().hide();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        viewPager = (ViewPager) findViewById(R.id.main_pager);

        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(trendFragment);
        adapter.addFragment(homeFragment);
        adapter.addFragment(profileFragment);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
