package com.example.belablok.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.belablok.R;
import com.example.belablok.adapteri.pagerAdapterPovijest;
import com.example.belablok.fragments.FragmentGames;
import com.example.belablok.fragments.FragmentLegs;
import com.example.belablok.fragments.FragmentUpisi;

public class PovijestActivity extends AppCompatActivity {

    private pagerAdapterPovijest mPagerAdapterPovijest;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_povijest);

        mPagerAdapterPovijest = new pagerAdapterPovijest(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        pagerAdapterPovijest adapter = new pagerAdapterPovijest(getSupportFragmentManager());
        adapter.addFragment(new FragmentGames(), "Games");
        adapter.addFragment(new FragmentLegs(), "Legs");
        adapter.addFragment(new FragmentUpisi(), "Upisi");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber);
    }
}
