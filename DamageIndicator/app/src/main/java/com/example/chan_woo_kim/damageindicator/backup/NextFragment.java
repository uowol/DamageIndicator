package com.example.chan_woo_kim.damageindicator.backup;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.example.chan_woo_kim.damageindicator.R;

/**
 * Created by Chan_Woo_Kim on 2016-09-26.
 */
public class NextFragment extends Fragment {

    private int ID;

    public NextFragment(int id) {
        this.ID = id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ID==GlobalValues.MAINFRAGMENT){
            Log.e("Log","MainFragment");
//            getFragmentManager().beginTransaction().replace(R.id.root, new MainPreferenceFragment()).commit();
        } else if(ID==GlobalValues.ANIMFRAGMENT){
            Log.e("Log","AnimFragment");
//            getFragmentManager().beginTransaction().replace(R.id.root, new AnimFragment()).commit();
        } else if(ID==GlobalValues.SIZEFRAGMENT){
            Log.e("Log","SizeFragment");
//            getFragmentManager().beginTransaction().add(R.id.root, new MainPreferenceFragment()).commit();
        } else if(ID==GlobalValues.UNITFRAGMENT){
            Log.e("Log","UnitFragment");
//            getFragmentManager().beginTransaction().add(R.id.root, new MainPreferenceFragment()).commit();
        }
    }
}
