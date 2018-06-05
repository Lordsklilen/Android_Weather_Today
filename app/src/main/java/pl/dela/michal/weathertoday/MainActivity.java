package pl.dela.michal.weathertoday;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static pl.dela.michal.weathertoday.NetworkUtils.getIcon;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Weather> weatherArrayList = new ArrayList<Weather>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logAllSharedPreferences();
        SharedPreferences storage = getSharedPreferences("Data", 0);
        String JsonData = storage.getString("5days", "0");
        if(JsonData.equals("0")){
            Log.i("JSON","Error");
        }
        else{
            Log.i("JSON",JsonData);
            parseJSON(JsonData);
        }
        Button clickButton = (Button) findViewById(R.id.backBtn);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button updateBtn = (Button) findViewById(R.id.UpdateBtn);
        updateBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URL weatherUrl = NetworkUtils.buildUrlForWeather5Days();
                Log.i("url",weatherUrl.toString());
                new FetchWeatherDetails().execute(weatherUrl);
            }
        });
    }
    private class FetchWeatherDetails extends AsyncTask<URL,Void,String>{

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
                //error catch

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
                parseJSON((weatherSearchResults));

            }
            super.onPostExecute(weatherSearchResults);
        }
    }

    private ArrayList<Weather> parseJSON(String weatherSearchResults) {
        if(weatherArrayList!=null){
            weatherArrayList.clear();
        }
        if(weatherSearchResults!= null){
            try{
                JSONObject rotObj = new JSONObject(weatherSearchResults);
                JSONArray results = rotObj.getJSONArray("DailyForecasts");
                for(int i =0 ;i< results.length();i++){
                    Weather weather = new Weather();
                    JSONObject resultsObj = results.getJSONObject(i);

                    String date = resultsObj.getString("Date");
                    JSONObject temepratureOBJ = resultsObj.getJSONObject("Temperature");
                    String minTemperature = temepratureOBJ.getJSONObject("Minimum").getString("Value");
                    String maxTemperature = temepratureOBJ.getJSONObject("Maximum").getString("Value");
                    String link = resultsObj.getString("Link");

                    JSONObject IconsOBJ = resultsObj.getJSONObject("Day");
                    String icon  = IconsOBJ.getString("Icon");
                    String IconPhrase  = IconsOBJ.getString("IconPhrase");
                    weather.setLink(link);
                    weather.setIcon(icon);
                    weather.setIconPhrase(IconPhrase);
                    weather.setDate(date);
                    weather.setMinTemp(minTemperature);
                    weather.setMaxTemp(maxTemperature);
                    Log.i("data: ","date: " + date + ", min: " + minTemperature + ", max: " + maxTemperature+ ", Icon: " + icon + ",icon phrase:" + IconPhrase);
                    weatherArrayList.add(weather);
                }
                drawWeatherGraph();
                return weatherArrayList;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void drawWeatherGraph(){
        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.removeAllSeries();
        DataPoint[] pointsMin = new DataPoint[5];
        DataPoint[] pointsMax = new DataPoint[5];
        String []dates= new String[5];
        for(int i =0;i< weatherArrayList.size();i++){
           // series.appendData(new DataPoint(i,Integer.valueOf(weatherArrayList.get(i).getMinTemp())));
            pointsMin [i] = new DataPoint(i,Double.valueOf(weatherArrayList.get(i).getMinTemp()));
            pointsMax [i] = new DataPoint(i,Double.valueOf(weatherArrayList.get(i).getMaxTemp()));
            dates[i] = weatherArrayList.get(i).getDate();
            int ressourceId = getResources().getIdentifier(
                    "imageView"+i,
                    "id",
                    "pl.dela.michal.weathertoday");
            ImageView img = (ImageView) findViewById(ressourceId);
            img.setImageResource(getIcon(weatherArrayList.get(i).getIcon()));

            int  txtressourceId = getResources().getIdentifier(
                    "textView"+i,
                    "id",
                    "pl.dela.michal.weathertoday");
            TextView txt = (TextView) findViewById(txtressourceId);
            txt.setText(weatherArrayList.get(i).getIconPhrase());
            int txtressourceIddate = getResources().getIdentifier(
                    "textViewDay"+i,
                    "id",
                    "pl.dela.michal.weathertoday");
            TextView dateTxt = (TextView) findViewById(txtressourceIddate);
            dateTxt.setText(weatherArrayList.get(i).getDate());
        }
        LineGraphSeries<DataPoint> seriesMax = new LineGraphSeries<>(pointsMax);
        seriesMax.setColor(Color.RED);
        seriesMax.setTitle("Maximal temperature");

        LineGraphSeries<DataPoint> seriesMin = new LineGraphSeries<>(pointsMin);
        seriesMin.setColor(Color.BLUE);
        seriesMin.setTitle("Minimal temperature");

        graph.addSeries(seriesMax);
        graph.addSeries(seriesMin);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(dates);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);

        graph.getGridLabelRenderer().setGridColor(Color.WHITE);
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);

        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

    }

    private void logAllSharedPreferences(){

        SharedPreferences storage = getSharedPreferences("Data", 0);
        Log.i("pref first use", storage.getString("firstUsage", "0"));
        Log.i("pref 5days", getSubstring(storage.getString("5days", "0")));
        Log.i("pref 12hours", getSubstring(storage.getString("12hours", "0")));
        Log.i("pref 24hours", getSubstring(storage.getString("24hourhistory", "0")));
        Log.i("pref language", storage.getString("language", "0"));
    }
    private String getSubstring(String tmp){
        if(!tmp.equals("0"))
            return tmp.subSequence(0,80).toString();
        return "0";
    }
}
