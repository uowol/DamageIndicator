//package com.example.chan_woo_kim.damageindicator;
//
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.preference.PreferenceActivity;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.SwitchCompat;
//import android.widget.CompoundButton;
//import android.widget.TextView;
//
//public class BackupActivity extends PreferenceActivity implements CompoundButton.OnCheckedChangeListener {
//
//    private TextView mDatausage;
//    private int[] units;
//
//    enum Types {KB, MB}
//
//    Types dataType;
//    int unit;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        addPreferencesFromResource(R.xml.pref_main);
//        startActivity(new Intent(this, Splash.class));
//
//        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
//        if (pref.getInt("datatype", -1) != -1) {
//
//            dataType = Types.values()[pref.getInt("datatype", -1)];
//            unit = pref.getInt("unit", -1);
//
//            if (pref.getInt("datatype", -1) == 0)
//                units = new int[]{10, 100, 500};
//            else if (pref.getInt("datatype", -1) == 1)
//                units = new int[]{1, 10, 100};
//
//            mDatausage = (TextView) findViewById(R.id.datausage);
//            mDatausage.setText(units[unit] + " " + dataType);
//
//
//        }
//
////        SwitchCompat bt = (SwitchCompat) findViewById(R.id.mborkb);
////        bt.setOnCheckedChangeListener(this);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//
//        editor.putInt("unit", unit);
//        if (dataType == Types.KB)
//            editor.putInt("datatype", 0);
//        else
//            editor.putInt("datatype", 1);
//        editor.commit();
//
//        Intent intent = new Intent(this, WindowService.class);
//        if (dataType == Types.KB) {
//            intent.putExtra("type", 0);
//            intent.putExtra("unit", unit);
//        } else {
//            intent.putExtra("type", 1);
//            intent.putExtra("unit", unit);
//        }
//        startService(intent);
//    }
//
//    @Override
//    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//        if (b) {
//            dataType = Types.KB;
//            final CharSequence[] units = {"10 KB", "100 KB", "500 KB"};
//            AlertDialog.Builder ab = new AlertDialog.Builder(this);
//            ab.setTitle("띄우는 기준을 선택하세요.");
//            ab.setItems(units, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    unit = i;
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            ((TextView) findViewById(R.id.datausage)).setText(units[unit]);
//                        }
//                    });
//                }
//            });
//            ab.show();
//        } else {
//            dataType = Types.MB;
//            final CharSequence[] units = {"1 MB", "10 MB", "100 MB"};
//            AlertDialog.Builder ab = new AlertDialog.Builder(this);
//            ab.setTitle("띄우는 기준을 선택하세요.");
//            ab.setItems(units, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    unit = i;
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            ((TextView) findViewById(R.id.datausage)).setText(units[unit]);
//                        }
//                    });
//                }
//            });
//            ab.show();
//
//        }
//    }
//}
