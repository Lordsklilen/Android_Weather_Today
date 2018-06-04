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

public class History24Hours extends AppCompatActivity {
    private String FAKE_JSON24_HISTORICAL_HOURS = "[  {    \"LocalObservationDateTime\": \"2018-06-04T11:55:00+02:00\",    \"EpochTime\": 1528106100,    \"WeatherText\": \"Częściowo słonecznie\",    \"WeatherIcon\": 3,    \"IsDayTime\": true,    \"Temperature\": {      \"Metric\": {        \"Value\": 22.8,        \"Unit\": \"C\",        \"UnitType\": 17      },      \"Imperial\": {        \"Value\": 73,        \"Unit\": \"F\",        \"UnitType\": 18      }    },    \"MobileLink\": \"http://m.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\",    \"Link\": \"http://www.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\"  },  {    \"LocalObservationDateTime\": \"2018-06-04T10:55:00+02:00\",    \"EpochTime\": 1528102500,    \"WeatherText\": \"Częściowo słonecznie\",    \"WeatherIcon\": 3,    \"IsDayTime\": true,    \"Temperature\": {      \"Metric\": {        \"Value\": 22.2,        \"Unit\": \"C\",        \"UnitType\": 17      },      \"Imperial\": {        \"Value\": 72,        \"Unit\": \"F\",        \"UnitType\": 18      }    },    \"MobileLink\": \"http://m.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\",    \"Link\": \"http://www.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\"  },  {    \"LocalObservationDateTime\": \"2018-06-04T09:55:00+02:00\",    \"EpochTime\": 1528098900,    \"WeatherText\": \"Częściowo słonecznie\",    \"WeatherIcon\": 3,    \"IsDayTime\": true,    \"Temperature\": {      \"Metric\": {        \"Value\": 21.1,        \"Unit\": \"C\",        \"UnitType\": 17      },      \"Imperial\": {        \"Value\": 70,        \"Unit\": \"F\",        \"UnitType\": 18      }    },    \"MobileLink\": \"http://m.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\",    \"Link\": \"http://www.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\"  },  {    \"LocalObservationDateTime\": \"2018-06-04T08:55:00+02:00\",    \"EpochTime\": 1528095300,    \"WeatherText\": \"Słonecznie\",    \"WeatherIcon\": 1,    \"IsDayTime\": true,    \"Temperature\": {      \"Metric\": {        \"Value\": 18.9,        \"Unit\": \"C\",        \"UnitType\": 17      },      \"Imperial\": {        \"Value\": 66,        \"Unit\": \"F\",        \"UnitType\": 18      }    },    \"MobileLink\": \"http://m.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\",    \"Link\": \"http://www.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\"  },  {    \"LocalObservationDateTime\": \"2018-06-04T07:55:00+02:00\",    \"EpochTime\": 1528091700,    \"WeatherText\": \"Lekka mgła\",    \"WeatherIcon\": 11,    \"IsDayTime\": true,    \"Temperature\": {      \"Metric\": {        \"Value\": 16.1,        \"Unit\": \"C\",        \"UnitType\": 17      },      \"Imperial\": {        \"Value\": 61,        \"Unit\": \"F\",        \"UnitType\": 18      }    },    \"MobileLink\": \"http://m.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\",    \"Link\": \"http://www.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\"  },  {    \"LocalObservationDateTime\": \"2018-06-04T06:55:00+02:00\",    \"EpochTime\": 1528088100,    \"WeatherText\": \"Mgła\",    \"WeatherIcon\": 11,    \"IsDayTime\": true,    \"Temperature\": {      \"Metric\": {        \"Value\": 15,        \"Unit\": \"C\",        \"UnitType\": 17      },      \"Imperial\": {        \"Value\": 59,        \"Unit\": \"F\",        \"UnitType\": 18      }    },    \"MobileLink\": \"http://m.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\",    \"Link\": \"http://www.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\"  },  {    \"LocalObservationDateTime\": \"2018-06-04T06:05:00+02:00\",    \"EpochTime\": 1528085100,    \"WeatherText\": \"Mgła\",    \"WeatherIcon\": 11,    \"IsDayTime\": true,    \"Temperature\": {      \"Metric\": {        \"Value\": 15,        \"Unit\": \"C\",        \"UnitType\": 17      },      \"Imperial\": {        \"Value\": 59,        \"Unit\": \"F\",        \"UnitType\": 18      }    },    \"MobileLink\": \"http://m.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\",    \"Link\": \"http://www.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\"  },  {    \"LocalObservationDateTime\": \"2018-06-04T04:55:00+02:00\",    \"EpochTime\": 1528080900,    \"WeatherText\": \"Lekka mgła\",    \"WeatherIcon\": 11,    \"IsDayTime\": true,    \"Temperature\": {      \"Metric\": {        \"Value\": 15,        \"Unit\": \"C\",        \"UnitType\": 17      },      \"Imperial\": {        \"Value\": 59,        \"Unit\": \"F\",        \"UnitType\": 18      }    },    \"MobileLink\": \"http://m.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\",    \"Link\": \"http://www.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\"  },  {    \"LocalObservationDateTime\": \"2018-06-04T03:55:00+02:00\",    \"EpochTime\": 1528077300,    \"WeatherText\": \"Lekka mgła\",    \"WeatherIcon\": 11,    \"IsDayTime\": false,    \"Temperature\": {      \"Metric\": {        \"Value\": 15,        \"Unit\": \"C\",        \"UnitType\": 17      },      \"Imperial\": {        \"Value\": 59,        \"Unit\": \"F\",        \"UnitType\": 18      }    },    \"MobileLink\": \"http://m.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\",    \"Link\": \"http://www.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\"  },  {    \"LocalObservationDateTime\": \"2018-06-04T02:55:00+02:00\",    \"EpochTime\": 1528073700,    \"WeatherText\": \"Lekka mgła\",    \"WeatherIcon\": 11,    \"IsDayTime\": false,    \"Temperature\": {      \"Metric\": {        \"Value\": 15,        \"Unit\": \"C\",        \"UnitType\": 17      },      \"Imperial\": {        \"Value\": 59,        \"Unit\": \"F\",        \"UnitType\": 18      }    },    \"MobileLink\": \"http://m.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\",    \"Link\": \"http://www.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\"  },  {    \"LocalObservationDateTime\": \"2018-06-04T01:55:00+02:00\",    \"EpochTime\": 1528070100,    \"WeatherText\": \"Lekka mgła\",    \"WeatherIcon\": 11,    \"IsDayTime\": false,    \"Temperature\": {      \"Metric\": {        \"Value\": 16.1,        \"Unit\": \"C\",        \"UnitType\": 17      },      \"Imperial\": {        \"Value\": 61,        \"Unit\": \"F\",        \"UnitType\": 18      }    },    \"MobileLink\": \"http://m.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\",    \"Link\": \"http://www.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\"  },  {    \"LocalObservationDateTime\": \"2018-06-04T01:05:00+02:00\",    \"EpochTime\": 1528067100,    \"WeatherText\": \"Bezchmurnie\",    \"WeatherIcon\": 33,    \"IsDayTime\": false,    \"Temperature\": {      \"Metric\": {        \"Value\": 16.1,        \"Unit\": \"C\",        \"UnitType\": 17      },      \"Imperial\": {        \"Value\": 61,        \"Unit\": \"F\",        \"UnitType\": 18      }    },    \"MobileLink\": \"http://m.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\",    \"Link\": \"http://www.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\"  },  {    \"LocalObservationDateTime\": \"2018-06-04T00:05:00+02:00\",    \"EpochTime\": 1528063500,    \"WeatherText\": \"Bezchmurnie\",    \"WeatherIcon\": 33,    \"IsDayTime\": false,    \"Temperature\": {      \"Metric\": {        \"Value\": 17.2,        \"Unit\": \"C\",        \"UnitType\": 17      },      \"Imperial\": {        \"Value\": 63,        \"Unit\": \"F\",        \"UnitType\": 18      }    },    \"MobileLink\": \"http://m.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\",    \"Link\": \"http://www.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\"  },  {    \"LocalObservationDateTime\": \"2018-06-03T23:05:00+02:00\",    \"EpochTime\": 1528059900,    \"WeatherText\": \"Bezchmurnie\",    \"WeatherIcon\": 33,    \"IsDayTime\": false,    \"Temperature\": {      \"Metric\": {        \"Value\": 17.8,        \"Unit\": \"C\",        \"UnitType\": 17      },      \"Imperial\": {        \"Value\": 64,        \"Unit\": \"F\",        \"UnitType\": 18      }    },    \"MobileLink\": \"http://m.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\",    \"Link\": \"http://www.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\"  },  {    \"LocalObservationDateTime\": \"2018-06-03T21:55:00+02:00\",    \"EpochTime\": 1528055700,    \"WeatherText\": \"Zachmurzenie duże\",    \"WeatherIcon\": 38,    \"IsDayTime\": false,    \"Temperature\": {      \"Metric\": {        \"Value\": 20,        \"Unit\": \"C\",        \"UnitType\": 17      },      \"Imperial\": {        \"Value\": 68,        \"Unit\": \"F\",        \"UnitType\": 18      }    },    \"MobileLink\": \"http://m.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\",    \"Link\": \"http://www.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\"  },  {    \"LocalObservationDateTime\": \"2018-06-03T20:55:00+02:00\",    \"EpochTime\": 1528052100,    \"WeatherText\": \"Bezchmurnie\",    \"WeatherIcon\": 33,    \"IsDayTime\": false,    \"Temperature\": {      \"Metric\": {        \"Value\": 20,        \"Unit\": \"C\",        \"UnitType\": 17      },      \"Imperial\": {        \"Value\": 68,        \"Unit\": \"F\",        \"UnitType\": 18      }    },    \"MobileLink\": \"http://m.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\",    \"Link\": \"http://www.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\"  },  {    \"LocalObservationDateTime\": \"2018-06-03T19:55:00+02:00\",    \"EpochTime\": 1528048500,    \"WeatherText\": \"Częściowo słonecznie\",    \"WeatherIcon\": 3,    \"IsDayTime\": true,    \"Temperature\": {      \"Metric\": {        \"Value\": 22.2,        \"Unit\": \"C\",        \"UnitType\": 17      },      \"Imperial\": {        \"Value\": 72,        \"Unit\": \"F\",        \"UnitType\": 18      }    },    \"MobileLink\": \"http://m.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\",    \"Link\": \"http://www.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\"  },  {    \"LocalObservationDateTime\": \"2018-06-03T18:55:00+02:00\",    \"EpochTime\": 1528044900,    \"WeatherText\": \"Częściowo słonecznie\",    \"WeatherIcon\": 3,    \"IsDayTime\": true,    \"Temperature\": {      \"Metric\": {        \"Value\": 22.8,        \"Unit\": \"C\",        \"UnitType\": 17      },      \"Imperial\": {        \"Value\": 73,        \"Unit\": \"F\",        \"UnitType\": 18      }    },    \"MobileLink\": \"http://m.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\",    \"Link\": \"http://www.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\"  },  {    \"LocalObservationDateTime\": \"2018-06-03T17:55:00+02:00\",    \"EpochTime\": 1528041300,    \"WeatherText\": \"Częściowo słonecznie\",    \"WeatherIcon\": 3,    \"IsDayTime\": true,    \"Temperature\": {      \"Metric\": {        \"Value\": 23.9,        \"Unit\": \"C\",        \"UnitType\": 17      },      \"Imperial\": {        \"Value\": 75,        \"Unit\": \"F\",        \"UnitType\": 18      }    },    \"MobileLink\": \"http://m.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\",    \"Link\": \"http://www.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\"  },  {    \"LocalObservationDateTime\": \"2018-06-03T16:55:00+02:00\",    \"EpochTime\": 1528037700,    \"WeatherText\": \"Częściowo słonecznie\",    \"WeatherIcon\": 3,    \"IsDayTime\": true,    \"Temperature\": {      \"Metric\": {        \"Value\": 23.9,        \"Unit\": \"C\",        \"UnitType\": 17      },      \"Imperial\": {        \"Value\": 75,        \"Unit\": \"F\",        \"UnitType\": 18      }    },    \"MobileLink\": \"http://m.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\",    \"Link\": \"http://www.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\"  },  {    \"LocalObservationDateTime\": \"2018-06-03T15:55:00+02:00\",    \"EpochTime\": 1528034100,    \"WeatherText\": \"Częściowo słonecznie\",    \"WeatherIcon\": 3,    \"IsDayTime\": true,    \"Temperature\": {      \"Metric\": {        \"Value\": 23.9,        \"Unit\": \"C\",        \"UnitType\": 17      },      \"Imperial\": {        \"Value\": 75,        \"Unit\": \"F\",        \"UnitType\": 18      }    },    \"MobileLink\": \"http://m.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\",    \"Link\": \"http://www.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\"  },  {    \"LocalObservationDateTime\": \"2018-06-03T14:55:00+02:00\",    \"EpochTime\": 1528030500,    \"WeatherText\": \"Częściowo słonecznie\",    \"WeatherIcon\": 3,    \"IsDayTime\": true,    \"Temperature\": {      \"Metric\": {        \"Value\": 22.2,        \"Unit\": \"C\",        \"UnitType\": 17      },      \"Imperial\": {        \"Value\": 72,        \"Unit\": \"F\",        \"UnitType\": 18      }    },    \"MobileLink\": \"http://m.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\",    \"Link\": \"http://www.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\"  },  {    \"LocalObservationDateTime\": \"2018-06-03T13:55:00+02:00\",    \"EpochTime\": 1528026900,    \"WeatherText\": \"Częściowo słonecznie\",    \"WeatherIcon\": 3,    \"IsDayTime\": true,    \"Temperature\": {      \"Metric\": {        \"Value\": 21.1,        \"Unit\": \"C\",        \"UnitType\": 17      },      \"Imperial\": {        \"Value\": 70,        \"Unit\": \"F\",        \"UnitType\": 18      }    },    \"MobileLink\": \"http://m.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\",    \"Link\": \"http://www.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\"  },  {    \"LocalObservationDateTime\": \"2018-06-03T12:55:00+02:00\",    \"EpochTime\": 1528023300,    \"WeatherText\": \"Zachmurzenie duże\",    \"WeatherIcon\": 6,    \"IsDayTime\": true,    \"Temperature\": {      \"Metric\": {        \"Value\": 20,        \"Unit\": \"C\",        \"UnitType\": 17      },      \"Imperial\": {        \"Value\": 68,        \"Unit\": \"F\",        \"UnitType\": 18      }    },    \"MobileLink\": \"http://m.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\",    \"Link\": \"http://www.accuweather.com/pl/pl/zabierzow/274433/current-weather/274433\"  }]";
    private ArrayList<WeatherHourly> weatherArrayList = new ArrayList<WeatherHourly>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history24_hours);
        Log.i("data:",FAKE_JSON24_HISTORICAL_HOURS);
        parseJSON(FAKE_JSON24_HISTORICAL_HOURS);

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
