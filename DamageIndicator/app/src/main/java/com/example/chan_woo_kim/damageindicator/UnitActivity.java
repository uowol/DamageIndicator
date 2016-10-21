package com.example.chan_woo_kim.damageindicator;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * Created by Chan_Woo_Kim on 2016-09-26.
 */
public class UnitActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit);

        android.app.FragmentManager mFragmentManager = getFragmentManager();
        UnitFragment mFragment = new UnitFragment();
        FragmentTransaction mTransaction = mFragmentManager.beginTransaction();

        mTransaction.add(R.id.root,mFragment);
        mTransaction.commit();

    }
}
