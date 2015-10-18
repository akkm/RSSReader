package com.example.rssreader;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;


public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        // デフォルト値をtrueとして設定する
        boolean cacheEnabled = sharedPreferences.getBoolean(getString(R.string.cache_enabled_key), true);
        SwitchPreference cacheSwitch = (SwitchPreference) findPreference(getString(R.string.cache_enabled_key));
        cacheSwitch.setChecked(cacheEnabled);

        EditTextPreference editText = (EditTextPreference) findPreference(getString(R.string.edittext_key));
        editText.setSummary(editText.getText());

        ListPreference list = (ListPreference) findPreference(getString(R.string.list_key));
        list.setSummary(list.getEntry());
    }

    private SharedPreferences.OnSharedPreferenceChangeListener onPreferenceChangeListenter = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals(getString(R.string.edittext_key))) {
                EditTextPreference pref = (EditTextPreference) findPreference(key);
                pref.setSummary(pref.getText());
            } else if (key.equals(getString(R.string.list_key))) {
                ListPreference pref = (ListPreference) findPreference(key);
                pref.setSummary(pref.getEntry());
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        sharedPreferences.registerOnSharedPreferenceChangeListener(onPreferenceChangeListenter);
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(onPreferenceChangeListenter);
    }
}
