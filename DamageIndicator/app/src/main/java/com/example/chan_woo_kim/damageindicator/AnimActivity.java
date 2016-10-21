package com.example.chan_woo_kim.damageindicator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by Chan_Woo_Kim on 2016-09-26.
 */
public class AnimActivity extends Activity implements MaterialTabListener {

    private MaterialTabHost mTab;
    private ViewPager mViewPager;
    private ViewPagerAdapter pagerAdapter;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onDestroy() {
        editor.commit();
        Log.w("Anime",pref.getInt("anim",99)+"");
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        pref = getSharedPreferences("data",MODE_PRIVATE);
        editor = pref.edit();

        mTab = (MaterialTabHost) findViewById(R.id.tab);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        editor.putInt("anim",0);
        pagerAdapter = new ViewPagerAdapter(this, pref);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mTab.setSelectedNavigationItem(position);
                Log.w("Position",position+"");
                if(position==0){
                    editor.putInt("anim",position);
                    Log.w("Anime",position+"");
                }else if(position==1){
                    editor.putInt("anim",position);
                    Log.w("Anime",position+"");
                }
            }
        });

        for(int i=0; i<pagerAdapter.getCount();i++) {
            mTab.addTab(mTab.newTab().setText("Animation" + i).setTabListener(this));
        }

        Button btnEnter = (Button) findViewById(R.id.btn_anim_enter);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onTabSelected(MaterialTab tab) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {
    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }
}
