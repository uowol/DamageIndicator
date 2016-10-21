package com.example.chan_woo_kim.damageindicator;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.chan_woo_kim.damageindicator.backup.*;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.app.FragmentManager mFragmentManager = getFragmentManager();
        MainFragment mFragment = new MainFragment();
        FragmentTransaction mTransaction = mFragmentManager.beginTransaction();

        mTransaction.add(R.id.root,mFragment);
        mTransaction.commit();
    }
}
