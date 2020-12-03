package com.android.gang.anabolix;
import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import timber.log.Timber;
//I watched a youtube video to learn how to do this, so I am citing it here, although I did make some changes
//https://www.youtube.com/watch?v=zP41A8VSjB4
public class WeatherActivity extends AppCompatActivity {
    EditText editCity;
    Button searchLocation;
    TextView Display1;
    class Weather extends AsyncTask<String, Void, String> {

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
            String content = "";
            char ch;
            while (data != -1){
                ch = (char) data;
                content = content + ch;
                data = isr.read();
            }

            return content;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
        }
    } //First String means URL is in String, Void mean nothing, Third String means Return type will be String

    public void search(View view) {
        editCity = findViewById(R.id.editCity);
        searchLocation = findViewById(R.id.searchLocation);
        Display1 = findViewById(R.id.Display1);
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
                temp = Double.parseDouble( (mainPart.getString("temp") ) );
                //translate from kelvin to celsius
                temp = ( (temp - 273.15) * (9/5)) + 32;
                String tempString = String.format("%.2f", temp);
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);


        String content;
        Weather weather = new Weather();
        try {
            content = weather.execute("https://api.openweathermap.org/data/2.5/weather?q=London&appid=16cbcd95f3bfcb300599fe876a57d1eb").get();
            //First we will check data is retrieve successfully or not
            Log.i("contentData",content);

        } catch (Exception e) {
            e.printStackTrace();
        }
        }
}
