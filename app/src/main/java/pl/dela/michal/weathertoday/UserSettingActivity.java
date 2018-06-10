package pl.dela.michal.weathertoday;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class UserSettingActivity extends PreferenceActivity {
    static Context context;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.user_settings);
        context = this;
        //ChangeLanguageButton
        ListPreference languagechange =  (ListPreference) findPreference("ChangeLanguageButton");
        languagechange.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Log.i("preference","has changed");
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                String value = sharedPrefs.getString("ChangeLanguageButton", "NOPASSWORD");
                Log.i("preference",value);
                SharedPreferences storage = getSharedPreferences("Data", 0);
                SharedPreferences.Editor storageEditor = storage.edit();
                storageEditor.putString("language",value).commit();
                setLocale(value);
                NetworkUtils.UpdatePreferences(UserSettingActivity.context);
                return true;
            }
        });
        Preference localizationBtn =  (Preference) findPreference("LocationBtn");
        localizationBtn.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(UserSettingActivity.this,LoccationSettingsActivity.class));
                return true;
            }
        });
    }
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
