package com.example.belablok.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.belablok.R;
import com.example.belablok.adapteri.pagerAdapterPovijest;
import com.example.belablok.fragments.FragmentGames;
import com.example.belablok.fragments.FragmentLegs;
import com.example.belablok.fragments.FragmentUpisi;
import com.example.belablok.interfaces.IPovijestActivity;

public class PovijestActivity extends AppCompatActivity implements IPovijestActivity{

    //private pagerAdapterPovijest mPagerAdapterPovijest;
    //private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_povijest);

        init();
    }

    private void init(){
        FragmentGames fragmentGames = new FragmentGames();
        doFragmentTransaction(fragmentGames, "games", false, "");
    }

    private void doFragmentTransaction(Fragment fragment, String tag, boolean addToBackStack, String message){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(!message.equals("")){
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.intent_message), message);
            fragment.setArguments(bundle);
        }

        transaction.replace(R.id.main_container, fragment, tag);

        if(addToBackStack){
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    @Override
    public void inflateFragment(int nFragment, String message) {
        switch (nFragment){
            case 1:
                FragmentLegs fragmentLegs = new FragmentLegs();
                doFragmentTransaction(fragmentLegs, "Legs", true, message);
                break;

            case 2:
                FragmentUpisi fragmentUpisi = new FragmentUpisi();
                doFragmentTransaction(fragmentUpisi, "Upisi", true, message);
                break;
        }
    }

}
