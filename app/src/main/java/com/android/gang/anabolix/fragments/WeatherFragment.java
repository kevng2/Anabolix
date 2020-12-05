package com.android.gang.anabolix.fragments;

import android.os.AsyncTask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.gang.anabolix.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import timber.log.Timber;

//I watched a youtube video to learn how to do this, so I am citing it here, although I did make some changes
//https://www.youtube.com/watch?v=zP41A8VSjB4
public class WeatherFragment extends Fragment {
    private EditText editCity;
    private Button searchLocation;
    private TextView Display1;

    static class Weather extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... address) {
            //String... means multiple address can be send. It acts as array
            try {
                URL url = new URL(address[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //Establish connection with address
                conn.connect();

                //retrieve data from url
                InputStream is = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                //Retrieve data and return it as String
                int data = isr.read();
                StringBuilder content = new StringBuilder();
                char ch;
                while (data != -1) {
                    ch = (char) data;
                    content.append(ch);
                    data = isr.read();
                }

                return content.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    } //First String means URL is in String, Void mean nothing, Third String means Return type will be String

    private void search(View view) {

        String cityName = editCity.getText().toString();

        String content;
        Weather weather = new Weather();
        try {
            content = weather.execute("https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=16cbcd95f3bfcb300599fe876a57d1eb").get();

            //check if retrieval was successful
            Timber.i(content);

            JSONObject jsonObject = new JSONObject(content);
            String weatherData = jsonObject.getString("weather");
            String mainTemperature = jsonObject.getString("main");

            double visibility;
            //put weather json in array
            JSONArray array = new JSONArray(weatherData);

            String main = "";
            String description = "";
            double temp;
            int humidity;
            for (int i = 0; i < array.length(); i++) {
                JSONObject weatherPart = array.getJSONObject(i);
                main = weatherPart.getString("main");
                description = weatherPart.getString("description");
            }
            //get json object
            JSONObject mainPart = new JSONObject(mainTemperature);
            temp = Double.parseDouble((mainPart.getString("temp")));
            //translate from kelvin to celsius
            temp = ((temp - 273.15) * (9 / 5)) + 32;
            String tempString = String.format(Locale.US, "%.2f", temp);
            visibility = Double.parseDouble(jsonObject.getString("visibility"));
            humidity = Integer.parseInt(mainPart.getString("humidity"));
            //By default visibility is in meter
            int visibilityInKilometer = (int) visibility / 1000;
            String Conditions = "Outlook: " + main
                    + "\n Description: " + description
                    + "\nTemperature: " + tempString + "°F"
                    + "\nHumidity: " + humidity + "%"
                    + "\nVisibility: " + visibilityInKilometer + " Kilometers";
            Display1.setText(Conditions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather, container, false);
        editCity = v.findViewById(R.id.editCity);
        searchLocation = v.findViewById(R.id.searchLocation);
        Display1 = v.findViewById(R.id.Display1);

        searchLocation.setOnClickListener(this::search);

        String content;
        Weather weather = new Weather();
        try {
            content = weather.execute("https://api.openweathermap.org/data/2.5/weather?q=London&appid=16cbcd95f3bfcb300599fe876a57d1eb").get();
            //First we will check data is retrieve successfully or not
            Timber.i(content);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }
}
