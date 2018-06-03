package pl.dela.michal.weathertoday;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private final static String WEATHER_BASE_URL_5DAYS = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/";
    private final static String WEATHER_BASE_URL_15DAYS = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/";
    private static String PLACE_ID = "274433";
    private final static String APIKEY = "murcs4qu1CJN4exGWaGMoyqh3X1eQrPh";
    private final static String PARAM_METRIC = "metric";
    private final static String METRIC_VALUE = "true";

    public static URL buildUrlForWeather5Days() {
        Uri buildUri = Uri.parse(WEATHER_BASE_URL_5DAYS + PLACE_ID).buildUpon()
                .appendQueryParameter("apikey",APIKEY)
                .appendQueryParameter(PARAM_METRIC,METRIC_VALUE)
                .build();
        URL url = null ;
        try{
            url = new URL(buildUri.toString());
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
    public static URL buildUrlForWeather15Days() {
        Uri buildUri = Uri.parse(WEATHER_BASE_URL_15DAYS + PLACE_ID).buildUpon()
                .appendQueryParameter("apikey",APIKEY)
                .appendQueryParameter(PARAM_METRIC,METRIC_VALUE)
                .build();
        URL url = null ;
        try{
            url = new URL(buildUri.toString());
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
    public static String getResponseFromHttpUrl(URL url ) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream is = urlConnection.getInputStream();
            Scanner scanner = new Scanner(is);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            }
            else
                return null;
        }
        finally{
            urlConnection.disconnect();
        }
    }
}