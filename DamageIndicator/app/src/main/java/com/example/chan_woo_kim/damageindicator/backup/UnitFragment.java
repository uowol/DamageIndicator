package com.example.chan_woo_kim.damageindicator.backup;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.example.chan_woo_kim.damageindicator.R;

/**
 * Created by Chan_Woo_Kim on 2016-09-26.
 */
public class UnitFragment extends PreferenceFragment {
    private Preference.OnPreferenceClickListener onPreferenceClickListener = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            if (preference instanceof Preference) {
                if(preference.getKey().equals("size")){
                    newWindow(GlobalValues.SIZEFRAGMENT);

                } else if(preference.getKey().equals("anim")){
                    newWindow(GlobalValues.ANIMFRAGMENT);

                } else if(preference.getKey().equals("unit")){
                    newWindow(GlobalValues.UNITFRAGMENT);

                }
            }
            return true;
        }
    };

    private void newWindow(int id){
        android.app.FragmentManager mFragmentManager = getFragmentManager();
        NextFragment mFragment = new NextFragment(id);
        FragmentTransaction mTransaction = mFragmentManager.beginTransaction();

        mTransaction.add(R.id.root,mFragment);
        mTransaction.commit();
    }

    private void setOnPreferenceClick(Preference mPreference) {
        mPreference.setOnPreferenceClickListener(onPreferenceClickListener);
        onPreferenceClickListener.onPreferenceClick(mPreference);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_main);

        setOnPreferenceClick(findPreference("color"));
        setOnPreferenceClick(findPreference("size"));
        setOnPreferenceClick(findPreference("anim"));
        setOnPreferenceClick(findPreference("unit"));
    }
}
