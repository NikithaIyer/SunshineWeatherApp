package nikithaiyer.com.sunshineweatherapp.network;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import nikithaiyer.com.sunshineweatherapp.WeatherAsyncResponse;
import nikithaiyer.com.sunshineweatherapp.WeatherDataParser;

public class FetchWeatherTask extends AsyncTask<String,Void,String[]> {

  private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();
  public WeatherAsyncResponse weatherAsyncResponse;

  @Override
  protected String[] doInBackground(String... strings) {
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String forecastJsonString = null;
    String noOfDays = "7";

    try {
      if (strings.length == 0) {
        return null;
      }
      Uri builtUri = Uri.parse("http://api.openweathermap.org/data/2.5/forecast/daily?").buildUpon()
          .appendQueryParameter("q",strings[0])
          .appendQueryParameter("mode","json")
          .appendQueryParameter("units","metric")
          .appendQueryParameter("cnt", noOfDays).build();
      URL url = new URL(builtUri.toString());
      Log.d(LOG_TAG,"Built URL: "+ builtUri.toString());
      urlConnection = (HttpURLConnection) url.openConnection();
      urlConnection.setRequestMethod("GET");
      urlConnection.connect();

      InputStream inputStream = urlConnection.getInputStream();
      StringBuffer buffer = new StringBuffer();
      if (inputStream == null){
        return  null;
      }
      reader = new BufferedReader(new InputStreamReader(inputStream));
      String line;
      while ((line = reader.readLine()) != null) {
        buffer.append(line + "\n");
      }
      if (buffer.length() == 0) {
        return null;
      }
      forecastJsonString = buffer.toString();
    } catch (IOException e) {
      Log.e(LOG_TAG, "Error ", e);
    } finally {
      if (urlConnection != null){
        urlConnection.disconnect();
      }
      if (reader != null){
        try {
          reader.close();
        } catch (IOException e) {
          Log.e(LOG_TAG,"Error closing stream ",e);
        }
      }
    }
    Log.d(LOG_TAG,"Forecast Json String: "+ forecastJsonString);
    WeatherDataParser parser = new WeatherDataParser();
    try {
      return parser.getWeatherDataFromJson(forecastJsonString,Integer.parseInt(noOfDays));
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  protected void onPostExecute(String[] forecastList) {
    weatherAsyncResponse.onComplete(forecastList);
  }
}
