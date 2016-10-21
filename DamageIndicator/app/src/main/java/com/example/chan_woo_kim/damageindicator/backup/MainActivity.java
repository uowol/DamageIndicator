package com.example.chan_woo_kim.damageindicator.backup;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.example.chan_woo_kim.damageindicator.R;

public class MainActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, Splash.class));

        android.app.FragmentManager mFragmentManager = getFragmentManager();
        NextFragment mFragment = new NextFragment(GlobalValues.MAINFRAGMENT);
        FragmentTransaction mTransaction = mFragmentManager.beginTransaction();

        mTransaction.add(R.id.root,mFragment);
        mTransaction.commit();
    }

}
