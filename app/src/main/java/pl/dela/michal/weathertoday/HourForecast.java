package pl.dela.michal.weathertoday;

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

public class HourForecast extends AppCompatActivity {
    private String FAKE_JSON12_HOURS = "[  {    \"DateTime\": \"2018-06-04T11:00:00+02:00\",    \"EpochDateTime\": 1528102800,    \"WeatherIcon\": 16,    \"IconPhrase\": \"Mostly cloudy w/ t-storms\",    \"IsDaylight\": true,    \"Temperature\": {      \"Value\": 21.7,      \"Unit\": \"C\",      \"UnitType\": 17    },    \"PrecipitationProbability\": 51,    \"MobileLink\": \"http://m.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&unit=c&lang=en-us\",    \"Link\": \"http://www.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&hbhhour=11&unit=c&lang=en-us\"  },  {    \"DateTime\": \"2018-06-04T12:00:00+02:00\",    \"EpochDateTime\": 1528106400,    \"WeatherIcon\": 3,    \"IconPhrase\": \"Partly sunny\",    \"IsDaylight\": true,    \"Temperature\": {      \"Value\": 23,      \"Unit\": \"C\",      \"UnitType\": 17    },    \"PrecipitationProbability\": 47,    \"MobileLink\": \"http://m.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&unit=c&lang=en-us\",    \"Link\": \"http://www.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&hbhhour=12&unit=c&lang=en-us\"  },  {    \"DateTime\": \"2018-06-04T13:00:00+02:00\",    \"EpochDateTime\": 1528110000,    \"WeatherIcon\": 2,    \"IconPhrase\": \"Mostly sunny\",    \"IsDaylight\": true,    \"Temperature\": {      \"Value\": 23.9,      \"Unit\": \"C\",      \"UnitType\": 17    },    \"PrecipitationProbability\": 43,    \"MobileLink\": \"http://m.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&unit=c&lang=en-us\",    \"Link\": \"http://www.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&hbhhour=13&unit=c&lang=en-us\"  },  {    \"DateTime\": \"2018-06-04T14:00:00+02:00\",    \"EpochDateTime\": 1528113600,    \"WeatherIcon\": 2,    \"IconPhrase\": \"Mostly sunny\",    \"IsDaylight\": true,    \"Temperature\": {      \"Value\": 24.7,      \"Unit\": \"C\",      \"UnitType\": 17    },    \"PrecipitationProbability\": 43,    \"MobileLink\": \"http://m.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&unit=c&lang=en-us\",    \"Link\": \"http://www.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&hbhhour=14&unit=c&lang=en-us\"  },  {    \"DateTime\": \"2018-06-04T15:00:00+02:00\",    \"EpochDateTime\": 1528117200,    \"WeatherIcon\": 2,    \"IconPhrase\": \"Mostly sunny\",    \"IsDaylight\": true,    \"Temperature\": {      \"Value\": 25.1,      \"Unit\": \"C\",      \"UnitType\": 17    },    \"PrecipitationProbability\": 47,    \"MobileLink\": \"http://m.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&unit=c&lang=en-us\",    \"Link\": \"http://www.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&hbhhour=15&unit=c&lang=en-us\"  },  {    \"DateTime\": \"2018-06-04T16:00:00+02:00\",    \"EpochDateTime\": 1528120800,    \"WeatherIcon\": 17,    \"IconPhrase\": \"Partly sunny w/ t-storms\",    \"IsDaylight\": true,    \"Temperature\": {      \"Value\": 25,      \"Unit\": \"C\",      \"UnitType\": 17    },    \"PrecipitationProbability\": 51,    \"MobileLink\": \"http://m.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&unit=c&lang=en-us\",    \"Link\": \"http://www.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&hbhhour=16&unit=c&lang=en-us\"  },  {    \"DateTime\": \"2018-06-04T17:00:00+02:00\",    \"EpochDateTime\": 1528124400,    \"WeatherIcon\": 3,    \"IconPhrase\": \"Partly sunny\",    \"IsDaylight\": true,    \"Temperature\": {      \"Value\": 25.9,      \"Unit\": \"C\",      \"UnitType\": 17    },    \"PrecipitationProbability\": 47,    \"MobileLink\": \"http://m.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&unit=c&lang=en-us\",    \"Link\": \"http://www.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&hbhhour=17&unit=c&lang=en-us\"  },  {    \"DateTime\": \"2018-06-04T18:00:00+02:00\",    \"EpochDateTime\": 1528128000,    \"WeatherIcon\": 3,    \"IconPhrase\": \"Partly sunny\",    \"IsDaylight\": true,    \"Temperature\": {      \"Value\": 25.2,      \"Unit\": \"C\",      \"UnitType\": 17    },    \"PrecipitationProbability\": 47,    \"MobileLink\": \"http://m.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&unit=c&lang=en-us\",    \"Link\": \"http://www.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&hbhhour=18&unit=c&lang=en-us\"  },  {    \"DateTime\": \"2018-06-04T19:00:00+02:00\",    \"EpochDateTime\": 1528131600,    \"WeatherIcon\": 17,    \"IconPhrase\": \"Partly sunny w/ t-storms\",    \"IsDaylight\": true,    \"Temperature\": {      \"Value\": 23.1,      \"Unit\": \"C\",      \"UnitType\": 17    },    \"PrecipitationProbability\": 51,    \"MobileLink\": \"http://m.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&unit=c&lang=en-us\",    \"Link\": \"http://www.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&hbhhour=19&unit=c&lang=en-us\"  },  {    \"DateTime\": \"2018-06-04T20:00:00+02:00\",    \"EpochDateTime\": 1528135200,    \"WeatherIcon\": 3,    \"IconPhrase\": \"Partly sunny\",    \"IsDaylight\": true,    \"Temperature\": {      \"Value\": 22.1,      \"Unit\": \"C\",      \"UnitType\": 17    },    \"PrecipitationProbability\": 43,    \"MobileLink\": \"http://m.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&unit=c&lang=en-us\",    \"Link\": \"http://www.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&hbhhour=20&unit=c&lang=en-us\"  },  {    \"DateTime\": \"2018-06-04T21:00:00+02:00\",    \"EpochDateTime\": 1528138800,    \"WeatherIcon\": 41,    \"IconPhrase\": \"Partly cloudy w/ t-storms\",    \"IsDaylight\": false,    \"Temperature\": {      \"Value\": 21.1,      \"Unit\": \"C\",      \"UnitType\": 17    },    \"PrecipitationProbability\": 51,    \"MobileLink\": \"http://m.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&unit=c&lang=en-us\",    \"Link\": \"http://www.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&hbhhour=21&unit=c&lang=en-us\"  },  {    \"DateTime\": \"2018-06-04T22:00:00+02:00\",    \"EpochDateTime\": 1528142400,    \"WeatherIcon\": 35,    \"IconPhrase\": \"Partly cloudy\",    \"IsDaylight\": false,    \"Temperature\": {      \"Value\": 20,      \"Unit\": \"C\",      \"UnitType\": 17    },    \"PrecipitationProbability\": 47,    \"MobileLink\": \"http://m.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&unit=c&lang=en-us\",    \"Link\": \"http://www.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&hbhhour=22&unit=c&lang=en-us\"  }]";
    private ArrayList<WeatherHourly> weatherArrayList = new ArrayList<WeatherHourly>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hour_forecast);


       // URL weatherUrl = NetworkUtils.buildUrlForWeather12Hours();
        //       // Log.i("url",weatherUrl.toString());
        //        //Json_data = new FetchWeatherDetails().execute(weatherUrl);
        Log.i("data:",FAKE_JSON12_HOURS);
        parseJSON(FAKE_JSON12_HOURS);

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
                URL weatherUrl = NetworkUtils.buildUrlForWeather12Hours();
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
            Log.i("url",weatherSearchResults.toString());
            return weatherSearchResults;
        }
        @Override
        protected void onPostExecute(String weatherSearchResults) {
            if(weatherSearchResults != null && !weatherSearchResults.equals("")){
                Log.i("url here",weatherSearchResults);
                weatherArrayList = parseJSON(weatherSearchResults);
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

                    String date = resultsObj.getString("DateTime");
                    JSONObject temepratureOBJ = resultsObj.getJSONObject("Temperature");
                    String temperature = temepratureOBJ.getString("Value");
                    String icon  = resultsObj.getString("WeatherIcon");
                    String IconPhrase  = resultsObj.getString("IconPhrase");
                    String PrecipitationProbability  = resultsObj.getString("PrecipitationProbability");
                    weather.setIcon(icon);
                    weather.setPrecipitationProbability(PrecipitationProbability);
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
        DataPoint[] points = new DataPoint[12];
        String []dates= new String[12];
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
            txt.setText(weatherArrayList.get(i).getIconPhrase()  + "\nProbability:\n" + weatherArrayList.get(i).getPrecipitationProbability());
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
