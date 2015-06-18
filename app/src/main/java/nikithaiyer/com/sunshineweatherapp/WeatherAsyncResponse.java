package nikithaiyer.com.sunshineweatherapp;

public interface WeatherAsyncResponse {
  void onComplete( String[] forecastList);
}
