package pl.dela.michal.weathertoday;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class LoccationSettingsActivity extends AppCompatActivity  {
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    double lattitude;
    double longitude;
    private ArrayList<Localization> placeArrayList = new ArrayList<Localization>();
    private ListView list ;
    private ArrayAdapter<String> adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loccation_settings);
        LoadNewPosition();
        Button btnSetGPSLocation = (Button) findViewById(R.id.btnGPS);
        btnSetGPSLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                getLocationByGPS();
            }
        });
        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EditText editTextPlace = (EditText) findViewById(R.id.editTextPlace);
                String searchingText = editTextPlace.getText().toString();
                URL weatherUrl = NetworkUtils.buildUrlForCityPositionSearch(searchingText);
                Log.i("url",weatherUrl.toString());
                new FetchManyPlaceDetails().execute(weatherUrl);
            }
        });

        Button clickButton = (Button) findViewById(R.id.backBtn);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    void getLocationByGPS() {
        if( ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null){
                lattitude = location.getLatitude();
                longitude = location.getLongitude();
                Log.i("GPS:", "Your Location is - \nLat: " + lattitude + "\nLong: " + longitude);
                URL weatherUrl = NetworkUtils.buildUrlForLatAndLongLocalization(lattitude,longitude);
                Log.i("url",weatherUrl.toString());
                new FetchPlaceDetails().execute(weatherUrl);
            } else {
                Log.i("GPS:", "problem");
            }
        }
    }

    private class FetchPlaceDetails extends AsyncTask<URL,Void,String> {

        @Override
        protected void  onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(URL... urls) {
            URL weatherUrl = urls[0];
            String weatherSearchResults = null;
            try {
                weatherSearchResults = NetworkUtils.getResponseFromHttpUrl(weatherUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return weatherSearchResults;
        }
        @Override
        protected void onPostExecute(String weatherSearchResults) {
            if(weatherSearchResults != null && !weatherSearchResults.equals("")){
                parseJSONGeoposition((weatherSearchResults));
            }
            super.onPostExecute(weatherSearchResults);
        }
    }

    private class FetchManyPlaceDetails extends AsyncTask<URL,Void,String> {

        @Override
        protected void  onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(URL... urls) {
            URL weatherUrl = urls[0];
            String weatherSearchResults = null;
            try {
                weatherSearchResults = NetworkUtils.getResponseFromHttpUrl(weatherUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return weatherSearchResults;
        }
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onPostExecute(String weatherSearchResults) {
            if(weatherSearchResults != null && !weatherSearchResults.equals("")){
                Log.i("Saved" , weatherSearchResults);
                parseJSONCities((weatherSearchResults));
            }
            super.onPostExecute(weatherSearchResults);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private ArrayList<Localization> parseJSONCities(String citiesJSON) {
        if(placeArrayList!=null){
            placeArrayList.clear();
        }
        if(citiesJSON!= null){
            try{
                LinearLayout list = (LinearLayout) findViewById(R.id.linerLayoutInside);
                list.removeAllViews();
                JSONArray rotObj = new JSONArray(citiesJSON);
                String places[] = new String[rotObj.length()];
                for(int i =0 ;i< rotObj.length();i++){
                    WeatherHourly weather = new WeatherHourly();
                    JSONObject resultsObj = rotObj.getJSONObject(i);

                    final String LocalizedName = resultsObj.getString("LocalizedName");
                    String Country = resultsObj.getJSONObject("Country").getString("LocalizedName");
                    String AdministrativeArea = resultsObj.getJSONObject("AdministrativeArea").getString("LocalizedName");
                    String Key = resultsObj.getString("Key");

                    Log.i("data: ","LocalizedName: " + LocalizedName + ",Country: " + Country + ", AdministrativeArea: " + AdministrativeArea + ", Key: " + Key );
                    Localization thisPlace = new Localization();
                    thisPlace.setAdministrativeArea(AdministrativeArea);
                    thisPlace.setKey(Key);
                    thisPlace.setCountry(Country);
                    thisPlace.setLocalizedName(LocalizedName);
                    placeArrayList.add(thisPlace);

                    //adding to scrollview

                    TextView tv1 = new TextView(this);
                    tv1.setText(String.valueOf(i) + ".LocalizedName: " + LocalizedName + "\nCountry: " + Country + "\nAdministrativeArea: " + AdministrativeArea + "\nKey: " + Key);
                    tv1.setTag(i);
                    tv1.setPadding(10,10,10,10);
                    tv1.setTextSize(16);
                    tv1.setBackground(getDrawable(R.drawable.border));
                    tv1.setTextColor(Color.BLACK);
                    tv1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            Localization thisPlace = placeArrayList.get(Integer.valueOf(((Integer) arg0.getTag())));
                            saveNewPosition(thisPlace);
                            setTitleText(thisPlace);
                       }
                    });
                    list.addView(tv1);
                }
                return placeArrayList;
            }
             catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void parseJSONGeoposition(String geopositionJSON) {

        if(geopositionJSON!= null){
            try{
                JSONObject resultsObj = new JSONObject(geopositionJSON);
                String LocalizedName = resultsObj.getString("LocalizedName");
                String Country = resultsObj.getJSONObject("Country").getString("LocalizedName");
                String AdministrativeArea = resultsObj.getJSONObject("AdministrativeArea").getString("LocalizedName");
                String Key = resultsObj.getString("Key");

                Localization thisPlace = new Localization();
                thisPlace.setAdministrativeArea(AdministrativeArea);
                thisPlace.setKey(Key);
                thisPlace.setCountry(Country);
                thisPlace.setLocalizedName(LocalizedName);

                saveNewPosition(thisPlace);
                setTitleText(thisPlace);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    void saveNewPosition(Localization position){
        SharedPreferences storage = getSharedPreferences("Data", 0);
        SharedPreferences.Editor storageEditor1 = storage.edit();
        storageEditor1.putString("LocalizedName", position.getLocalizedName());
        storageEditor1.putString("Country", position.getCountry());
        storageEditor1.putString("AdministrativeArea", position.getAdministrativeArea());
        storageEditor1.putString("Key", position.getKey());
        storageEditor1.apply();
        NetworkUtils.UpdatePreferences(this);
        //Uncoment to download all data after changing localization
        reDownloadJsons();
    }
    void LoadNewPosition(){
        SharedPreferences storage = getSharedPreferences("Data", 0);
        String LocalizedName = storage.getString("LocalizedName", "0");
        String Country = storage.getString("Country", "0");
        String Key = storage.getString("Key", "0");
        String AdministrativeArea = storage.getString("AdministrativeArea", "0");

        Localization thisPlace = new Localization();
        thisPlace.setLocalizedName(LocalizedName);
        thisPlace.setCountry(Country);
        thisPlace.setAdministrativeArea(AdministrativeArea);
        thisPlace.setKey(Key);
        setTitleText(thisPlace);
    }
    void setTitleText(Localization position){
        String tmp = getText(R.string.LocalizedName) + ": " + position.getLocalizedName() + "\n" + getText(R.string.Country) + ": " + position.getCountry() + "\n" +getText(R.string.AdministrativeArea) + ": " + position.getAdministrativeArea()+ "\n" +getText(R.string.Key) + ": "+ position.getKey();
        Log.i("data: ",tmp);
        TextView InformationAboutPlace = (TextView) findViewById(R.id.InformationAboutPlace);
        InformationAboutPlace.setText(tmp);


    }

    private void reDownloadJsons(){
        URL weatherUrl = NetworkUtils.buildUrlForWeather5Days();
        new FetchWeatherDetails5days().execute(weatherUrl);

        URL weatherUrl12hours = NetworkUtils.buildUrlForWeather12Hours();
        new FetchWeatherDetails12hours().execute(weatherUrl12hours);

        URL weatherUrl24hours = NetworkUtils.buildUrlForWeather24HistoricalHours();
        new FetchWeatherDetails24hours().execute(weatherUrl24hours);
    }
    private class FetchWeatherDetails5days extends AsyncTask<URL,Void,String>{

        @Override
        protected void  onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(URL... urls) {
            URL weatherUrl = urls[0];
            String weatherSearchResults = null;
            try {
                weatherSearchResults = NetworkUtils.getResponseFromHttpUrl(weatherUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return weatherSearchResults;
        }
        @Override
        protected void onPostExecute(String weatherSearchResults) {
            if(weatherSearchResults != null && !weatherSearchResults.equals("")){
                SharedPreferences storage = getSharedPreferences("Data", 0);
                SharedPreferences.Editor storageEditor = storage.edit();
                storageEditor.putString("5days", weatherSearchResults).commit();
                Log.i("Saved" , weatherSearchResults);
                //parseJSON((weatherSearchResults));

            }
            super.onPostExecute(weatherSearchResults);
        }
    }
    private class FetchWeatherDetails12hours extends AsyncTask<URL,Void,String> {

        @Override
        protected void  onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(URL... urls) {
            URL weatherUrl = urls[0];
            String weatherSearchResults = null;
            try {
                weatherSearchResults = NetworkUtils.getResponseFromHttpUrl(weatherUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return weatherSearchResults;
        }
        @Override
        protected void onPostExecute(String weatherSearchResults) {
            if(weatherSearchResults != null && !weatherSearchResults.equals("")){
                SharedPreferences storage = getSharedPreferences("Data", 0);
                SharedPreferences.Editor storageEditor = storage.edit();
                storageEditor.putString("12hours", weatherSearchResults).commit();
                Log.i("Saved" , weatherSearchResults);
            }
            super.onPostExecute(weatherSearchResults);
        }
    }
    private class FetchWeatherDetails24hours extends AsyncTask<URL,Void,String> {

        @Override
        protected void  onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(URL... urls) {
            URL weatherUrl = urls[0];
            String weatherSearchResults = null;
            try {
                weatherSearchResults = NetworkUtils.getResponseFromHttpUrl(weatherUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return weatherSearchResults;
        }
        @Override
        protected void onPostExecute(String weatherSearchResults) {
            if(weatherSearchResults != null && !weatherSearchResults.equals("")){
                SharedPreferences storage = getSharedPreferences("Data", 0);
                SharedPreferences.Editor storageEditor = storage.edit();
                storageEditor.putString("24hourhistory", weatherSearchResults).commit();
                Log.i("Saved" , weatherSearchResults);
            }
            super.onPostExecute(weatherSearchResults);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocationByGPS();
                break;
        }
    }
}
