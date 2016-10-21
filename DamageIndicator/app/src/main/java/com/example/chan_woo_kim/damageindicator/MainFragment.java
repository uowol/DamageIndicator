package com.example.chan_woo_kim.damageindicator;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by Chan_Woo_Kim on 2016-09-26.
 */
public class MainFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(R.id.root, new MainPreferenceFragment()).commit();
    }
}
