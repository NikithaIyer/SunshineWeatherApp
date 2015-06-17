package nikithaiyer.com.sunshineweatherapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends ActionBarActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .add(R.id.container, new PlaceholderFragment())
          .commit();
    }
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  public static class PlaceholderFragment extends Fragment {

    private ArrayAdapter<String> adapter;

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_main, container,false);
      String[] forecastArray = { "Today Sunny",
                                "Tomorrow Foggy",
                                "Wednesday Rain",
                                "Thursday Mist",
                                "Friday Snowfall",
                                "Saturday Cloudy",
                                "Sunday Rain" };
      ArrayList<String> weekForecast = new ArrayList<>(Arrays.asList(forecastArray));
      ListView listView = (ListView) rootView.findViewById(R.id.list_view_forecast);
      adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast,R.id.list_item_forecast_textview,weekForecast);
      listView.setAdapter(adapter);

      HttpURLConnection urlConnection = null;
      BufferedReader reader = null;
      String forecastJsonString = null;

      try {
        URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=411011&mode=json&units=metric&cnt=7");
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
        Log.e("PlaceholderFragment","Error ",e);
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


      return rootView;
    }
  }
}
