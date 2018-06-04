package pl.dela.michal.weathertoday;

import android.content.Intent;
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
    private String FAKE_JSON5DAYS = "{\"Headline\":{\"EffectiveDate\":\"2018-06-03T08:00:00+02:00\",\"EffectiveEpochDate\":1528005600,\"Severity\":4,\"Text\":\"Thunderstorms in the area Sunday morning through Sunday evening; storms can cause flash flooding\",\"Category\":\"thunderstorm\",\"EndDate\":\"2018-06-04T02:00:00+02:00\",\"EndEpochDate\":1528070400,\"MobileLink\":\"http://m.accuweather.com/en/pl/zabierzow/274433/extended-weather-forecast/274433?unit=c&lang=en-us\",\"Link\":\"http://www.accuweather.com/en/pl/zabierzow/274433/daily-weather-forecast/274433?unit=c&lang=en-us\"},\"DailyForecasts\":[{\"Date\":\"2018-06-03T07:00:00+02:00\",\"EpochDate\":1528002000,\"Temperature\":{\"Minimum\":{\"Value\":13.8,\"Unit\":\"C\",\"UnitType\":17},\"Maximum\":{\"Value\":23.6,\"Unit\":\"C\",\"UnitType\":17}},\"Day\":{\"Icon\":16,\"IconPhrase\":\"Mostly cloudy w/ t-storms\"},\"Night\":{\"Icon\":41,\"IconPhrase\":\"Partly cloudy w/ t-storms\"},\"Sources\":[\"AccuWeather\"],\"MobileLink\":\"http://m.accuweather.com/en/pl/zabierzow/274433/daily-weather-forecast/274433?day=1&unit=c&lang=en-us\",\"Link\":\"http://www.accuweather.com/en/pl/zabierzow/274433/daily-weather-forecast/274433?day=1&unit=c&lang=en-us\"},{\"Date\":\"2018-06-04T07:00:00+02:00\",\"EpochDate\":1528088400,\"Temperature\":{\"Minimum\":{\"Value\":13.2,\"Unit\":\"C\",\"UnitType\":17},\"Maximum\":{\"Value\":25.7,\"Unit\":\"C\",\"UnitType\":17}},\"Day\":{\"Icon\":17,\"IconPhrase\":\"Partly sunny w/ t-storms\"},\"Night\":{\"Icon\":36,\"IconPhrase\":\"Intermittent clouds\"},\"Sources\":[\"AccuWeather\"],\"MobileLink\":\"http://m.accuweather.com/en/pl/zabierzow/274433/daily-weather-forecast/274433?day=2&unit=c&lang=en-us\",\"Link\":\"http://www.accuweather.com/en/pl/zabierzow/274433/daily-weather-forecast/274433?day=2&unit=c&lang=en-us\"},{\"Date\":\"2018-06-05T07:00:00+02:00\",\"EpochDate\":1528174800,\"Temperature\":{\"Minimum\":{\"Value\":10.0,\"Unit\":\"C\",\"UnitType\":17},\"Maximum\":{\"Value\":24.9,\"Unit\":\"C\",\"UnitType\":17}},\"Day\":{\"Icon\":17,\"IconPhrase\":\"Partly sunny w/ t-storms\"},\"Night\":{\"Icon\":35,\"IconPhrase\":\"Partly cloudy\"},\"Sources\":[\"AccuWeather\"],\"MobileLink\":\"http://m.accuweather.com/en/pl/zabierzow/274433/daily-weather-forecast/274433?day=3&unit=c&lang=en-us\",\"Link\":\"http://www.accuweather.com/en/pl/zabierzow/274433/daily-weather-forecast/274433?day=3&unit=c&lang=en-us\"},{\"Date\":\"2018-06-06T07:00:00+02:00\",\"EpochDate\":1528261200,\"Temperature\":{\"Minimum\":{\"Value\":8.7,\"Unit\":\"C\",\"UnitType\":17},\"Maximum\":{\"Value\":20.5,\"Unit\":\"C\",\"UnitType\":17}},\"Day\":{\"Icon\":2,\"IconPhrase\":\"Mostly sunny\"},\"Night\":{\"Icon\":36,\"IconPhrase\":\"Intermittent clouds\"},\"Sources\":[\"AccuWeather\"],\"MobileLink\":\"http://m.accuweather.com/en/pl/zabierzow/274433/daily-weather-forecast/274433?day=4&unit=c&lang=en-us\",\"Link\":\"http://www.accuweather.com/en/pl/zabierzow/274433/daily-weather-forecast/274433?day=4&unit=c&lang=en-us\"},{\"Date\":\"2018-06-07T07:00:00+02:00\",\"EpochDate\":1528347600,\"Temperature\":{\"Minimum\":{\"Value\":13.6,\"Unit\":\"C\",\"UnitType\":17},\"Maximum\":{\"Value\":24.8,\"Unit\":\"C\",\"UnitType\":17}},\"Day\":{\"Icon\":4,\"IconPhrase\":\"Intermittent clouds\"},\"Night\":{\"Icon\":34,\"IconPhrase\":\"Mostly clear\"},\"Sources\":[\"AccuWeather\"],\"MobileLink\":\"http://m.accuweather.com/en/pl/zabierzow/274433/daily-weather-forecast/274433?day=5&unit=c&lang=en-us\",\"Link\":\"http://www.accuweather.com/en/pl/zabierzow/274433/daily-weather-forecast/274433?day=5&unit=c&lang=en-us\"}]}";
    private String FAKE_JSON12_HOURS = "[  {    \"DateTime\": \"2018-06-04T11:00:00+02:00\",    \"EpochDateTime\": 1528102800,    \"WeatherIcon\": 16,    \"IconPhrase\": \"Mostly cloudy w/ t-storms\",    \"IsDaylight\": true,    \"Temperature\": {      \"Value\": 21.7,      \"Unit\": \"C\",      \"UnitType\": 17    },    \"PrecipitationProbability\": 51,    \"MobileLink\": \"http://m.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&unit=c&lang=en-us\",    \"Link\": \"http://www.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&hbhhour=11&unit=c&lang=en-us\"  },  {    \"DateTime\": \"2018-06-04T12:00:00+02:00\",    \"EpochDateTime\": 1528106400,    \"WeatherIcon\": 3,    \"IconPhrase\": \"Partly sunny\",    \"IsDaylight\": true,    \"Temperature\": {      \"Value\": 23,      \"Unit\": \"C\",      \"UnitType\": 17    },    \"PrecipitationProbability\": 47,    \"MobileLink\": \"http://m.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&unit=c&lang=en-us\",    \"Link\": \"http://www.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&hbhhour=12&unit=c&lang=en-us\"  },  {    \"DateTime\": \"2018-06-04T13:00:00+02:00\",    \"EpochDateTime\": 1528110000,    \"WeatherIcon\": 2,    \"IconPhrase\": \"Mostly sunny\",    \"IsDaylight\": true,    \"Temperature\": {      \"Value\": 23.9,      \"Unit\": \"C\",      \"UnitType\": 17    },    \"PrecipitationProbability\": 43,    \"MobileLink\": \"http://m.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&unit=c&lang=en-us\",    \"Link\": \"http://www.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&hbhhour=13&unit=c&lang=en-us\"  },  {    \"DateTime\": \"2018-06-04T14:00:00+02:00\",    \"EpochDateTime\": 1528113600,    \"WeatherIcon\": 2,    \"IconPhrase\": \"Mostly sunny\",    \"IsDaylight\": true,    \"Temperature\": {      \"Value\": 24.7,      \"Unit\": \"C\",      \"UnitType\": 17    },    \"PrecipitationProbability\": 43,    \"MobileLink\": \"http://m.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&unit=c&lang=en-us\",    \"Link\": \"http://www.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&hbhhour=14&unit=c&lang=en-us\"  },  {    \"DateTime\": \"2018-06-04T15:00:00+02:00\",    \"EpochDateTime\": 1528117200,    \"WeatherIcon\": 2,    \"IconPhrase\": \"Mostly sunny\",    \"IsDaylight\": true,    \"Temperature\": {      \"Value\": 25.1,      \"Unit\": \"C\",      \"UnitType\": 17    },    \"PrecipitationProbability\": 47,    \"MobileLink\": \"http://m.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&unit=c&lang=en-us\",    \"Link\": \"http://www.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&hbhhour=15&unit=c&lang=en-us\"  },  {    \"DateTime\": \"2018-06-04T16:00:00+02:00\",    \"EpochDateTime\": 1528120800,    \"WeatherIcon\": 17,    \"IconPhrase\": \"Partly sunny w/ t-storms\",    \"IsDaylight\": true,    \"Temperature\": {      \"Value\": 25,      \"Unit\": \"C\",      \"UnitType\": 17    },    \"PrecipitationProbability\": 51,    \"MobileLink\": \"http://m.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&unit=c&lang=en-us\",    \"Link\": \"http://www.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&hbhhour=16&unit=c&lang=en-us\"  },  {    \"DateTime\": \"2018-06-04T17:00:00+02:00\",    \"EpochDateTime\": 1528124400,    \"WeatherIcon\": 3,    \"IconPhrase\": \"Partly sunny\",    \"IsDaylight\": true,    \"Temperature\": {      \"Value\": 25.9,      \"Unit\": \"C\",      \"UnitType\": 17    },    \"PrecipitationProbability\": 47,    \"MobileLink\": \"http://m.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&unit=c&lang=en-us\",    \"Link\": \"http://www.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&hbhhour=17&unit=c&lang=en-us\"  },  {    \"DateTime\": \"2018-06-04T18:00:00+02:00\",    \"EpochDateTime\": 1528128000,    \"WeatherIcon\": 3,    \"IconPhrase\": \"Partly sunny\",    \"IsDaylight\": true,    \"Temperature\": {      \"Value\": 25.2,      \"Unit\": \"C\",      \"UnitType\": 17    },    \"PrecipitationProbability\": 47,    \"MobileLink\": \"http://m.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&unit=c&lang=en-us\",    \"Link\": \"http://www.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&hbhhour=18&unit=c&lang=en-us\"  },  {    \"DateTime\": \"2018-06-04T19:00:00+02:00\",    \"EpochDateTime\": 1528131600,    \"WeatherIcon\": 17,    \"IconPhrase\": \"Partly sunny w/ t-storms\",    \"IsDaylight\": true,    \"Temperature\": {      \"Value\": 23.1,      \"Unit\": \"C\",      \"UnitType\": 17    },    \"PrecipitationProbability\": 51,    \"MobileLink\": \"http://m.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&unit=c&lang=en-us\",    \"Link\": \"http://www.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&hbhhour=19&unit=c&lang=en-us\"  },  {    \"DateTime\": \"2018-06-04T20:00:00+02:00\",    \"EpochDateTime\": 1528135200,    \"WeatherIcon\": 3,    \"IconPhrase\": \"Partly sunny\",    \"IsDaylight\": true,    \"Temperature\": {      \"Value\": 22.1,      \"Unit\": \"C\",      \"UnitType\": 17    },    \"PrecipitationProbability\": 43,    \"MobileLink\": \"http://m.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&unit=c&lang=en-us\",    \"Link\": \"http://www.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&hbhhour=20&unit=c&lang=en-us\"  },  {    \"DateTime\": \"2018-06-04T21:00:00+02:00\",    \"EpochDateTime\": 1528138800,    \"WeatherIcon\": 41,    \"IconPhrase\": \"Partly cloudy w/ t-storms\",    \"IsDaylight\": false,    \"Temperature\": {      \"Value\": 21.1,      \"Unit\": \"C\",      \"UnitType\": 17    },    \"PrecipitationProbability\": 51,    \"MobileLink\": \"http://m.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&unit=c&lang=en-us\",    \"Link\": \"http://www.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&hbhhour=21&unit=c&lang=en-us\"  },  {    \"DateTime\": \"2018-06-04T22:00:00+02:00\",    \"EpochDateTime\": 1528142400,    \"WeatherIcon\": 35,    \"IconPhrase\": \"Partly cloudy\",    \"IsDaylight\": false,    \"Temperature\": {      \"Value\": 20,      \"Unit\": \"C\",      \"UnitType\": 17    },    \"PrecipitationProbability\": 47,    \"MobileLink\": \"http://m.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&unit=c&lang=en-us\",    \"Link\": \"http://www.accuweather.com/en/pl/zabierzow/274433/hourly-weather-forecast/274433?day=1&hbhhour=22&unit=c&lang=en-us\"  }]";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // URL weatherUrl = NetworkUtils.buildUrlForWeather5Days();
       // Log.i("url",weatherUrl.toString());
        // /Json_data = new FetchWeatherDetails().execute(weatherUrl);
        Log.i("url",FAKE_JSON5DAYS);
        parseJSON(FAKE_JSON5DAYS);
        Button clickButton = (Button) findViewById(R.id.backBtn);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
               // startActivity(new Intent(MainActivity.this,MenuActivity.class));
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
                e.printStackTrace();
            }
            Log.i("url main",weatherSearchResults.toString());
            return weatherSearchResults;
        }
        @Override
        protected void onPostExecute(String weatherSearchResults) {
            if(weatherSearchResults != null && !weatherSearchResults.equals("")){
                Log.i("url here",weatherSearchResults);
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


        // enable scaling and scrolling
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);



    }
}
