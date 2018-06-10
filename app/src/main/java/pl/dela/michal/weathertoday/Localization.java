package pl.dela.michal.weathertoday;

public class Localization {
    String LocalizedName ;
    String Country;
    String AdministrativeArea;
    String Key;

    public String getLocalizedName() {
        return LocalizedName;
    }

    public void setLocalizedName(String localizedName) {
        LocalizedName = localizedName;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getAdministrativeArea() {
        return AdministrativeArea;
    }

    public void setAdministrativeArea(String administrativeArea) {
        AdministrativeArea = administrativeArea;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    @Override
    public String toString() {
        return "Localization{" +
                "LocalizedName='" + LocalizedName + '\'' +
                ", Country='" + Country + '\'' +
                ", AdministrativeArea='" + AdministrativeArea + '\'' +
                ", Key='" + Key + '\'' +
                '}';
    }
}
