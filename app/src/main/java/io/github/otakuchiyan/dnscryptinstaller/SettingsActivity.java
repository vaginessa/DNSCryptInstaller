package io.github.otakuchiyan.dnscryptinstaller;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

/**
 * Created by yugen on 10/3/15.
 */
public class SettingsActivity extends PreferenceActivity{
    @Override
    public void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(R.string.action_settings);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new AppPref()).commit();
    }

    public static class AppPref extends PreferenceFragment{
        @Override
        public void onCreate(final Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref);
        }
    }
}
