package pl.dela.michal.weathertoday;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        SharedPreferences storage = getSharedPreferences("Data", 0);
        String firstUsage = storage.getString("firstUsage", "0");
        if(firstUsage.equals("0")){
            SharedPreferences.Editor storageEditor = storage.edit();
            storageEditor.putString("firstUsage", "1").commit();

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
    void goToMenu(){
        startActivity(new Intent(LoaderActivity.this, MenuActivity.class));
    }
    void closeActivity(){
        finish();
    }
}
