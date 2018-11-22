package com.example.edu.openweather;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button searchBtn;
    EditText cityInput1;
    TextView weatherResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        cityInput1 = findViewById(R.id.cityInput1);
        weatherResult = findViewById(R.id.weatherResult);

        switch (v.getId()) {
            case R.id.searchBtn:
                OpenWeatherApiTask task = new OpenWeatherApiTask();
                try {
                    String weather = task.execute(cityInput1.getText().toString()).get();
//                    String weather = task.execute("London").get();
                    weatherResult.setText(weather);
                }catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }

    private class OpenWeatherApiTask extends AsyncTask<String, Void, String> {
        @Override
                protected String doInBackground(String... params) {
            String id = params[0];
            String weather = null;
            String urlString="http://api.openweathermap.org/data/2.5/weather"+"?q=" + id + "&appid=e1887ea28d339bf6e327226a3ab990bb";
            try {
                URL url=new URL(urlString);
                HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
                InputStream in=urlConnection.getInputStream();
                byte[] buffer=new byte[1000];
                in.read(buffer);

                weather= new String(buffer);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return weather;
        }
    }
}