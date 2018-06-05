package pl.dela.michal.weathertoday;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

public class History24Hours extends AppCompatActivity {
    private ArrayList<WeatherHourly> weatherArrayList = new ArrayList<WeatherHourly>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history24_hours);

        SharedPreferences storage = getSharedPreferences("Data", 0);
        String JsonData = storage.getString("24hourhistory", "0");
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
                URL weatherUrl = NetworkUtils.buildUrlForWeather24HistoricalHours();
                Log.i("url",weatherUrl.toString());
                new FetchWeatherDetails().execute(weatherUrl);
            }
        });
    }
    private class FetchWeatherDetails extends AsyncTask<URL,Void,String> {

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
                parseJSON((weatherSearchResults));
            }
            super.onPostExecute(weatherSearchResults);
        }
    }

    private ArrayList<WeatherHourly> parseJSON(String weatherSearchResults) {
        if(weatherArrayList!=null){
            weatherArrayList.clear();
        }
        if(weatherSearchResults!= null){
            try{
                JSONArray rotObj = new JSONArray(weatherSearchResults);
                for(int i =0 ;i< rotObj.length();i++){
                    WeatherHourly weather = new WeatherHourly();
                    JSONObject resultsObj = rotObj.getJSONObject(i);

                    String date = resultsObj.getString("LocalObservationDateTime");
                    JSONObject temepratureOBJ = resultsObj.getJSONObject("Temperature");
                    String temperature = temepratureOBJ.getJSONObject("Metric").getString("Value");
                    String icon  = resultsObj.getString("WeatherIcon");
                    String IconPhrase  = resultsObj.getString("WeatherText");
                    weather.setIcon(icon);
                    weather.setIconPhrase(IconPhrase);
                    weather.setDate(date);
                    weather.setTemperature(temperature);
                    Log.i("data: ","date: " + date +",hour: " + weather.getHour() + ", temperature: " + temperature + ", Icon: " + icon + ",icon phrase:" + IconPhrase);
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
        DataPoint[] points = new DataPoint[24];
        String []dates= new String[24];
        for(int i =0;i< weatherArrayList.size();i++){
            points [i] = new DataPoint(i,Double.valueOf(weatherArrayList.get(i).getTemperature()));
            dates[i] = weatherArrayList.get(i).getHour();
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
            dateTxt.setText(weatherArrayList.get(i).getHour());
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);
        series.setColor(Color.RED);
        series.setTitle("Temperature");
        graph.addSeries(series);

        graph.getGridLabelRenderer().setGridColor(Color.WHITE);
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(dates);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
    }
}
