package com.example.chan_woo_kim.damageindicator;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Chan_Woo_Kim on 2016-09-26.
 */
public class SizeActivity extends Activity {
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size);

        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        editor = pref.edit();

        String font = pref.getString("font","hachicro.TTF");
        String color = pref.getString("color","#f03232");
        int mSize = pref.getInt("size",20);

        final TextView size = (TextView) findViewById(R.id.tv_size);
        size.setTextColor(Color.parseColor(color));
        size.setTypeface(Typeface.createFromAsset(getAssets(), font));
        size.setTextSize(mSize);
        final TextView sizeDP = (TextView) findViewById(R.id.tv_size_dp);
        sizeDP.setText(mSize+"dp");
        SeekBar seekBar = (SeekBar)findViewById(R.id.sb_size);
        seekBar.setProgress(pref.getInt("size",20)-20);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                sizeDP.setText((20+i)+"dp");
                size.setTextSize(i+20);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                editor.putInt("size",seekBar.getProgress()+20);
            }
        });
        Button btnEnter = (Button) findViewById(R.id.btn_size_enter);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.commit();
    }
}
