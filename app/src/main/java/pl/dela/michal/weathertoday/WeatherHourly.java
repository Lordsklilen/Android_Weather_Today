package pl.dela.michal.weathertoday;

public class WeatherHourly {
    String date;
    String Temperature;
    String link;
    String Icon;
    String IconPhrase;
    String PrecipitationProbability;
    WeatherHourly(){};
    public String getDate() {
        return date.substring(5,10);
    }
    public String getHour() {
        return date.substring(11,17);
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
