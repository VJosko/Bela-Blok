package com.vudrag.belablok.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.vudrag.belablok.R;
import com.vudrag.belablok.fragments.FragmentGames;
import com.vudrag.belablok.fragments.FragmentLegs;
import com.vudrag.belablok.fragments.FragmentUpisi;
import com.vudrag.belablok.interfaces.IPovijestActivity;

public class PovijestActivity extends AppCompatActivity implements IPovijestActivity{

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private ImageButton ibtn_natrag;
    private TextView tvNaslov;
    private String sTema;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        sTema = mPreferences.getString("tema", "tamna");
        if(sTema.equals("tamna")){
            setTheme(R.style.AppThemeDark);
        }
        else if(sTema.equals("svjetla")){
            setTheme(R.style.AppThemeLight);
        }
        setContentView(R.layout.activity_povijest);

        tvNaslov = findViewById(R.id.tv_naslov);

        init();

        ibtn_natrag = findViewById(R.id.ibtn_natrag);
        ibtn_natrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentGames test = (FragmentGames) getSupportFragmentManager().findFragmentByTag("games");
                if(test != null && test.isVisible()){
                    finish();
                }
                else {
                    getSupportFragmentManager().popBackStack();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sTema != mPreferences.getString("tema", "tamna")){
            recreate();
        }
    }

    private void init(){
        FragmentGames fragmentGames = new FragmentGames();
        doFragmentTransaction(fragmentGames, "games", false, "");
    }

    private void doFragmentTransaction(Fragment fragment, String tag, boolean addToBackStack, String message){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_right);

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
    public void setNaslov(String sNaslov) {
        tvNaslov.setText(sNaslov);
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
