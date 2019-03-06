package com.argb.mtgapp.userInfo;

import android.os.Bundle;

import com.argb.mtgapp.R;
import com.argb.mtgapp.userInfo.adapters.ViewPagerAdapter;
import com.argb.mtgapp.userInfo.fragments.FourFragment;
import com.argb.mtgapp.userInfo.fragments.OneFragment;
import com.argb.mtgapp.userInfo.fragments.ThreeFragment;
import com.argb.mtgapp.userInfo.fragments.TwoFragment;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class UserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Title toolbar");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "Personal");
        adapter.addFragment(new TwoFragment(), "Games");
        adapter.addFragment(new ThreeFragment(), "Decks");
        adapter.addFragment(new FourFragment(), "Friends");
        viewPager.setAdapter(adapter);
    }
}
