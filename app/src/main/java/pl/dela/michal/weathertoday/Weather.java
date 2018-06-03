package pl.dela.michal.weathertoday;

public class Weather {
    String date;
    String minTemp;
    String maxTemp;
    String link;
    Weather(){};
    Weather(String date,String min,String max,String link){
        this.date = date;
        this.minTemp = min;
        this.maxTemp = max;
        this.link = link;
    }
    public String getDate() {
        return date.substring(5,10);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
