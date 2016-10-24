package com.example.chan_woo_kim.damageindicator;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chan_Woo_Kim on 2016-09-26.
 */
public class UnitFragment extends PreferenceFragment{
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int i;


    @Override
    public void onDestroy() {
        super.onDestroy();
        editor.commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_unit);

        pref = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = pref.edit();

        final CheckBoxPreference MBCBox = (CheckBoxPreference) findPreference("MB");
        final CheckBoxPreference KBCBox = (CheckBoxPreference) findPreference("KB");

        MBCBox.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                boolean checked = (boolean)o;
                if(checked){
                    KBCBox.setChecked(false);
                    editor.putInt("type",0);
                }else{
                    KBCBox.setChecked(true);
                    editor.putInt("type",1);
                }
                return true;
            }
        });
        KBCBox.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                boolean checked = (boolean)o;
                if(checked){
                    MBCBox.setChecked(false);
                    editor.putInt("type",1);
                }else{
                    MBCBox.setChecked(true);
                    editor.putInt("type",0);
                }
                return true;
            }
        });

        String usingBox;

        final List<CheckBoxPreference> MBboxes = new ArrayList<>();
        MBboxes.add((CheckBoxPreference) findPreference("1MB"));
        MBboxes.add((CheckBoxPreference) findPreference("5MB"));
        MBboxes.add((CheckBoxPreference) findPreference("10MB"));
        MBboxes.add((CheckBoxPreference) findPreference("100MB"));
        for (final CheckBoxPreference box1 : MBboxes){
            box1.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                final int k = i++;
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    for(CheckBoxPreference box2 : MBboxes){
                        box2.setChecked(false);
                    }
                    box1.setChecked(true);
                    findPreference("typing_MB").setTitle("직접입력");
                    Log.w("NOW", k+"");
                    switch (k){
                        case 0:
                            editor.putInt("unit",1);
                            break;
                        case 1:
                            editor.putInt("unit",5);
                            break;
                        case 2:
                            editor.putInt("unit",10);
                            break;
                        case 3:
                            editor.putInt("unit",100);
                            break;
                    }
                    return true;
                }
            });
        }
        i=0;
        findPreference("typing_MB").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                Log.w("Text", (String)o);
                preference.setTitle(o +"MB");
                for(CheckBoxPreference box2 : MBboxes){
                    box2.setChecked(false);
                }
                try{
                    int k = Integer.parseInt((String) o);
                    editor.putInt("unit",k);
                    Toast.makeText(getActivity(),k+"MB",Toast.LENGTH_SHORT).show();
                    return true;
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
                Toast.makeText(getActivity(),"숫자를 적어주세요.",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        final List<CheckBoxPreference> KBboxes = new ArrayList<>();
        KBboxes.add((CheckBoxPreference) findPreference("100KB"));
        KBboxes.add((CheckBoxPreference) findPreference("200KB"));
        KBboxes.add((CheckBoxPreference) findPreference("500KB"));
        for (final CheckBoxPreference box1 : KBboxes){
            box1.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                final int k = i++;
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    for(CheckBoxPreference box2 : KBboxes){
                        box2.setChecked(false);
                    }
                    box1.setChecked(true);
                    findPreference("typing_KB").setTitle("직접입력");
                    Log.w("NOW", k+"");
                    switch (k){
                        case 0:
                            editor.putInt("unit",100);
                            break;
                        case 1:
                            editor.putInt("unit",200);
                            break;
                        case 2:
                            editor.putInt("unit",500);
                            break;
                    }
                    return true;
                }
            });
        }
        i=0;
        findPreference("typing_KB").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                Log.w("Text", (String)o);
                for(CheckBoxPreference box2 : KBboxes){
                    box2.setChecked(false);
                }
                preference.setTitle(o +"KB");
                try{
                    int k = Integer.parseInt((String) o);
                    editor.putInt("unit",k);
                    Toast.makeText(getActivity(),k+"KB",Toast.LENGTH_SHORT).show();
                    return true;
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
                Toast.makeText(getActivity(),"숫자를 적어주세요.",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
