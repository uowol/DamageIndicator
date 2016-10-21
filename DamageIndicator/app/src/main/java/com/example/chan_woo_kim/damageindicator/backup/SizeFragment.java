package com.example.chan_woo_kim.damageindicator.backup;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chan_woo_kim.damageindicator.R;

/**
 * Created by Chan_Woo_Kim on 2016-09-26.
 */
public class SizeFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.splash, null);
        return mView;
    }
}
