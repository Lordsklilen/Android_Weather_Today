package pl.dela.michal.weathertoday;

import android.graphics.drawable.Drawable;
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
    public static int getIcon(String number){
        switch(number){
            case "1":
                return R.drawable.a01;
            case "2":
                return R.drawable.a02;
            case "3":
                return R.drawable.a03;
            case "4":
                return R.drawable.a04;
            case "5":
                return R.drawable.a05;
            case "6":
                return R.drawable.a06;
            case "7":
                return R.drawable.a07;
            case "8":
                return R.drawable.a08;
            case "11":
                return R.drawable.a11;
            case "12":
                return R.drawable.a12;
            case "13":
                return R.drawable.a13;
            case "14":
                return R.drawable.a14;
            case "15":
                return R.drawable.a15;
            case "16":
                return R.drawable.a16;
            case "17":
                return R.drawable.a17;
            case "18":
                return R.drawable.a18;
            case "19":
                return R.drawable.a19;
            case "20":
                return R.drawable.a20;
            case "21":
                return R.drawable.a21;
            case "22":
                return R.drawable.a22;
            case "23":
                return R.drawable.a23;
            case "24":
                return R.drawable.a24;
            case "25":
                return R.drawable.a25;
            case "26":
                return R.drawable.a26;
            case "29":
                return R.drawable.a29;
            case "30":
                return R.drawable.a30;
            case "31":
                return R.drawable.a31;
            case "32":
                return R.drawable.a32;
            case "33":
                return R.drawable.a33;
            case "34":
                return R.drawable.a34;
            case "35":
                return R.drawable.a35;
            case "36":
                return R.drawable.a36;
            case "37":
                return R.drawable.a37;
            case "38":
                return R.drawable.a38;
            case "39":
                return R.drawable.a39;
            case "40":
                return R.drawable.a40;
            case "41":
                return R.drawable.a41;
            case "42":
                return R.drawable.a42;
            case "43":
                return R.drawable.a43;
            case "44":
                return R.drawable.a44;
        }
        return R.drawable.a01;

    }
}