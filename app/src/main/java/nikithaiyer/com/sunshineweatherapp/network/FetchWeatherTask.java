package nikithaiyer.com.sunshineweatherapp.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchWeatherTask extends AsyncTask<String,Void,String> {
  @Override
  protected String doInBackground(String... strings) {
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String forecastJsonString = null;

    try {
      URL url = new URL(strings[0]);
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
      Log.e("PlaceholderFragment", "Error ", e);
    } finally {
      if (urlConnection != null){
        urlConnection.disconnect();
      }
      if (reader != null){
        try {
          reader.close();
        } catch (IOException e) {
          Log.e("PlaceholderFragment","Error closing stream ",e);
        }
      }
    }
    return forecastJsonString;
  }
}
