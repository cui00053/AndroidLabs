package com.example.chenxiao.androidlabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;



public class WeatherForecast extends Activity {

    protected static final String ACTIVITY_NAME = "WeatherForecast";

    private ImageView weatherImage;
    private TextView currentTempTV;
    private TextView minTempTV;
    private TextView maxTempTV;
    private TextView windTV;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        weatherImage = (ImageView) findViewById(R.id.weatherImage);
        currentTempTV = (TextView) findViewById(R.id.currentTempTV);
        minTempTV = (TextView) findViewById(R.id.minTempTV);
        maxTempTV = (TextView) findViewById(R.id.maxTempTV);
        windTV = (TextView)findViewById(R.id.windTV) ;
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        ForecastQuery forecast = new ForecastQuery();
        forecast.execute();

    }


   public class ForecastQuery extends AsyncTask<String, Integer, String>{

        private String wind;
        private String min;
        private String max;
        private String currentTemp;
        private String iconName;
        private Bitmap icon;

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            URL imageUrl = null;

            try {
                url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                InputStream inputStream = conn.getInputStream();

                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(inputStream, null);

                while(parser.next() != XmlPullParser.END_DOCUMENT){
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    if(parser.getName().equals("temperature")){
                        currentTemp = parser.getAttributeValue(null,"value");
                        publishProgress(20);
                        android.os.SystemClock.sleep(500);
                        min = parser.getAttributeValue(null,"min");
                        publishProgress(40);
                        android.os.SystemClock.sleep(500);
                        max = parser.getAttributeValue(null,"value");
                        publishProgress(60);
                        android.os.SystemClock.sleep(500);
                    }
                    if(parser.getName().equals("speed")){
                        wind = parser.getAttributeValue(null,"value");
                        publishProgress(80);
                    }
                    if(parser.getName().equals("weather")){
                        iconName = parser.getAttributeValue(null,"icon");
                    }
                }
                conn.disconnect();

                // Get weather image\
                String imageFile = iconName + ".png";
                Log.i(ACTIVITY_NAME,"Searching "+iconName+".png file");
                if(fileExistance(imageFile)){
                    FileInputStream fis = null;
                    try {    fis = openFileInput(imageFile);   }
                    catch (FileNotFoundException e) {    e.printStackTrace();  }
                    icon = BitmapFactory.decodeStream(fis);
                    Log.i(ACTIVITY_NAME,"Weather image exists");
                }else{
                    imageUrl = new URL("http://openweathermap.org/img/w/" + iconName + ".png");

                    icon = HttpUtils.getImage(imageUrl);
                    FileOutputStream outputStream = openFileOutput( iconName + ".png", Context.MODE_PRIVATE);
                    icon.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    Log.i(ACTIVITY_NAME,"Download weather image");
                }
                publishProgress(100);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer...value){
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
        }

        @Override
        protected void onPostExecute(String result){
            progressBar.setVisibility(View.INVISIBLE);
            minTempTV.setText(min + "°C");
            maxTempTV.setText(max + "°C");
            currentTempTV.setText(currentTemp + "°C");
            windTV.setText(wind );
            weatherImage.setImageBitmap(icon);
        }

    }
   static class HttpUtils {
        public static Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        public static Bitmap getImage(String urlString) {
            try {
                URL url = new URL(urlString);
                return getImage(url);
            } catch (MalformedURLException e) {
                return null;
            }
        }

    }


}
