package com.example.chan_woo_kim.damageindicator;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.rarepebble.colorpicker.ColorPreference;


/**
 * Created by Chan_Woo_Kim on 2016-09-26.
 */
public class MainPreferenceFragment extends PreferenceFragment {

    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;

    private SwitchPreference switchPref;
    private Preference colorPref;
    private Preference sizePref;
    private Preference unitPref;
    private Preference animPref;
    private Preference fontPref;
    private int selectedColorRGB;
    private ColorPicker mColorPicker;

    private void newWindow(Object newClass, int id) {
        Intent nWin = new Intent(getActivity(), (Class<?>) newClass);
        startActivityForResult(nWin,id);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case GlobalValues.SIZEACTIVITY:

                break;
            case GlobalValues.ANIMACTIVITY:

                break;
            case GlobalValues.UNITACTIVITY:

                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPref = getActivity().getSharedPreferences("data", getActivity().MODE_PRIVATE);
        mEditor = mPref.edit();
        addPreferencesFromResource(R.xml.pref_main);
        int defaultColorR = mPref.getInt("R",0);
        int defaultColorG = mPref.getInt("G",0);
        int defaultColorB = mPref.getInt("B",0);

        mColorPicker = new ColorPicker(getActivity(), defaultColorR, defaultColorG, defaultColorB);

        switchPref = (SwitchPreference) findPreference("isServiced");
        switchPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                int unit = mPref.getInt("unit",-1);
                int type = mPref.getInt("type",-1);
                int anim = mPref.getInt("anim",4);
                int limit = mPref.getInt("limit",1);
                int time = mPref.getInt("time",1);
                int size = mPref.getInt("size",20);
                String font = mPref.getString("font","hachicro.TTF");
                String color = mPref.getString("color","#f03232");

                Intent service = new Intent(getActivity(), WindowService.class);
                service.putExtra("unit",unit);
                service.putExtra("type",type);
                service.putExtra("anim",anim);
                service.putExtra("limit",limit);
                service.putExtra("time",time);
                service.putExtra("size",size);
                service.putExtra("font",font);
                service.putExtra("color",color);
                if((boolean)o){
                    getActivity().startService(service);
                }
                if(!(boolean)o){
                    getActivity().stopService(service);
                    Log.e("Stooooop!","Please!!!");
                }
                return true;
            }
        });
        sizePref = (Preference) findPreference("size");
        unitPref = (Preference) findPreference("unit");
        fontPref = (Preference) findPreference("font");
        animPref = (Preference) findPreference("anim");
        colorPref = (Preference) findPreference("color");
        colorPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                mColorPicker.show();
                EditText hexEdit = (EditText) mColorPicker.findViewById(R.id.codHex);
                hexEdit.setGravity(Gravity.CENTER);
                Button btnColorPicker = (Button) mColorPicker.findViewById(R.id.okColorButton);
                btnColorPicker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mEditor.putInt("R",mColorPicker.getRed());
                        mEditor.putInt("G",mColorPicker.getGreen());
                        mEditor.putInt("B",mColorPicker.getBlue());
                        selectedColorRGB = mColorPicker.getColor();
                        String color = String.format("#%06X", (0xFFFFFF & selectedColorRGB));
                        Log.e("Color Picked", color);
                        mEditor.putString("color", color);
                        mEditor.commit();
                        mColorPicker.hide();
                    }
                });
                return true;
            }
        });
        sizePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Log.e("size", "실행");
                newWindow(SizeActivity.class, GlobalValues.SIZEACTIVITY);

                return true;
            }
        });
        animPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Log.e("anim", "실행");
                newWindow(AnimActivity.class, GlobalValues.ANIMACTIVITY);

                return true;
            }
        });
        unitPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Log.e("unit", "실행");
                newWindow(UnitActivity.class, GlobalValues.UNITACTIVITY);

                return true;
            }
        });
        fontPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Log.e("font", "실행");
                newWindow(UnitActivity.class, GlobalValues.UNITACTIVITY);

                return false;
            }
        });
    }
}
