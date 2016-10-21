package com.example.chan_woo_kim.damageindicator.backup;
//tumbex

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
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chan_woo_kim.damageindicator.R;

import java.util.Random;

/**
 * Created by Chan_Woo_Kim on 2016-09-08.
 */
public class WindowService extends Service {

    Context context;

    private final double MB = 7077888.0/7.1;

    private final double KB = 7077888.0/7.1/1024.0;
    private int type;
    private int unit;

    private int anim;

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

        if(intent!=null) {
            type = intent.getIntExtra("type", -1);
            unit = intent.getIntExtra("unit", -1);
            anim = intent.getIntExtra("anim", 1);
        }
        Log.e("Value", type+" "+unit);
        return startId;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
            }else if(type == 1){
                val = (mDataUsage - mOldDataUsage) / MB;
            }
            mOldDataUsage = mDataUsage;

            VALUE += val;
//            Log.e("VALUE",VALUE+"");

            final TextView damage = new TextView(context);

            if(type == 0){
                damage.setText("-" + String.format("%.2f", VALUE) + "KB");
            }else{
                damage.setText("-" + String.format("%.2f", VALUE) + "MB");
            }

            damage.setShadowLayer((float) 0.1, 2, 2, Color.parseColor("#483000"));
            damage.setTextColor(Color.RED);
            damage.setTextSize(40);
            damage.setTypeface(Typeface.createFromAsset(context.getAssets(), "big_noodle_titling_oblique.ttf"));

            if(anim == 4) {
                mAnimation = AnimationUtils.loadAnimation(context, R.anim.custom_anim_toast);
            }

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

            if(anim == 0)
                mParam.windowAnimations = android.R.style.Animation_Translucent;
            else if (anim == 1)
                mParam.windowAnimations = android.R.style.Animation_InputMethod;
            else if (anim == 2)
                mParam.windowAnimations = android.R.style.Animation_Toast;
            else if (anim == 3)
                mParam.windowAnimations = android.R.style.Animation_Dialog;



            // 만들어진 것 위에 다른 것이 덮어버리면 그 아래있는건 수정 불가하다

            if(type == 0)
                units = new int[]{10, 100, 500};
            else if (type == 1)
                units = new int[]{1, 10 ,100};
//            if (VALUE >= units[unit]) {
            if (VALUE >= 0) {
                VALUE = 0;
                mManager.addView(layout, mParam);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
//                            damage.startAnimation(a);
                            Thread.sleep(1000);
                            mManager.removeView(layout);
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
