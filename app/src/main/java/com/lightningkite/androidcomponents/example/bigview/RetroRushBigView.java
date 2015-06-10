package com.lightningkite.androidcomponents.example.bigview;

import android.os.Bundle;
import android.widget.TextView;

import com.lightningkite.androidcomponents.R;
import com.lightningkite.androidcomponents.bigview.BigView;
import com.lightningkite.androidcomponents.bigview.BigViewContainer;
import com.lightningkite.androidcomponents.example.WeatherAPIHelper;
import com.lightningkite.androidcomponents.example.model.CurrentWeather;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.uk.rushorm.core.RushSearch;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * An example BigView that demonstrates how Retrofit and RushORM work together.
 * Created by jivie on 6/3/15.
 */
public class RetroRushBigView extends BigView {

    @InjectView(R.id.rfaa_database_count)
    TextView mDatabaseCountView;
    @InjectView(R.id.rfaa_text)
    TextView mTextView;

    public RetroRushBigView(BigViewContainer container, Bundle arguments, int id) {
        super(container, arguments, id);

        inflate(mActivity, R.layout.bigview_retrorush, this);
        ButterKnife.inject(this);

        mDatabaseCountView.setText(String.valueOf(
                new RushSearch().count(CurrentWeather.class)
        ));

        fetchCurrentWeather();
    }

    private void fetchCurrentWeather() {
        WeatherAPIHelper.getService().getZipWeather("84321,us", new Callback<CurrentWeather>() {
            @Override
            public void success(CurrentWeather currentWeather, Response response) {
                if (currentWeather != null) {
                    mTextView.setText(currentWeather.toString());
                    currentWeather.save();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }
}
