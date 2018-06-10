package pl.dela.michal.weathertoday;

public class WeatherHourly {
    String date;
    String Temperature;
    String link;
    String Icon;
    String IconPhrase;
    String PrecipitationProbability;
    WeatherHourly(){};
    WeatherHourly(String a,String b, String c, String d, String e, String f){
        date = a;
        Temperature = b;
        link =c;
        Icon = d;
        IconPhrase = e;
        PrecipitationProbability = f;
    }
    public String getDate() {
        return date.substring(5,10);
    }
    public String getHour() {
        return date.substring(11,16);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public String getIconPhrase() {
        return IconPhrase;
    }

    public void setIconPhrase(String iconPhrase) {
        IconPhrase = iconPhrase;
    }

    public String getTemperature() {
        return Temperature;
    }
    public void setTemperature(String temperature) {
        Temperature = temperature;
    }
    public String getPrecipitationProbability() {
        return PrecipitationProbability + "%";
    }
    public void setPrecipitationProbability(String precipitationProbability) {
        PrecipitationProbability = precipitationProbability;
    }
}
