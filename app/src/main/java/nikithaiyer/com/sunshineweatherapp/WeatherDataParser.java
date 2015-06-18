package nikithaiyer.com.sunshineweatherapp;

import android.text.format.Time;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class WeatherDataParser {

  private static final String LOG_TAG = WeatherDataParser.class.getSimpleName();

  public String[] getWeatherDataFromJson(String forecastJsonString, int numOfDays) throws JSONException {

    JSONObject forecastJson = new JSONObject(forecastJsonString);
    final String LISTNODE = "list";
    JSONArray weatherArray = forecastJson.getJSONArray(LISTNODE);

    Time dayTime = new Time();
    dayTime.setToNow();
    int julianStartDay = Time.getJulianDay(System.currentTimeMillis(),dayTime.gmtoff);

    String[] resultStrings = new String[numOfDays];
    for (int i = 0; i < weatherArray.length(); i++) {

      JSONObject dayForecast = weatherArray.getJSONObject(i);
      long dateTime = dayTime.setJulianDay(julianStartDay + i);

      SimpleDateFormat shoretenedDateFormat = new SimpleDateFormat("EEE MMM dd");
      String day = shoretenedDateFormat.format(dateTime);

      JSONObject weatherObject = dayForecast.getJSONArray("weather").getJSONObject(0);
      String description = weatherObject.getString("description");

      JSONObject temperatureObject = dayForecast.getJSONObject("temp");
      double max = temperatureObject.getDouble("max");
      double min = temperatureObject.getDouble("min");

      String highAndLow = Math.round(max) + "/" + Math.round(min);
      resultStrings[i] = day + " - " + description + " - " + highAndLow;
    }
    for (String s: resultStrings) {
      Log.d(LOG_TAG, "ForecastEntry: " + s);
    }
    return  resultStrings;
  }

}


