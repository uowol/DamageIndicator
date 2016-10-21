package com.example.chan_woo_kim.damageindicator;
//tumbex

import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Chan_Woo_Kim on 2016-09-08.
 */
public class WindowService extends Service {

    Context context;

    private static int counter = 0;
    private final double MB = 7077888.0 / 7.1;
    private final double KB = 7077888.0 / 7.1 / 1024.0;
    //intent로 받을것들
    private int type;
    private int unit;
    private int anim;
    private int limit;
    private int time;
    private int size;
    private String font = "Phantom Fingers.ttf";
    private String color = "#f0f500";

    private int[] units;
    private Animation mAnimation;
    private DisplayMetrics mDisplayMetrics;
    private Random mRandom;
    private long mStartRX;
    private long mStartTX;
    private WindowManager mManager;
    private double VALUE = 0;
    private int minX, minY, maxX, maxY;
    private long mDataUsage;
    private long mOldDataUsage;
    private Handler mHandler = new Handler();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("Now", "onStartCommand");

        if (intent != null) {
            type = intent.getIntExtra("type", -1);
            unit = intent.getIntExtra("unit", -1);
            anim = intent.getIntExtra("anim", 0);
            limit = intent.getIntExtra("limit", 1);
            time = intent.getIntExtra("time", 1);
            size = intent.getIntExtra("size", 20);
            font = intent.getStringExtra("font");
            color = intent.getStringExtra("color");
        }
        Log.e("Value", type + " " + unit);
        return startId;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(0);
        Log.e("Now", "DESTROYED");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;

        mOldDataUsage = TrafficStats.getMobileRxBytes() + TrafficStats.getMobileTxBytes();

        if (mStartRX == TrafficStats.UNSUPPORTED || mStartTX == TrafficStats.UNSUPPORTED) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Uh Oh!");
            alert.setMessage("Your device does not support traffic stat monitoring.");
            alert.show();
        } else {
            mHandler.post(mRunnable);
        }

        mRandom = new Random();

        mDisplayMetrics = getApplicationContext().getResources().getDisplayMetrics();

        mManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        minX = -mDisplayMetrics.widthPixels / 2;
        maxX = mDisplayMetrics.widthPixels / 2;
        minY = -mDisplayMetrics.heightPixels / 2;
        maxY = mDisplayMetrics.heightPixels / 2;
    }


    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {

            mDataUsage = TrafficStats.getMobileRxBytes() + TrafficStats.getMobileTxBytes();
            double val = 0;
            if (type == 0) {
                val = (mDataUsage - mOldDataUsage) / KB;
            } else if (type == 1) {
                val = (mDataUsage - mOldDataUsage) / MB;
            }
            mOldDataUsage = mDataUsage;

            VALUE += val;
//            Log.e("VALUE",VALUE+"");

            final TextView damage = new TextView(context);

            if (type == 0) {
                damage.setText("-" + String.format("%.2f", VALUE) + "KB");
            } else {
                damage.setText("-" + String.format("%.2f", VALUE) + "MB");
            }

            // 여기도 저장된 값 불러와야함

            damage.setShadowLayer((float) 0.1, 2, 2, Color.parseColor("#483000"));
            damage.setTextColor(Color.parseColor(color));
            damage.setTextSize(size);
            damage.setTypeface(Typeface.createFromAsset(context.getAssets(), font));

            final RelativeLayout layout = new RelativeLayout(context);
            layout.setMinimumHeight(200);
            layout.addView(damage);

            final WindowManager.LayoutParams mParam = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT
            );
            mParam.x = mRandom.nextInt((maxX - 100) - (minX + 100) + 1) - maxX + 100;
            mParam.y = mRandom.nextInt(-(minY + 100) + 1) - maxY + 100;

//            Log.w("AnimationError",""+anim);
            if (anim == 0)
                mAnimation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
            else if (anim == 1)
                mAnimation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);

            // 만들어진 것 위에 다른 것이 덮어버리면 그 아래있는건 수정 불가하다

            if (type == 0)
                units = new int[]{10, 100, 500};
            else if (type == 1)
                units = new int[]{1, 10, 100};
//            if (VALUE >= units[unit]) {
            if (VALUE >= 0 && counter <= limit) {
                VALUE = 0;
                mManager.addView(layout, mParam);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.w("Counter", counter + "");
                            damage.startAnimation(mAnimation);
                            counter++;
                            Thread.sleep(time * 1000);
                            mManager.removeView(layout);
                            counter--;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            mHandler.post(mRunnable);
        }
    };
}
