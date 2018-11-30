package pl.dela.michal.weathertoday;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import java.util.Locale;

public class LoaderActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        SharedPreferences storage = getSharedPreferences("Data", 0);
        String firstUsage = storage.getString("firstUsage", "0");
        if(firstUsage.equals("0")){
            SharedPreferences.Editor storageEditor = storage.edit();
            storageEditor.putString("5days","");
            storageEditor.putString("12hours","");
            storageEditor.putString("24hourhistory", "");
            storageEditor.putString("language", "pl");
            storageEditor.putString("LocalizedName", "Zabierzów");
            storageEditor.putString("Country", "Polska");
            storageEditor.putString("AdministrativeArea", "Małopolskie");
            storageEditor.putString("Key", "274433");
            storageEditor.putString("firstUsage", "1");
            storageEditor.apply();
            Log.i("init","storage has been initiated");
            NetworkUtils.UpdatePreferences(this);
        }
        else {
            String lang = storage.getString("language", "0");
            if(!firstUsage.equals("0"))
                setLocale(lang);
            Log.i("init","storage has been loaded");
            NetworkUtils.UpdatePreferences(this);
        }

        Runnable r = new Runnable() {
            @Override
            public void run(){
                goToMenu();
            }
        };
        Runnable c = new Runnable() {
            @Override
            public void run(){
                closeActivity();
            }
        };

        Handler h = new Handler();
        h.postDelayed(r, 1000);
        h.postDelayed(c, 1100);
    }
    public void setLocale(String lang) {
        Log.i("init","language has been changed");
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
    void goToMenu(){
        startActivity(new Intent(LoaderActivity.this, MenuActivity.class));
    }
    void closeActivity(){
        finish();
    }

}
