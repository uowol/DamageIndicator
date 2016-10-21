package com.example.chan_woo_kim.damageindicator;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Chan_Woo_Kim on 2016-09-23.
 */
public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);
    }
}
