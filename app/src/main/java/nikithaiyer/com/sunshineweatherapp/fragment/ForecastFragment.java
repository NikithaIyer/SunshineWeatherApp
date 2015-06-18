package nikithaiyer.com.sunshineweatherapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nikithaiyer.com.sunshineweatherapp.R;
import nikithaiyer.com.sunshineweatherapp.WeatherAsyncResponse;
import nikithaiyer.com.sunshineweatherapp.network.FetchWeatherTask;

public class ForecastFragment extends Fragment implements WeatherAsyncResponse {

  private ArrayAdapter<String> adapter;
  private final String[] forecastArray = new String[]{"Today Sunny",
      "Tomorrow Foggy",
      "Wednesday Rain",
      "Thursday Mist",
      "Friday Snowfall",
      "Saturday Cloudy",
      "Sunday Rain"};
  private ListView listView;
  //  private String[] forecastArray = new String[]{};

  public ForecastFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.forecast_fragment, container, false);
    List<String> weekForecast = new ArrayList<>(Arrays.asList(forecastArray));
    listView = (ListView) rootView.findViewById(R.id.list_view_forecast);
    adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, weekForecast);
    listView.setAdapter(adapter);
    return rootView;
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_forecast_fragment, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_refresh) {
      FetchWeatherTask fetchWeatherTask = new FetchWeatherTask();
      fetchWeatherTask.execute("411011");
      fetchWeatherTask.weatherAsyncResponse = this;
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onComplete(String[] forecastList) {
    List<String> weekForecast = new ArrayList<>(Arrays.asList(forecastList));
    adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, weekForecast);
    listView.setAdapter(adapter);
  }
}

