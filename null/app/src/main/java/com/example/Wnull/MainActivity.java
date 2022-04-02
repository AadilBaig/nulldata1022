package com.example.Wnull;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.filament.View;

public class MainActivity extends AppCompatActivity {

    private Button definitionButton, weatherButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        definitionButton = (Button) findViewById(R.id.definitionButton);
        definitionButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                openActivity2();
            }
        });

        weatherButton = (Button) findViewById(R.id.weatherButton);
        weatherButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                openActivity3();
            }
        });

//        toMain = (Button) findViewById(R.id.toMain);
//        toMain.setOnClickListener(new android.view.View.OnClickListener() {
//            @Override
//            public void onClick(android.view.View view) {
//                openActivity1();
//            }
//        });

    }

        public void openActivity2(){
        Intent intent = new Intent(this, MainDefinition.class);
        startActivity(intent);
        }

        public void openActivity3(){
        Intent intent = new Intent(this, MainWeather.class);
        startActivity(intent);
        }

        public void openActivity1() {
        //Intent intent = new Intent(this, MainDefinition.class);
        //startActivity(intent);
        }


}