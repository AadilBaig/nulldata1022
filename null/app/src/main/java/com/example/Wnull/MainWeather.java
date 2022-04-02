package com.example.Wnull;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject; //!!!!!!!!!!!

import cz.msebera.android.httpclient.Header;


public class MainWeather extends AppCompatActivity {
    private Button toMain, toDefinition, cityFinder;

    final String APP_ID = "64109c0ef36369e30a5e98b4fc0bc4ca";
    final String weather_URL = "https://api.openweathermap.org/data/2.5/weather";
    final long minTime = 5000;
    final float minDistance = 1000;
    final int RequestCode = 101;
    String locationProvider = LocationManager.GPS_PROVIDER;
    TextView nameOfCity, weatherState, temperature;
    ImageView mweatherIcon;
    RelativeLayout mCityFinder;
    LocationManager mLocationManager;
    LocationListener mLocationListener;
    EditText cityField;
    public String newCity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_weather);
        weatherState = findViewById(R.id.weatherCondition);
        temperature = findViewById(R.id.Temperature);
        mweatherIcon = findViewById(R.id.weatherIcon);
        mCityFinder = findViewById(R.id.cityFinder);
        nameOfCity = findViewById(R.id.cityName);
        toMain = (Button) findViewById(R.id.toMain);
        toDefinition = (Button) findViewById(R.id.toDefinition);

        cityFinder = (Button) findViewById(R.id.cityFind);
        cityField = (EditText) findViewById(R.id.searchCity);

        toMain.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                openActivity1();
            }
        });

        toDefinition.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                openActivity2();
            }
        });

        cityFinder.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                newCity = cityField.getText().toString();
                onResume();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (newCity != null) {
            getWeatherForNewCity(newCity);
        } else {
            getWeatherForCurrentLocation();
        }
    }

    private void getWeatherForNewCity(String city) {
        RequestParams params = new RequestParams();
        params.put("q", city);
        params.put("appID",APP_ID);
        letsdoSomeNetworking(params);
    }

    private void getWeatherForCurrentLocation() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String latitude = String.valueOf(location.getLatitude());
                String longitude = String.valueOf(location.getLongitude());

                RequestParams params = new RequestParams();
                params.put("lat", latitude);
                params.put("lon", longitude);
                params.put("appid", APP_ID);
                letsdoSomeNetworking(params);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                //not able to get location
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},RequestCode);
            return;
        }
        mLocationManager.requestLocationUpdates(locationProvider, minTime, minDistance, mLocationListener);
    }

    private void letsdoSomeNetworking (RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(weather_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(MainWeather.this, "Data retrieved successfully", Toast.LENGTH_SHORT).show();
                weatherData weatherD = weatherData.fromJson(response);
                updateUI(weatherD);
                //super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void updateUI(weatherData weather) {
        temperature.setText(weather.getmTemperature());
        nameOfCity.setText(weather.getmCity());
        weatherState.setText(weather.getmWeatherType());
        int resourceID = getResources().getIdentifier(weather.getmIcon(), "drawable", getPackageName());
        mweatherIcon.setImageResource(resourceID);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(mLocationListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == RequestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainWeather.this, "Location retrieved succesfully", Toast.LENGTH_SHORT).show();
                getWeatherForCurrentLocation();
            } else {
                //user denied permission
            }
        }
    }

    public void openActivity1(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openActivity2(){
        Intent intent = new Intent(this, MainDefinition.class);
        startActivity(intent);
    }
}