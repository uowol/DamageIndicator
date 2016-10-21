package com.example.chan_woo_kim.damageindicator;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

/**
 * Created by Chan_Woo_Kim on 2016-09-26.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private final SharedPreferences pref;
    private final View slideInAnimation;
    private final View fadeInAnimation;
    private final int ANIMATIONS = 2;

    Context c;

    String colorShadow = "#483000";                          // pref에 넣고 가져와야
    String font;
    String color;

    private LayoutInflater inflater;

    public ViewPagerAdapter(Context c, SharedPreferences pref) {
        this.c = c;
        this.pref = pref;
        inflater = LayoutInflater.from(c);

        slideInAnimation = inflater.inflate(R.layout.page_slide_in, null);
        fadeInAnimation = inflater.inflate(R.layout.page_fade_in, null);

        color = this.pref.getString("color","#f03232");
        font = this.pref.getString("font","hachicro.TTF");


    }

    @Override
    public int getCount() {
        return ANIMATIONS;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View v = null;
        final TextView[] mTextView = new TextView[ANIMATIONS];
        final Animation[] a = new Animation[ANIMATIONS];

        switch (position){
            case 0:
                v = fadeInAnimation;
                mTextView[position] = (TextView)v.findViewById(R.id.tv_fade_in);
                a[position] = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
                a[position].setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mTextView[position].startAnimation(a[position]);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                mTextView[position].setTypeface(Typeface.createFromAsset(c.getAssets(), font));
                mTextView[position].setShadowLayer((float) 0.1, 2, 2, Color.parseColor(colorShadow));
                mTextView[position].setTextColor(Color.parseColor(color));
                mTextView[position].startAnimation(a[position]);
                break;
            case 1:
                v = slideInAnimation;
                mTextView[position] = (TextView)v.findViewById(R.id.tv_slide_in);
                a[position] = AnimationUtils.loadAnimation(c, android.R.anim.slide_in_left);
                a[position].setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mTextView[position].startAnimation(a[position]);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                mTextView[position].setTypeface(Typeface.createFromAsset(c.getAssets(), font));
                mTextView[position].setShadowLayer((float) 0.1, 2, 2, Color.parseColor(colorShadow));
                mTextView[position].setTextColor(Color.parseColor(color));
                mTextView[position].startAnimation(a[position]);
                break;
        }

        ((ViewPager)container).addView(v, 0);

        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((View)object);
    }
}
