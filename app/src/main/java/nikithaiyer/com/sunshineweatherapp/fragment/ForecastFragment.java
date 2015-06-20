package nikithaiyer.com.sunshineweatherapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import nikithaiyer.com.sunshineweatherapp.ForecastDetailActivity;
import nikithaiyer.com.sunshineweatherapp.R;
import nikithaiyer.com.sunshineweatherapp.WeatherAsyncResponse;
import nikithaiyer.com.sunshineweatherapp.network.FetchWeatherTask;

public class ForecastFragment extends Fragment implements WeatherAsyncResponse {

  private ArrayAdapter<String> adapter;
  private ListView listView;

  public ForecastFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d("WWWWW","******ForecastFragmentOnCreateCalled********");
    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.forecast_fragment, container, false);

    FetchWeatherTask fetchWeatherTask = new FetchWeatherTask();
    fetchWeatherTask.weatherAsyncResponse = this;

    List<String> weekForecast = new ArrayList<>();
    adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, weekForecast);
    listView = (ListView) rootView.findViewById(R.id.list_view_forecast);
    String zipCode = getZipCode();
    fetchWeatherTask.execute(zipCode);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        String forecast = adapter.getItem(position);
//        Toast.makeText(getActivity(),forecast,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), ForecastDetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, forecast);
        startActivity(intent);
      }
    });

//    listView.setAdapter(adapter);
    return rootView;
  }

  private String getZipCode() {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
    return sharedPreferences.getString(getString(R.string.location_preference_key),getString(R.string.location_preference_default_value));
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
      fetchWeatherTask.weatherAsyncResponse = this;
      String zipCode = getZipCode();
      fetchWeatherTask.execute(zipCode);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onComplete(String[] forecastList) {
    adapter.clear();
    if (forecastList != null) {
      adapter.addAll(forecastList);
      listView.setAdapter(adapter);
    }
  }
}

