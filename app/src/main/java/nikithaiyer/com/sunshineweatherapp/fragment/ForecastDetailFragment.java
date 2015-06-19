package nikithaiyer.com.sunshineweatherapp.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nikithaiyer.com.sunshineweatherapp.R;

public class ForecastDetailFragment extends Fragment {


  public ForecastDetailFragment() {
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_forecast_detail, container, false);
    Intent intent = getActivity().getIntent();
    if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
      String forecastDetailString = intent.getStringExtra(Intent.EXTRA_TEXT);
      TextView detailText = (TextView) rootView.findViewById(R.id.detail_text);
      detailText.setText(forecastDetailString);
    }
    return rootView;
  }
}
